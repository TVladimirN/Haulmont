package com.haulmont.testtask.repository;

import com.haulmont.testtask.payload.DoctorRecipeStatistic;
import com.haulmont.testtask.payload.dao.DoctorDAO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends MyRepository<DoctorDAO, Long> {

//    String DOCTOR_STATISTIC = "select concat_ws(' ', d.first_name, d.middle_name, d.last_name) as concat," +
//            " count(r.recipe_id) as num from doctors d join recipes r on r.doctor = d.doctor_id " +
//            "group by concat";

    String DOCTOR_STATISTIC = "select new com.haulmont.testtask.payload.DoctorRecipeStatistic(" +
            "d.firstName, d.middleName, d.lastName, count(r.id)) from doctors d join recipes r on d.id = r.doctor" +
            " group by d.firstName, d.middleName, d.lastName";

    @Query(value = DOCTOR_STATISTIC)
    List<DoctorRecipeStatistic> getStatistics();
}
