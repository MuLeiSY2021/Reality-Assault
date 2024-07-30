package indi.muleisy.ra.battle.data;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "knife")
@Data
public class Knife {
    @Indexed
    private Long id;
    private Short damage;
}
