package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.CouponFlow;
import indi.muleisy.ra.service.ebusiness.report.repository.CouponFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponReportService {

    @Autowired
    private CouponFlowRepository couponFlowRepository;

    public Result reportCouponFlow(CouponFlow couponFlow) {
        couponFlowRepository.save(couponFlow);
        return Result.success();
    }
}
