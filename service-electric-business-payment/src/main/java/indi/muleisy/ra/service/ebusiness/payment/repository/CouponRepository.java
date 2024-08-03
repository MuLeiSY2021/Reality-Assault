package indi.muleisy.ra.service.ebusiness.payment.repository;

import indi.muleisy.ra.service.ebusiness.payment.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    Coupon findByCouponIdAndUserId(String couponId, String userId);
}
