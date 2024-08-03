package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.GoldFlow;
import indi.muleisy.ra.service.ebusiness.report.service.GoldReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/gold")
public class GoldReportController {

    @Autowired
    private GoldReportService goldReportService;

    @PostMapping("/flow")
    public Result reportGoldFlow(@RequestBody GoldFlow goldFlow) {
        return goldReportService.reportGoldFlow(goldFlow);
    }
}
