package indi.muleisy.ra.service.ebusiness.order.service;

import indi.muleisy.ra.service.ebusiness.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderExpiryService {

    @Autowired
    private OrderRepository orderRepository;

    public void handleExpiredOrder(String orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            // 更新订单状态为 "EXPIRED"
            order.setStatus("EXPIRED");
            orderRepository.save(order);
            // 可以在此处执行其他相关的业务逻辑，比如通知用户订单已过期
        });
    }
}
