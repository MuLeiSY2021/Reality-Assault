package indi.muleisy.ra.service.ebusiness.payment.repository;

import indi.muleisy.ra.service.ebusiness.payment.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, String> {
    UserBalance findByUserIdAndCurrencyType(String userId, String currencyType);
    List<UserBalance> findByUserId(String userId);
}
