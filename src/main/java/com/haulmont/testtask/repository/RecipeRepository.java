package com.haulmont.testtask.repository;

import com.haulmont.testtask.payload.dao.RecipeDAO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends MyRepository<RecipeDAO, Long> {

    String FIND_BY_PARAMS = "select r from recipes r where r.description like ?1 and r.patient " +
            "in (select p.id from patients p where concat_ws(p.firstName, p.middleName, p.lastName) like ?2) and" +
            " r.doctor in (select d.id from doctors d where " +
            "concat_ws(' ', d.firstName, d.middleName, d.lastName) like ?3)";

    @Query(value = FIND_BY_PARAMS)
    List<RecipeDAO> findByParams(String description, String patient, String doctor);

}
