package com.example.report.service;

import com.example.report.models.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<Patient> findAllEntity();
}
