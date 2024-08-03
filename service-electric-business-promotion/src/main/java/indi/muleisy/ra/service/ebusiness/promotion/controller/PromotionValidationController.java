package indi.muleisy.ra.service.ebusiness.promotion.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionValidation;
import indi.muleisy.ra.service.ebusiness.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
public class PromotionValidationController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/validate")
    public Result validatePromotion(@RequestBody PromotionValidation promotionValidation) {
        return promotionService.processPromotionValidation(promotionValidation);
    }
}
