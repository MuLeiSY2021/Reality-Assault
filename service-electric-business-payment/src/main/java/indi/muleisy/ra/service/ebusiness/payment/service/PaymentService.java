package indi.muleisy.ra.service.ebusiness.payment.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.order.model.Order;
import indi.muleisy.ra.service.ebusiness.order.model.OrderItem;
import indi.muleisy.ra.service.ebusiness.payment.model.Coupon;
import indi.muleisy.ra.service.ebusiness.payment.model.UserBalance;
import indi.muleisy.ra.service.ebusiness.payment.model.UserInventory;
import indi.muleisy.ra.service.ebusiness.payment.repository.CouponRepository;
import indi.muleisy.ra.service.ebusiness.payment.repository.OrderRepository;
import indi.muleisy.ra.service.ebusiness.payment.repository.UserBalanceRepository;
import indi.muleisy.ra.service.ebusiness.payment.repository.UserInventoryRepository;
import indi.muleisy.ra.service.ebusiness.report.model.GoodsFlow;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplateBuilder restTemplate;

    private final String GOODS_REPORT_URL = "http://report-service/report/goods/flow";

    public Result checkBalance(String userId, List<UserBalance> balanceCheckList) {
        for (UserBalance balanceCheck : balanceCheckList) {
            UserBalance userBalance = userBalanceRepository.findByUserIdAndCurrencyType(userId, balanceCheck.getCurrencyType());
            if (userBalance == null || userBalance.getBalance() < balanceCheck.getBalance()) {
                return Result.failure(ResultCode.USER_LOGIN_ERROR, "余额不足");
            }
        }
        return Result.success();
    }

    public Result getBalance(String userId) {
        List<UserBalance> balances = userBalanceRepository.findByUserId(userId);
        return Result.success(balances);
    }

    @Transactional
    public Result processOrderPayment(Order order) {
        try {
            // Step 1: 写入报表模块 (假设是通过某种服务或消息队列发送，略去实现细节)
            reportPaymentDetails(order);

            // Step 2: 检查用户余额是否充足
            double totalOrderCost = order.getDiscountedPrice();
            UserBalance cashBalance = userBalanceRepository.findByUserIdAndCurrencyType(order.getUserId(), "cash");

            if (cashBalance == null || cashBalance.getBalance() < totalOrderCost) {
                throw new RuntimeException("余额不足");
            }

            // Step 3: 扣减用户余额
            cashBalance.setBalance(cashBalance.getBalance() - totalOrderCost);
            userBalanceRepository.save(cashBalance);

            // Step 4: 更新商品库存
            for (OrderItem item : order.getItems()) {
                UserInventory inventory = userInventoryRepository.findByUserIdAndProductId(order.getUserId(), item.getProductId());
                if (inventory == null) {
                    inventory = new UserInventory();
                    inventory.setUserId(order.getUserId());
                    inventory.setProductId(item.getProductId());
                    inventory.setQuantity(item.getQuantity());
                } else {
                    inventory.setQuantity(inventory.getQuantity() + item.getQuantity());
                }
                userInventoryRepository.save(inventory);
            }

            // Step 5: 处理优惠券过期
            if (order.getCouponId() != null) {
                Coupon coupon = couponRepository.findByCouponIdAndUserId(order.getCouponId(), order.getUserId());
                if (coupon != null) {
                    coupon.setUsed(true);
                    couponRepository.save(coupon);
                }
            }

            // Step 6: 更新订单状态为已支付
            order.setStatus("PAID");
            orderRepository.save(order);

            return Result.success();

        } catch (Exception e) {
            // 回滚事务
            return Result.failure(ResultCode.USER_LOGIN_ERROR, e.getMessage());
        }
    }

    private void reportPaymentDetails(Order order) {
        // 构建货物流向对象
        GoodsFlow goodsFlow = new GoodsFlow();
        goodsFlow.setOrderId(order.getOrderId());
        for (OrderItem orderItem :order.getItems()) {
            goodsFlow.addProductsId(orderItem.getProductId());
            goodsFlow.addQuantities(orderItem.getQuantity());
        }

        // 发送请求到报表模块
        Result result = restTemplate.build().postForObject(GOODS_REPORT_URL, goodsFlow, Result.class);

        if (result != null && Objects.equals(result.getCode(), ResultCode.SUCCESS.getCode())) {
            log.info("货物流向上报成功");
        } else {
            log.warn("货物流向上报失败: " + (result != null ? result.getMessage() : "未知错误"));
        }
    }
}
