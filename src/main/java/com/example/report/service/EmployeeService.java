package com.example.report.service;

import com.example.report.models.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Employee findEntity();
}
