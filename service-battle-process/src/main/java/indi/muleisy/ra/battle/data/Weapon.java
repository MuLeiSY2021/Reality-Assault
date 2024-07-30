package indi.muleisy.ra.battle.data;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "weapon")
@Data
public class Weapon {
    @Indexed
    private Long id;
    private Short clip;
    private Short damage;
    private Double speed;
    private Double recoil;
}
