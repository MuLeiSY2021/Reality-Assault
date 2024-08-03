package indi.muleisy.ra.service.ebusiness.order.listener;

import indi.muleisy.ra.service.ebusiness.order.service.OrderExpiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class OrderExpiryListener implements MessageListener {

    @Autowired
    private OrderExpiryService orderExpiryService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredOrderId = message.toString();
        orderExpiryService.handleExpiredOrder(expiredOrderId);
    }
}
