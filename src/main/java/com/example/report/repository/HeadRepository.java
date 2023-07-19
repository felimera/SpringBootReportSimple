package com.example.report.repository;

import com.example.report.models.Head;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadRepository extends CrudRepository<Head, Integer> {
}
