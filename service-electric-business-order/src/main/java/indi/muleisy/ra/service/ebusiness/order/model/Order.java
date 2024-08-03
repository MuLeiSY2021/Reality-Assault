package indi.muleisy.ra.service.ebusiness.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String orderId; // 订单ID
    private String userId; // 用户ID
    private List<OrderItem> items; // 订单项列表
    private double totalPrice; // 总价格
    private double discountedPrice; // 折扣后的价格
    private String deliveryInfo; // 送货信息
    private Date orderTime; // 下单时间
    private String status; // 订单状态 (PENDING, PAID, FAILED, EXPIRED)
    private String couponId; // 使用的优惠券ID
    private String paymentToken; // 支付Token
}
