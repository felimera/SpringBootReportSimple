package com.example.report.controller;

import com.example.report.service.report.EmployeeReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "api/report/")
public class EmployeeController {
    @Autowired
    EmployeeReportService employeeReportService;

    final String NAME_DOCUMENT = "employees-details";

    @GetMapping(path = "employee")
    public ResponseEntity<?> getReportEmployee(HttpServletResponse response, @RequestParam String format) throws JRException {
        HttpHeaders headers = new HttpHeaders();
        //set the PDF format
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline; filename=", NAME_DOCUMENT + ".pdf");
        JasperPrint empReport = this.employeeReportService.reportMain();
        if (format.equals("pdf"))
            return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
        else {
            exportExcel(empReport, response);
            return ResponseEntity.ok().build();
        }
    }

    public void exportExcel(JasperPrint jasperPrint, HttpServletResponse response) {
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setSheetNames(new String[]{"sheet1"});
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setHeader("Content-Disposition", "attachment;filename=" + NAME_DOCUMENT + ".xlsx");
            response.setContentType("application/octet-stream");
            exporter.exportReport();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
