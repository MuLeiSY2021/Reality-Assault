package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class GoodsFlow {
    private String flowId = generateFlowId();
    private String orderId;
    private List<String> productsId = new LinkedList<>();
    private List<Integer> quantities = new LinkedList<>();
    private Date flowTime = new Date();

    private String generateFlowId() {
        // 根据需要生成唯一的Flow ID，可以使用UUID或其他方式
        return UUID.randomUUID().toString();
    }

    public void addProductsId(String productId) {
        this.productsId.add(productId);
    }

    public void addQuantities(int quantity) {
        this.quantities.add(quantity);
    }
}
