package indi.muleisy.ra.battle.dao;

import indi.muleisy.ra.battle.data.Knife;
import indi.muleisy.ra.battle.data.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class WeaponDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Weapon get(String id) {
        return mongoTemplate.findById(id, Weapon.class);
    }
}
