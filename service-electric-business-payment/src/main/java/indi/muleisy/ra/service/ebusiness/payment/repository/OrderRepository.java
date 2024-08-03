package indi.muleisy.ra.service.ebusiness.payment.repository;

import indi.muleisy.ra.service.ebusiness.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
