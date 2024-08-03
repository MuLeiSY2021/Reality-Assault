package indi.muleisy.ra.service.ebusiness.product.repository;

import indi.muleisy.ra.service.ebusiness.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
