package indi.muleisy.ra.battle.data;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "support")
@Data
public class Support {
    @Indexed
    private Long id;
    private Byte type;
}
