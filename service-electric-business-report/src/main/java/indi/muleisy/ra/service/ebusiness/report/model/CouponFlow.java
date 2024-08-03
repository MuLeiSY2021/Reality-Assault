package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

import java.util.Date;

@Data
public class CouponFlow {
    private String flowId;
    private String couponId;
    private String orderId;
    private double discount;
    private Date flowTime;
}
