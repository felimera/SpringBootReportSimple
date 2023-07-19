package com.example.report.repository;

import com.example.report.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReporsitory extends JpaRepository<Employee,Integer> {
}
