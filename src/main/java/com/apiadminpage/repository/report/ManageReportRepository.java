package com.apiadminpage.repository.report;

import com.apiadminpage.entity.report.ManageReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageReportRepository extends JpaRepository<ManageReport, Integer> {
    ManageReport findByName(String name);
}
