package com.example.report.service;

import com.example.report.models.Head;
import org.springframework.stereotype.Service;

@Service
public interface HeadService {
    Head findEntity();
}
