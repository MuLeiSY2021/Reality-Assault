package indi.muleisy.ra.battle.dao;

import indi.muleisy.ra.battle.data.Support;
import indi.muleisy.ra.battle.data.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SupportDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Support get(String id) {
        return mongoTemplate.findById(id, Support.class);
    }
}
