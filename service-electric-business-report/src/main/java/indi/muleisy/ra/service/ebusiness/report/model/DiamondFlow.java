package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

import java.util.Date;

@Data
public class DiamondFlow {
    private String flowId;
    private String orderId;
    private double amount;
    private Date flowTime;
}
