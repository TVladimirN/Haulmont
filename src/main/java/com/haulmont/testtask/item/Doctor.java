package com.haulmont.testtask.item;

import com.haulmont.testtask.payload.dao.DoctorDAO;
import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.modal.ModalComponent;
import com.haulmont.testtask.ui.table.TableComponent;

import java.util.List;
import java.util.stream.Collectors;

public class Doctor {

    @ComponentName("id")
    @TableComponent(order = 0, render = false)
    private Long id;
    @ComponentName("Фамилия")
    @TableComponent(order = 1, render = true)
    @ModalComponent
    private String firstName;
    @ComponentName("Имя")
    @TableComponent(order = 2, render = true)
    @ModalComponent
    private String middleName;
    @ComponentName("Отчество")
    @TableComponent(order = 3, render = true)
    @ModalComponent
    private String lastName;
    @ComponentName("Специализация")
    @TableComponent(order = 4, render = true)
    @ModalComponent
    private String specialization;

    public static Doctor convertDoctorDaoToDoctor(DoctorDAO doctor) {
        Doctor _doctor = new Doctor();
        _doctor.setId(doctor.getId());
        _doctor.setFirstName(doctor.getFirstName());
        _doctor.setMiddleName(doctor.getMiddleName());
        _doctor.setLastName(doctor.getLastName());
        _doctor.setSpecialization(doctor.getSpecialization());

        return _doctor;
    }

    public static List<Doctor> convertListDoctorDaoToListDoctor(List<DoctorDAO> doctors) {
        return doctors.stream().map(Doctor::convertDoctorDaoToDoctor).collect(Collectors.toList());
    }

    public static DoctorDAO converDoctorToDoctorDAO(Doctor doctor){
        DoctorDAO doctorDAO = new DoctorDAO();
        doctorDAO.setId(doctor.getId());
        doctorDAO.setFirstName(doctor.getFirstName());
        doctorDAO.setMiddleName(doctor.getMiddleName());
        doctorDAO.setLastName(doctor.getLastName());
        doctorDAO.setSpecialization(doctor.getSpecialization());

        return doctorDAO;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }
}
