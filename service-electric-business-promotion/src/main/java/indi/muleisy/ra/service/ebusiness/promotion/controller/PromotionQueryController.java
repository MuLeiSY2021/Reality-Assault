package indi.muleisy.ra.service.ebusiness.promotion.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.promotion.model.PromotionQuery;
import indi.muleisy.ra.service.ebusiness.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
public class PromotionQueryController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/query")
    public Result queryPromotion(@RequestBody PromotionQuery promotionQuery) {
        return promotionService.processPromotionQuery(promotionQuery);
    }
}
