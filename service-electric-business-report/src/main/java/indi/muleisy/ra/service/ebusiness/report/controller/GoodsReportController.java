package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.GoodsFlow;
import indi.muleisy.ra.service.ebusiness.report.service.GoodsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/goods")
public class GoodsReportController {

    @Autowired
    private GoodsReportService goodsReportService;

    @PostMapping("/flow")
    public Result reportGoodsFlow(@RequestBody GoodsFlow goodsFlow) {
        return goodsReportService.reportGoodsFlow(goodsFlow);
    }
}
