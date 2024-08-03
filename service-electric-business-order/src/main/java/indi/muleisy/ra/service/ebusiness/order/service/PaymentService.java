package indi.muleisy.ra.service.ebusiness.order.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.report.model.GoodsFlow;
import indi.muleisy.ra.service.ebusiness.order.model.Order;
import indi.muleisy.ra.service.ebusiness.order.model.OrderItem;
import indi.muleisy.ra.service.ebusiness.order.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private final String GOODS_REPORT_URL = "http://report-service/report/goods/flow";

    @Transactional
    public Result payOrder(String orderId, String paymentMethod, String paymentToken) {
        // 获取订单
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            // Step 1: 检查并处理支付
            boolean paymentSuccess = processPayment(order, paymentMethod, paymentToken);
            if (!paymentSuccess) {
                log.error("支付失败，订单ID：{}", order.getOrderId());
                return Result.failure(ResultCode.USER_LOGIN_ERROR, "支付失败");
            }

            // Step 2: 更新订单状态为已支付
            order.setStatus("PAID");
            orderRepository.save(order);

            // Step 3: 将货物流向上报到报表模块
            reportLogistics(order);

            return Result.success();

        } catch (Exception e) {
            log.error("订单支付处理失败，订单ID：{}，错误信息：{}", order.getOrderId(), e.getMessage());
            processRefund(order, paymentMethod); // 进行退款处理
            return Result.failure(ResultCode.USER_LOGIN_ERROR, "订单支付处理失败: " + e.getMessage());
        }
    }

    private boolean processPayment(Order order, String paymentMethod, String paymentToken) {
        try {
            String url = "http://payment-module/api/payment/verify"; // 假设支付模块的支付验证接口
            // 构建支付请求
            PaymentRequest paymentRequest = new PaymentRequest(order.getUserId(), order.getDiscountedPrice(), paymentMethod, paymentToken);
            Result paymentResult = restTemplateBuilder.build().postForObject(url, paymentRequest, Result.class);

            if (Objects.equals(Objects.requireNonNull(paymentResult).getCode(), ResultCode.SUCCESS.getCode())) {
                log.info("支付成功，订单ID：{}", order.getOrderId());
                return true;
            } else {
                log.error("支付失败，订单ID：{}，错误信息：{}", order.getOrderId(), paymentResult.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("支付处理异常，订单ID：{}，错误信息：{}", order.getOrderId(), e.getMessage());
            return false;
        }
    }

    private void processRefund(Order order, String paymentMethod) {
        try {
            String url = "http://payment-module/api/payment/refund"; // 假设支付模块的退款接口
            // 构建退款请求
            RefundRequest refundRequest = new RefundRequest(order.getUserId(), order.getDiscountedPrice(), paymentMethod);
            Result refundResult = restTemplateBuilder.build().postForObject(url, refundRequest, Result.class);

            if (Objects.equals(Objects.requireNonNull(refundResult).getCode(), ResultCode.SUCCESS.getCode())) {
                log.info("退款成功，订单ID：{}", order.getOrderId());
            } else {
                log.error("退款失败，订单ID：{}，错误信息：{}", order.getOrderId(), refundResult.getMessage());
            }
        } catch (Exception e) {
            log.error("退款处理异常，订单ID：{}，错误信息：{}", order.getOrderId(), e.getMessage());
        }
    }

    // 支付请求类
    private static class PaymentRequest {
        private String userId;
        private double amount;
        private String paymentMethod;
        private String paymentToken;

        public PaymentRequest(String userId, double amount, String paymentMethod, String paymentToken) {
            this.userId = userId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.paymentToken = paymentToken;
        }

        // getters and setters
    }

    // 退款请求类
    private static class RefundRequest {
        private String userId;
        private double amount;
        private String paymentMethod;

        public RefundRequest(String userId, double amount, String paymentMethod) {
            this.userId = userId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
        }

        // getters and setters
    }


    private void reportLogistics(Order order) {
        // 构建货物流向对象
        GoodsFlow goodsFlow = new GoodsFlow();
        goodsFlow.setOrderId(order.getOrderId());
        for (OrderItem orderItem :order.getItems()) {
            goodsFlow.addProductsId(orderItem.getProductId());
            goodsFlow.addQuantities(orderItem.getQuantity());
        }

        // 发送请求到报表模块
        Result result = restTemplateBuilder.build().postForObject(GOODS_REPORT_URL, goodsFlow, Result.class);

        if (result != null && Objects.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
            log.info("货物流向上报成功");
        } else {
            log.warn("货物流向上报失败: " + (result != null ? result.getMessage() : "未知错误"));
        }
    }


}
