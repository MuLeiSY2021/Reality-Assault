package indi.muleisy.ra.service.ebusiness.order.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.order.model.Order;
import indi.muleisy.ra.service.ebusiness.order.repository.OrderRepository;
import indi.muleisy.ra.service.ebusiness.order.repository.OrderExpiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderManagementService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderExpiryRepository orderExpiryRepository;

    public Result createOrder(Order order) {
        // 计算订单总价和折扣后价格
        order.setTotalPrice(calculateTotalPrice(order));
        order.setDiscountedPrice(calculateDiscountedPrice(order));

        // 设置初始状态
        order.setStatus("PENDING");
        orderRepository.save(order);

        // 注册订单到Redis，设置过期处理
        orderExpiryRepository.registerOrderExpiry(order.getOrderId());

        return Result.success(order.getOrderId());
    }

    public Result viewOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return Result.success(order.get());
        } else {
            return Result.failure(ResultCode.USER_NOT_EXISTED);
        }
    }

    public Result modifyOrder(Order order) {
        Optional<Order> existingOrder = orderRepository.findById(order.getOrderId());
        if (existingOrder.isPresent()) {
            orderRepository.save(order);
            return Result.success();
        } else {
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
    }

    private double calculateTotalPrice(Order order) {
        return order.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    private double calculateDiscountedPrice(Order order) {
        return order.getItems().stream().mapToDouble(item -> (item.getPrice() - item.getDiscount()) * item.getQuantity()).sum();
    }
}
