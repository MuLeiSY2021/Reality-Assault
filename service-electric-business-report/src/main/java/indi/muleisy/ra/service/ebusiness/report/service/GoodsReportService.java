package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.report.model.GoodsFlow;
import indi.muleisy.ra.service.ebusiness.report.repository.GoodsFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsReportService {

    @Autowired
    private GoodsFlowRepository goodsFlowRepository;

    public Result reportGoodsFlow(GoodsFlow goodsFlow) {
        goodsFlowRepository.save(goodsFlow);
        return Result.success();
    }
}
