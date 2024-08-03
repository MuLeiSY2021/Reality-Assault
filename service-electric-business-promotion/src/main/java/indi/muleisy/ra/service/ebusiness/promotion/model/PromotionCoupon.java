package indi.muleisy.ra.service.ebusiness.promotion.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PromotionCoupon {

    @Id
    private String couponCode;
    private String discountInfo;
    private boolean valid;

    public boolean isValid() {
        return valid;
    }
}
