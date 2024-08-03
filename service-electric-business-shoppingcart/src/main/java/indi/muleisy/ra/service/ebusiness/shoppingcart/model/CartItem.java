package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private int quantity;
    private double price;
}
