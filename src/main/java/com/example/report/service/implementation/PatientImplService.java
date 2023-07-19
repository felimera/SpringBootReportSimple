package com.example.report.service.implementation;

import com.example.report.models.Patient;
import com.example.report.repository.PatientRepository;
import com.example.report.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientImplService implements PatientService {
    @Autowired
    PatientRepository patientRepository;
    @Override
    public List<Patient> findAllEntity() {
        return patientRepository.findAll();
    }
}
