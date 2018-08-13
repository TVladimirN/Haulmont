package com.haulmont.testtask.repository;

import com.haulmont.testtask.payload.dao.DoctorDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<DoctorDAO, Long> {

    List<DoctorDAO> findAll();

}
