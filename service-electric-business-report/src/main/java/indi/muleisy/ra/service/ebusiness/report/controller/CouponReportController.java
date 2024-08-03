package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.CouponFlow;
import indi.muleisy.ra.service.ebusiness.report.service.CouponReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/coupon")
public class CouponReportController {

    @Autowired
    private CouponReportService couponReportService;

    @PostMapping("/flow")
    public Result reportCouponFlow(@RequestBody CouponFlow couponFlow) {
        return couponReportService.reportCouponFlow(couponFlow);
    }
}
