package com.haulmont.testtask.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Item.ItemContainerInfos({
        @Item.ItemContainerInfo(
                propertyId = "firstName",
                type = String.class,
                defaultValue = "",
                header = "Фамилия"
        ),
        @Item.ItemContainerInfo(
                propertyId = "middleName",
                type = String.class,
                defaultValue = "",
                header = "Имя"
        ),
        @Item.ItemContainerInfo(
                propertyId = "lastName",
                type = String.class,
                defaultValue = "",
                header = "Отчество"
        ),
        @Item.ItemContainerInfo(
                propertyId = "specialization",
                type = String.class,
                defaultValue = "",
                header = "Специализация"
        ),

})
public class Doctor implements Item {

    private static final String FIRST_NAME_ID = "firstName";
    private static final String MIDDLE_NAME_ID = "middleName";
    private static final String LAST_NAME_ID = "lastName";
    private static final String SPECIALIZATION_ID = "specialization";

    private Map<Object, Object> properties;

    public Doctor() {
        this.properties = new HashMap<>();
    }

    public String getFirstName() {
        return (String) this.properties.get(FIRST_NAME_ID);
    }

    public void setFirstName(String firstName) {
        this.properties.put(FIRST_NAME_ID, firstName);
    }

    public String getMiddleName() {
        return (String) this.properties.get(MIDDLE_NAME_ID);
    }

    public void setMiddleName(String middleName) {
        this.properties.put(MIDDLE_NAME_ID, middleName);
    }

    public String getLastName() {
        return (String) this.properties.get(LAST_NAME_ID);
    }

    public void setLastName(String lastName) {
        this.properties.put(LAST_NAME_ID, lastName);
    }

    public String getSpecialization() {
        return (String) this.properties.get(SPECIALIZATION_ID);
    }

    public void setSpecialization(String specialization) {
        this.properties.put(SPECIALIZATION_ID, specialization);
    }

    @Override
    public Collection<?> getIds() {
        return null;
    }

    @Override
    public Object getValueById(Object o) {
        return null;
    }
}
