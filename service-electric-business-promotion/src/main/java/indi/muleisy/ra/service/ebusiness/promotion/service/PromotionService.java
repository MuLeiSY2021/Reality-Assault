package indi.muleisy.ra.service.ebusiness.promotion.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionQuery;
import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionValidation;
import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionCoupon;
import indi.muleisy.ra.service.ebusiness.promotion.repository.PromotionCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PromotionService {

    @Autowired
    private PromotionCouponRepository promotionCouponRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String COUPON_REPORT_URL = "http://report-service/report/coupon/flow";

    public Result processPromotionQuery(PromotionQuery promotionQuery) {
        PromotionCoupon promotionCoupon = promotionCouponRepository.findByCouponCode(promotionQuery.getCouponCode());

        if (promotionCoupon == null) {
            return Result.failure(ResultCode.USER_NOT_EXISTED);
        }

        return Result.success(promotionCoupon.getDiscountInfo());
    }

    public Result processPromotionValidation(PromotionValidation promotionValidation) {
        PromotionCoupon promotionCoupon = promotionCouponRepository.findByCouponCode(promotionValidation.getCouponCode());

        if (promotionCoupon == null || !promotionCoupon.isValid()) {
            handleExpiredCoupon(promotionValidation.getCouponCode());
            return Result.failure(ResultCode.USER_VERIFY_ERROR);
        }

        return Result.success();
    }

    private void handleExpiredCoupon(String couponCode) {
        PromotionCoupon promotionCoupon = promotionCouponRepository.findByCouponCode(couponCode);
        if (promotionCoupon != null) {
            promotionCoupon.setValid(false);
            promotionCouponRepository.save(promotionCoupon);
            reportExpiredCoupon(promotionCoupon);
        }
    }

    private void reportExpiredCoupon(PromotionCoupon promotionCoupon) {
        Result result = restTemplate.postForObject(COUPON_REPORT_URL, promotionCoupon, Result.class);

        if (result != null && result.getCode() == ResultCode.SUCCESS.getCode()) {
            System.out.println("促销券失效上报成功");
        } else {
            System.err.println("促销券失效上报失败: " + (result != null ? result.getMessage() : "未知错误"));
        }
    }
}
