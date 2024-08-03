package indi.muleisy.ra.service.ebusiness.order.repository;

import indi.muleisy.ra.service.ebusiness.order.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class UserVerifyRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User findUserById(String id) {
        return mongoTemplate.findById(id, User.class, "users");
    }

}
