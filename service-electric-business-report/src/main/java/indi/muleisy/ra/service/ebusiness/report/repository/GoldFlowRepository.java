package indi.muleisy.ra.service.ebusiness.report.repository;

import indi.muleisy.ra.service.ebusiness.report.model.GoldFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldFlowRepository extends JpaRepository<GoldFlow, String> {
}
