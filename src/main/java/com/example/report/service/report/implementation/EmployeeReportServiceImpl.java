package com.example.report.service.report.implementation;

import com.example.report.models.Employee;
import com.example.report.models.Head;
import com.example.report.models.entityreport.EmployeeReport;
import com.example.report.models.entityreport.subreport.Cabecera;
import com.example.report.models.entityreport.subreport.Paciente;
import com.example.report.service.EmployeeService;
import com.example.report.service.HeadService;
import com.example.report.service.PatientService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    HeadService headService;
    @Autowired
    PatientService patientService;

    public EmployeeReport addEmployee() {
        Employee employee = employeeService.findEntity();
        EmployeeReport employeeReport = new EmployeeReport();
        employeeReport.setFirstName(employee.getFirstName());
        employeeReport.setLastName(employee.getLastName());
        employeeReport.setSalary(employee.getSalary());
        return employeeReport;
    }

    public Cabecera addCabecera() {
        Head head = headService.findEntity();
        return new Cabecera(head.getTittle());
    }

    public List<Paciente> addListPatient() {
        return patientService.findAllEntity()
                .stream()
                .map(p -> {
                    Paciente paciente = new Paciente();
                    paciente.setId(p.getId());
                    paciente.setNombre(p.getFirstName());
                    paciente.setApellido(p.getLastName());
                    paciente.setFecha(p.getBirthdate());
                    paciente.setGenero(p.getGender());
                    paciente.setDni(p.getDni());
                    paciente.setDomicilio(p.getHabitualResidence());
                    paciente.setPoblacion(p.getPopulationProvince());
                    paciente.setProvincia(p.getProvince());
                    paciente.setNumHistoriaClinica(p.getClinicalRecordNumber());
                    return paciente;
                }).collect(Collectors.toList());
    }

    public JasperReport addJasperSubReport(String pathSubReport) {
        try {
            return JasperCompileManager.compileReport(getClass().getResourceAsStream(pathSubReport));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map getPacienteParameter() {
        Map<String, Object> mapPacienteParameter = new HashMap<>();
        mapPacienteParameter.put("pacienteDataset", new JRBeanCollectionDataSource(this.addListPatient()));
        return mapPacienteParameter;
    }

    @Override
    public JasperPrint reportMain() {
        try {
            InputStream employeeReportStream = getClass().getResourceAsStream("/report/PrincipalReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            EmployeeReport employeeReport = addEmployee();
            //dynamic parameters required for report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tituloReport", this.addJasperSubReport("/report/CabeceraSubReport.jrxml"));
            parameters.put("subreportParameter", new JRBeanCollectionDataSource(Collections.singleton(this.addCabecera())));
            parameters.put("pacienteReport", this.addJasperSubReport("/report/ListaPacienteSubReport.jrxml"));
            parameters.put("pacienteParameter", this.getPacienteParameter());
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(employeeReport));
            return JasperFillManager.fillReport(jasperReport, parameters, source);
        } catch (Exception e) {
            throw new RuntimeException("Error es :" + e.getMessage());
        }
    }
}
