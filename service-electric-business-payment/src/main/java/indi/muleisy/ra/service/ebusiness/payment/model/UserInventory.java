package indi.muleisy.ra.service.ebusiness.payment.model;

import lombok.Data;

@Data
public class UserInventory {
    private String userId;
    private String productId;
    private int quantity;
}
