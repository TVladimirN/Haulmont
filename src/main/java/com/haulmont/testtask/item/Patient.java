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
                propertyId = "phone",
                type = String.class,
                defaultValue = "",
                header = "Телефон"
        ),

})
public class Patient implements Item {

    private static final String FIRST_NAME_ID = "firstName";
    private static final String MIDDLE_NAME_ID = "middleName";
    private static final String LAST_NAME_ID = "lastName";
    private static final String PHONE_ID = "phone";

    private Map<Object, Object> properties;

    public Patient() {
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

    public String getPhone() {
        return (String) this.properties.get(PHONE_ID);
    }

    public void setPhone(String phone) {
        this.properties.put(PHONE_ID, phone);
    }


    @Override
    public Collection<?> getIds() {
        return this.properties.keySet();
    }

    @Override
    public Object getValueById(Object o) {
        return this.properties.get(o);
    }

}
