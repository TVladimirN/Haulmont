package com.haulmont.testtask.config;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.dao.RecipeDAO;
import com.haulmont.testtask.ui.table.CommonTable;
import com.vaadin.ui.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public class UiConfig {

    @Bean("patientsTable")
    public Component patientsTable(@Autowired @Qualifier("patientRepository") CrudRepository repository) {
        CommonTable commonTable = new CommonTable<>(PatientDAO.class);
        commonTable.setCaption("Пациенты");
        commonTable.setRepository(repository);
        return commonTable;
    }

    @Bean("doctorsTable")
    public Component doctorsTable(@Autowired @Qualifier("doctorRepository") CrudRepository repository) {
        CommonTable commonTable = new CommonTable<>(DoctorDAO.class);
        commonTable.setCaption("Врачи");
        commonTable.setRepository(repository);
        return commonTable;
    }

    @Bean("recipesTable")
    public Component recipesTable(@Autowired @Qualifier("recipeRepository") CrudRepository repository) {
        CommonTable commonTable = new CommonTable<>(RecipeDAO.class);
        commonTable.setCaption("Рецепты");
        commonTable.setRepository(repository);
        return commonTable;
    }

}
