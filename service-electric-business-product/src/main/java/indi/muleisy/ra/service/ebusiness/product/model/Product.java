package indi.muleisy.ra.service.ebusiness.product.model;

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
    private int stock; // 库存
}
