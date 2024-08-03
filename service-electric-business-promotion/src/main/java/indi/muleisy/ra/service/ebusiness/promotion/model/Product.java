package indi.muleisy.ra.service.ebusiness.promotion.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "product")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int searchWeight; // 查询权重
}
