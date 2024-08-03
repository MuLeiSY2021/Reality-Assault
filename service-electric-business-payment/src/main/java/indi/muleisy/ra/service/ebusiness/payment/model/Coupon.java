package indi.muleisy.ra.service.ebusiness.payment.model;

import lombok.Data;

@Data
public class Coupon {
    private String couponId;
    private String userId;
    private boolean isUsed;
}
