package com.example.report.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PATIENT")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIRTHDATE")
    private String birthdate;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "DNI")
    private String dni;
    @Column(name = "HABITUAL_RESIDENCE")
    private String habitualResidence;
    @Column(name = "POPULATION_PROVINCE")
    private String populationProvince;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "CLINICAL_RECORD_NUMBER")
    private String clinicalRecordNumber;
}
