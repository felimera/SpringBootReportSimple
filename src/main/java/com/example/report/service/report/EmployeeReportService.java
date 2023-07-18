package com.example.report.service.report;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeReportService {
    JasperPrint reportMain();
}
