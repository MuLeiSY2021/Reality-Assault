package indi.muleisy.ra.service.user.repository;

import indi.muleisy.ra.service.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MongoUserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(User user) {
        mongoTemplate.save(user, "users");
    }

    public User findUserById(String id) {
        return mongoTemplate.findById(id, User.class, "users");
    }

    public User findByEmailOrPhoneOrOpenId(String identifier){
        return mongoTemplate.findOne(
                Query.query(
                        Criteria.where("email").is(identifier)
                .orOperator(
                        Criteria.where("phone").is(identifier),
                        Criteria.where("username").is(identifier),
                        Criteria.where("QQOpenId").is(identifier)
                )
                ), User.class);
    }

}
