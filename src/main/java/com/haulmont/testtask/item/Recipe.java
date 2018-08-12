package com.haulmont.testtask.item;

import com.haulmont.testtask.dao.RecipeDAO;
import com.haulmont.testtask.dao.RecipePriority;
import com.haulmont.testtask.repository.PatientRepository;
import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.modal.ModalComponent;
import com.haulmont.testtask.ui.table.TableComponent;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Recipe {

    @ComponentName("id")
    @TableComponent(order = 0, render = false)
    private Long id;
    @ComponentName("Описание")
    @TableComponent(order = 1, render = true)
    @ModalComponent(componentType = ModalComponent.Type.TEXT_AREA)
    private String description;
    @ComponentName("Пациент")
    @TableComponent(order = 2, render = true)
    @ModalComponent(
            componentType = ModalComponent.Type.COMBO_BOX,
            object = PatientRepository.class,
            dataSource = "findAll")
    private Patient patient;
    @ComponentName("Врач")
    @TableComponent(order = 3, render = true)
    @ModalComponent//(componentType = ModalComponent.Type.COMBO_BOX)
    private Doctor doctor;
    @ComponentName("Дата создания")
    @TableComponent(order = 4, render = true)
    @ModalComponent(componentType = ModalComponent.Type.DATE)
    private Date dateCreate;
    @ComponentName("Срок действия")
    @TableComponent(order = 5, render = true)
    @ModalComponent(componentType = ModalComponent.Type.DATE)
    private Date duration;
    @ComponentName("Приоритет")
    @TableComponent(order = 6, render = true)
    @ModalComponent//(componentType = ModalComponent.Type.COMBO_BOX)
    private RecipePriority priority;

    public static Recipe convertRecipeDaoToRecipe(RecipeDAO recipe) {
        Recipe _recipe = new Recipe();
        _recipe.setId(recipe.getId());
        _recipe.setDescription(recipe.getDescription());
        _recipe.setPatient(Patient.convertPatientDaoToPatient(recipe.getPatient()));
        _recipe.setDoctor(Doctor.convertDoctorDaoToDoctor(recipe.getDoctor()));
        _recipe.setDateCreate(recipe.getDateCreate());
        _recipe.setDuration(recipe.getDuration());
        _recipe.setPriority(recipe.getPriority());

        return _recipe;
    }

    public static List<Recipe> convertListRecipeDaoToListRecipe(List<RecipeDAO> recipes) {
        return recipes.stream().map(Recipe::convertRecipeDaoToRecipe).collect(Collectors.toList());
    }

    public static RecipeDAO convertRecipeItemToRecipeDAO(Recipe recipe){
        RecipeDAO recipeDAO = new RecipeDAO();
        recipeDAO.setId(recipe.getId());
        recipeDAO.setDescription(recipe.getDescription());
        recipeDAO.setPatient(Patient.convertPatientItemToPatientDAO(recipe.getPatient()));
        recipeDAO.setDoctor(Doctor.converDoctorToDoctorDAO(recipe.getDoctor()));
        recipeDAO.setDateCreate(recipe.getDateCreate());
        recipeDAO.setDuration(recipe.getDuration());
        recipeDAO.setPriority(recipe.getPriority());
        return recipeDAO;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDuration() {
        return this.duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public RecipePriority getPriority() {
        return this.priority;
    }

    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", dateCreate=" + dateCreate +
                ", duration=" + duration +
                ", priority=" + priority +
                '}';
    }
}
