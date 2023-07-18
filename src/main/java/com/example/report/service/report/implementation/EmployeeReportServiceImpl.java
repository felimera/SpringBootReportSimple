package com.example.report.service.report.implementation;

import com.example.report.models.Employee;
import com.example.report.models.entityreport.EmployeeReport;
import com.example.report.models.entityreport.subreport.Cabecera;
import com.example.report.service.EmployeeService;
import com.example.report.service.report.EmployeeReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {
    @Autowired
    EmployeeService employeeService;

    public EmployeeReport addEmployee() {
        Employee employee = employeeService.findEntity();
        EmployeeReport employeeReport = new EmployeeReport();
        employeeReport.setFirstName(employee.getFirstName());
        employeeReport.setLastName(employee.getLastName());
        employeeReport.setSalary(employee.getSalary());
        return employeeReport;
    }

    public Cabecera addCabecera() {
        return new Cabecera("Titulo de la cabecera");
    }

    public JasperReport addJasperSubReport() {
        String pathSubReport = "/report/CabeceraSubReport.jrxml";
        try {
            return JasperCompileManager.compileReport(getClass().getResourceAsStream(pathSubReport));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JasperPrint reportMain() {
        try {
            InputStream employeeReportStream = getClass().getResourceAsStream("/report/PrincipalReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            EmployeeReport employeeReport = addEmployee();
            //dynamic parameters required for report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloReport", this.addJasperSubReport());
            parameters.put("subreportParameter", new JRBeanCollectionDataSource(Collections.singleton(this.addCabecera())));
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(employeeReport));
            return JasperFillManager.fillReport(jasperReport, parameters, source);
        } catch (Exception e) {
            throw new RuntimeException("Error es :" + e.getMessage());
        }
    }
}
