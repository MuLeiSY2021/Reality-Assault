package indi.muleisy.ra.service.ebusiness.report.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "shopping_cart")
public class ShoppingCart {
    @Id
    private String userId;
    private List<CartItem> items;
}
