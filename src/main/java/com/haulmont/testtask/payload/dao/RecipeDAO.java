package com.haulmont.testtask.payload.dao;


import com.haulmont.testtask.converter.LocalDateConverter;
import com.haulmont.testtask.payload.RecipePriority;
import com.haulmont.testtask.repository.PatientRepository;
import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.annotation.ToString;
import com.haulmont.testtask.ui.modal.ComponentType;
import com.haulmont.testtask.ui.modal.ModalComponent;
import com.haulmont.testtask.ui.table.TableComponent;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "recipes")
public class RecipeDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false, insertable = false, updatable = false, unique = true)
    @ComponentName("id")
    @TableComponent(order = 0, render = false)
    private Long id;

    @Column(name = "description", nullable = false)
    @ComponentName("Описание")
    @TableComponent(order = 1, render = true)
//    @ModalComponent(componentType = ComponentType.TEXT_AREA)
    private String description;

    @ManyToOne(optional = false, targetEntity = PatientDAO.class)
    @JoinColumn(name = "patient", foreignKey = @ForeignKey(name = "patient_id"))
    @ComponentName("Пациент")
    @TableComponent(order = 2, render = true,
            string = @ToString(parameter = {"getFirstName", "getMiddleName", "getLastName"}))
//    @ModalComponent(
//            componentType = ComponentType.COMBO_BOX,
//            object = PatientRepository.class,
//            dataSource = "findAll",
//            string = @ToString(parameter = {"getFirstName", "getMiddleName", "getLastName"}))
    private PatientDAO patient;

    @ManyToOne(optional = false, targetEntity = DoctorDAO.class)
    @JoinColumn(name = "doctor", foreignKey = @ForeignKey(name = "doctor_id"))
    @ComponentName("Врач")
    @TableComponent(order = 3, render = true,
            string = @ToString(parameter = {"getFirstName", "getMiddleName", "getLastName"}))
    //@ModalComponent//(componentType = ModalComponent.Type.COMBO_BOX)
    private DoctorDAO doctor;

    @Column(name = "date_create", nullable = false)
    @ComponentName("Дата создания")
    @TableComponent(order = 4, render = true)
    //@ModalComponent(componentType = ComponentType.DATE)
    @Convert(converter = LocalDateConverter.class)
    private LocalDate dateCreate;

    @Column(name = "duration", nullable = false)
    @ComponentName("Срок действия")
    @TableComponent(order = 5, render = true)
    //@ModalComponent(componentType = ComponentType.DATE, isRequire = true)
    @Convert(converter = LocalDateConverter.class)
    private LocalDate duration;

    @Column(name = "priority", nullable = false)
    @ComponentName("Приоритет")
    @TableComponent(order = 6, render = true)
    //@ModalComponent(componentType = ModalComponent.Type.COMBO_BOX)
    private RecipePriority priority;

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

    public PatientDAO getPatient() {
        return this.patient;
    }

    public void setPatient(PatientDAO patient) {
        this.patient = patient;
    }

    public DoctorDAO getDoctor() {
        return this.doctor;
    }

    public void setDoctor(DoctorDAO doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDate getDuration() {
        return this.duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    public RecipePriority getPriority() {
        return this.priority;
    }

    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeDAO)) return false;
        RecipeDAO recipeDAO = (RecipeDAO) o;
        return Objects.equals(getId(), recipeDAO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
