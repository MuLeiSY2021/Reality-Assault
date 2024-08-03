package indi.muleisy.ra.service.ebusiness.payment.model;

import lombok.Data;

@Data
public class UserBalance {
    private String userId;
    private String currencyType;
    private double balance;
}

