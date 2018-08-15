package com.haulmont.testtask.config;

import com.haulmont.testtask.payload.dao.DoctorDAO;
import com.haulmont.testtask.payload.dao.PatientDAO;
import com.haulmont.testtask.spring.RepositoryService;
import com.haulmont.testtask.ui.table.CommonTable;
import com.haulmont.testtask.ui.table.recipe.RecipeTable;
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

//    @Bean("recipesTable")//fixme пока не работает
//    public Component recipesTable(@Autowired @Qualifier("recipeRepository") CrudRepository repository) {
//        CommonTable commonTable = new CommonTable<>(RecipeDAO.class);
//        commonTable.setCaption("Рецепты");
//        commonTable.setRepository(repository);
//        return commonTable;
//    }

    @Bean
    public Component recipeTable(
            @Autowired @Qualifier("recipeRepository") CrudRepository repository,
            @Autowired RepositoryService repositoryService) {
        RecipeTable recipeTable = new RecipeTable();
        recipeTable.setCaption("Рецепты");
        recipeTable.setRepository(repository);
        recipeTable.setRepositoryService(repositoryService);
        return recipeTable;
    }

}
