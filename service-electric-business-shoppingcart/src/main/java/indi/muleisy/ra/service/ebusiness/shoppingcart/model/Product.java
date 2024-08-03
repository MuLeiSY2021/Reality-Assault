package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

@Data
public class Product {
    private String productId;
    private String name;
    private int quantity;
    private double price;
}
