package com.example.report.models.entityreport;

import lombok.Data;

//mark class as an Entity

@Data
public class EmployeeReport {
    private Integer id;
    private String firstName;
    private String lastName;
    private String salary;
}
