package indi.muleisy.ra.service.ebusiness.payment.repository;

import indi.muleisy.ra.service.ebusiness.payment.model.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInventoryRepository extends JpaRepository<UserInventory, String> {
    UserInventory findByUserIdAndProductId(String userId, String productId);
}
