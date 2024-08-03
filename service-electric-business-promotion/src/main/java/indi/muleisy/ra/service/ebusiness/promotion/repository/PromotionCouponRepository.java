package indi.muleisy.ra.service.ebusiness.promotion.repository;

import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionCouponRepository extends JpaRepository<PromotionCoupon, String> {
    PromotionCoupon findByCouponCode(String couponCode);
}
