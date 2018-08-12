package com.haulmont.testtask.repository;

import com.haulmont.testtask.dao.RecipeDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<RecipeDAO, Long> {

    String FIND_BY_PARAMS = "select r from recipes r where r.description like ?1 and r.patient " +
            "in (select p.id from patients p where p.firstName + ' ' + p.middleName + ' ' +" +
            " p.lastName like ?2) and r.doctor in (select d.id from doctors d where " +
            "d.firstName + ' ' + d.middleName + ' ' + d.lastName like ?3)";

    @Query("select r from recipes r")
    List<RecipeDAO> findAll();

    @Query(value = FIND_BY_PARAMS)
    List<RecipeDAO> findByParams(String description, String patient, String doctor);

}
