package com.haulmont.testtask.repository;

import com.haulmont.testtask.payload.dao.PatientDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientDAO, Long> {

    List<PatientDAO> findAll();

}
