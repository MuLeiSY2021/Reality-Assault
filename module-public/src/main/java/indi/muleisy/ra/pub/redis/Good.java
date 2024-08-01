package indi.muleisy.ra.pub.redis;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "good")
@Data
public class Good {
    @Indexed
    private Long id;
    private Byte type;
    private String tableId;
    private Integer cost;
    private Boolean buyable;
}
