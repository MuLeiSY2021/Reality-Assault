package indi.muleisy.ra.service.ebusiness.report.controller;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.MoneyFlow;
import indi.muleisy.ra.service.ebusiness.report.service.MoneyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report/money")
public class MoneyReportController {

    @Autowired
    private MoneyReportService moneyReportService;

    @PostMapping("/flow")
    public Result reportMoneyFlow(@RequestBody MoneyFlow moneyFlow) {
        return moneyReportService.reportMoneyFlow(moneyFlow);
    }
}
