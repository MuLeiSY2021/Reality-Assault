package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.DiamondFlow;
import indi.muleisy.ra.service.ebusiness.report.service.DiamondReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/diamond")
public class DiamondReportController {

    @Autowired
    private DiamondReportService diamondReportService;

    @PostMapping("/flow")
    public Result reportDiamondFlow(@RequestBody DiamondFlow diamondFlow) {
        return diamondReportService.reportDiamondFlow(diamondFlow);
    }
}
