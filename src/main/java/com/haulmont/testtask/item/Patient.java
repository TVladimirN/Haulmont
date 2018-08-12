package com.haulmont.testtask.item;

import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.modal.ModalComponent;
import com.haulmont.testtask.ui.table.TableComponent;

import java.util.List;
import java.util.stream.Collectors;

public class Patient implements Item {

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
    @ComponentName("Телефон")
    @TableComponent(order = 4, render = true)
    @ModalComponent(componentType = ModalComponent.Type.PHONE_FIELD)
    private String phone;

    public static Patient convertPatientDaoToPatient(PatientDAO patient) {
        Patient _patient = new Patient();
        _patient.setId(patient.getId());
        _patient.setFirstName(patient.getFirstName());
        _patient.setMiddleName(patient.getMiddleName());
        _patient.setLastName(patient.getLastName());
        _patient.setPhone(patient.getPhone());

        return _patient;
    }

    public static List<Patient> convertListPatientDaoToListPatient(List<PatientDAO> patients) {
        return patients.stream().map(Patient::convertPatientDaoToPatient).collect(Collectors.toList());
    }

    public static PatientDAO convertPatientItemToPatientDAO(Patient patient) {
        PatientDAO _paPatient = new PatientDAO();
        _paPatient.setId(patient.getId());
        _paPatient.setFirstName(patient.getFirstName());
        _paPatient.setMiddleName(patient.getMiddleName());
        _paPatient.setLastName(patient.getLastName());
        _paPatient.setPhone(patient.getPhone());

        return _paPatient;
    }

    public Patient() {
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }

    @Override
    public PatientDAO converItemToDao() {
        return convertPatientItemToPatientDAO(this);
    }

    @Override
    public void fillItemFromDAO(Object object) {
        PatientDAO patientDAO = (PatientDAO) object;
        setId(patientDAO.getId());
        setFirstName(patientDAO.getFirstName());
        setMiddleName(patientDAO.getMiddleName());
        setLastName(patientDAO.getLastName());
        setPhone(patientDAO.getPhone());

    }
}
