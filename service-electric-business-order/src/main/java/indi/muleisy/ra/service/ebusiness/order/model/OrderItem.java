package indi.muleisy.ra.service.ebusiness.order.model;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private int quantity;
    private double price;
    private double discount;
    private boolean isPhysical;
    private double diamondPrice;
    private double goldPrice;
}
