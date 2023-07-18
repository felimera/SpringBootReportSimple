package com.example.report.service.implementation;

import com.example.report.models.Employee;
import com.example.report.repository.EmployeeReporsitory;
import com.example.report.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeImplService implements EmployeeService {
    @Autowired
    EmployeeReporsitory employeeReporsitory;

    @Override
    public Employee findEntity() {
        List<Employee>employeeList= employeeReporsitory.findAll();
        return employeeReporsitory.findById(1).orElseThrow(() -> new RuntimeException("No se encontro el empleado solicitado."));
    }
}
