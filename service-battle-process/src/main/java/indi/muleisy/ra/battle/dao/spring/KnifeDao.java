package indi.muleisy.ra.battle.dao.spring;

import indi.muleisy.ra.battle.data.Knife;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KnifeDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Knife get(String id) {
        return mongoTemplate.findById(id, Knife.class);
    }
}
