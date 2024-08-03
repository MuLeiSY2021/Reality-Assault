package indi.muleisy.ra.service.ebusiness.report.service;

import indi.muleisy.ra.pub.rpc.Result;
import indi.muleisy.ra.service.ebusiness.report.model.DiamondFlow;
import indi.muleisy.ra.service.ebusiness.report.repository.DiamondFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiamondReportService {

    @Autowired
    private DiamondFlowRepository diamondFlowRepository;

    public Result reportDiamondFlow(DiamondFlow diamondFlow) {
        diamondFlowRepository.save(diamondFlow);
        return Result.success();
    }
}
