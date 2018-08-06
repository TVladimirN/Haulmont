package com.haulmont.testtask.item;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Item.ItemContainerInfos({
        @Item.ItemContainerInfo(
                propertyId = "description",
                type = String.class,
                defaultValue = "",
                header = "Описание"
        ),
        @Item.ItemContainerInfo(
                propertyId = "patient",
                type = String.class,
                defaultValue = "",
                header = "Пациент"
        ),
        @Item.ItemContainerInfo(
                propertyId = "doctor",
                type = String.class,
                defaultValue = "",
                header = "Врач"
        ),
        @Item.ItemContainerInfo(
                propertyId = "dateCreate",
                type = String.class,
                defaultValue = "",
                header = "Дата создания"
        ),
        @Item.ItemContainerInfo(
                propertyId = "duration",
                type = String.class,
                defaultValue = "",
                header = "Срок действия"
        ),
        @Item.ItemContainerInfo(
                propertyId = "priority",
                type = String.class,
                defaultValue = "",
                header = "Приоритет"
        ),

})
public class Recept implements Item {

    private static final String DESCRIPTION_ID = "description";
    private static final String PATIENT_ID = "patient";
    private static final String DOCTOR_ID = "doctor";
    private static final String DATE_CREATE_ID = "dateCreate";
    private static final String DURATION_ID = "duration";
    private static final String PRIORITY_ID = "priority";

    private Map<Object, Object> properties = new HashMap<>();

    public String getDescription() {
        return (String) this.properties.get(DESCRIPTION_ID);
    }

    public void setDescription(String description) {
        this.properties.put(DESCRIPTION_ID, description);
    }

    public Patient getPatient() {
        return (Patient) this.properties.get(PATIENT_ID);
    }

    public void setPatient(Patient patient) {
        this.properties.put(PATIENT_ID, patient);
    }

    public Doctor getDoctor() {
        return (Doctor) this.properties.get(DOCTOR_ID);
    }

    public void setDoctor(Doctor doctor) {
        this.properties.put(DOCTOR_ID, doctor);
    }

    public Date getDateCreate() {
        return (Date) this.properties.get(DATE_CREATE_ID);
    }

    public void setDateCreate(Date dateCreate) {
        this.properties.put(DATE_CREATE_ID, dateCreate);
    }

    public Date getDuration() {
        return (Date) this.properties.get(DURATION_ID);
    }

    public void setDuration(Date duration) {
        this.properties.put(DURATION_ID, duration);
    }

    public Priority getPriority() {
        return (Priority) this.properties.get(PRIORITY_ID);
    }

    public void setPriority(Priority priority) {
        this.properties.put(PRIORITY_ID, priority);
    }

    @Override
    public Collection<?> getIds() {
        return null;
    }

    @Override
    public Object getValueById(Object o) {
        return null;
    }

    enum Priority {
        NORMAL,
        CITO,
        STATIM
    }
}
