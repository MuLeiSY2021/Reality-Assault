package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

import java.util.Date;

@Data
public class MoneyFlow {
    private String flowId;
    private String orderId;
    private String paymentMethod;
    private double amount;
    private Date flowTime;
}
