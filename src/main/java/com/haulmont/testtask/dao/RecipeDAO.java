package com.haulmont.testtask.dao;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "recipes")
public class RecipeDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false, insertable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    //    @OneToOne(optional = false, targetEntity = Patient.class)
    @ManyToOne(optional = false, targetEntity = Patient.class)
    @JoinColumn(name = "patient", foreignKey = @ForeignKey(name = "patient_id"))
    private Patient patient;
    //    @OneToOne(optional = false, targetEntity = DoctorDAO.class)
    @ManyToOne(optional = false, targetEntity = DoctorDAO.class)
    @JoinColumn(name = "doctor", foreignKey = @ForeignKey(name = "doctor_id"))
    private DoctorDAO doctor;
    @Column(name = "date_create", nullable = false)
    private Date dateCreate;
    @Column(name = "duration", nullable = false)
    private Date duration;
    @Column(name = "priority", nullable = false)
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

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DoctorDAO getDoctor() {
        return this.doctor;
    }

    public void setDoctor(DoctorDAO doctor) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeDAO)) return false;
        RecipeDAO that = (RecipeDAO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(description, that.description) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(doctor, that.doctor) &&
                Objects.equals(dateCreate, that.dateCreate) &&
                Objects.equals(duration, that.duration) &&
                priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), description, patient, doctor, dateCreate, duration, priority);
    }

}
