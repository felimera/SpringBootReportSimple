package com.example.report.service.implementation;

import com.example.report.models.Head;
import com.example.report.repository.HeadRepository;
import com.example.report.service.HeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeadImplService implements HeadService {
    @Autowired
    HeadRepository headRepository;

    @Override
    public Head findEntity() {
        return headRepository.findById(1).orElseThrow(() -> new RuntimeException("No se encontro el registro de la cabecera."));
    }
}
