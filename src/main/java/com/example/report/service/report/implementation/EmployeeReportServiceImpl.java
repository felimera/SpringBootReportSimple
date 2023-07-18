package com.example.report.service.report.implementation;

import com.example.report.models.entityreport.Employee;
import com.example.report.models.entityreport.subreport.Cabecera;
import com.example.report.service.report.EmployeeReportService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {
    public Employee addEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Natalia");
        employee.setLastName("Gómez");
        employee.setSalary("1200");
        return employee;
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
            Employee employee = addEmployee();
            //dynamic parameters required for report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloReport", this.addJasperSubReport());
            parameters.put("subreportParameter", new JRBeanCollectionDataSource(Collections.singleton(this.addCabecera())));
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(employee));
            return JasperFillManager.fillReport(jasperReport, parameters, source);
        } catch (Exception e) {
           throw new RuntimeException("Error es :"+e.getMessage());
        }
    }
}
