package indi.muleisy.ra.service.ebusiness.report.repository;

import indi.muleisy.ra.service.ebusiness.report.model.DiamondFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiamondFlowRepository extends JpaRepository<DiamondFlow, String> {
}
