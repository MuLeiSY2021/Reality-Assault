package indi.muleisy.ra.service.user.repository;

import indi.muleisy.ra.service.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserInfoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    public User findUserById(String id) {
        return mongoTemplate.findById(id, User.class, "users");
    }

    public void saveUser(User user) {
        mongoTemplate.save(user, "users");
    }

    public User findByEmailOrPhoneOrOpenId(String identifier) {
        return mongoTemplate.findOne(
                Query.query(
                        Criteria.where("email").is(identifier)
                                .orOperator(
                                        Criteria.where("phone").is(identifier),
                                        Criteria.where("username").is(identifier),
                                        Criteria.where("QQOpenId").is(identifier),
                                        Criteria.where("WeChatOpenId").is(identifier),
                                        Criteria.where("GitHubOpenId").is(identifier)
                                )
                ), User.class, "users"
        );
    }

    public Optional<User> findById(String id) {
        User user = mongoTemplate.findById(id, User.class, "users");
        return Optional.ofNullable(user);
    }

    public void save(User user) {
        mongoTemplate.save(user, "users");
    }
}
