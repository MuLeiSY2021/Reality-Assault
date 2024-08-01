package indi.muleisy.ra.battle.dao.spring;

import indi.muleisy.ra.pub.redis.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GoodDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Good get(Integer id) {
        return (Good) mongoTemplate.find(new Query(Criteria.where("id").is(id)), Good.class);
    }
}
