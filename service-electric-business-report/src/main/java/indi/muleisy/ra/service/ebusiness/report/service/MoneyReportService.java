package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.pub.rpc.ResultCode;
import indi.muleisy.ra.service.ebusiness.report.model.MoneyFlow;
import indi.muleisy.ra.service.ebusiness.report.repository.MoneyFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyReportService {

    @Autowired
    private MoneyFlowRepository moneyFlowRepository;

    public Result reportMoneyFlow(MoneyFlow moneyFlow) {
        moneyFlowRepository.save(moneyFlow);
        return Result.success();
    }
}
