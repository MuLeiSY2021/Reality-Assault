package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.GoldFlow;
import indi.muleisy.ra.service.ebusiness.report.repository.GoldFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoldReportService {

    @Autowired
    private GoldFlowRepository goldFlowRepository;

    public Result reportGoldFlow(GoldFlow goldFlow) {
        goldFlowRepository.save(goldFlow);
        return Result.success();
    }
}
