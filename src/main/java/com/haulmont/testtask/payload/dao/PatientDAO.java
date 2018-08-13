package com.haulmont.testtask.payload.dao;

import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.modal.ModalComponent;
import com.haulmont.testtask.ui.table.TableComponent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "patients")
public class PatientDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false, insertable = false, updatable = false, unique = true)
    @ComponentName("id")
    @TableComponent(order = 0, render = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @ComponentName("Фамилия")
    @TableComponent(order = 1, render = true)
    @ModalComponent
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    @ComponentName("Имя")
    @TableComponent(order = 2, render = true)
    @ModalComponent
    private String middleName;

    @Column(name = "last_name", nullable = false)
    @ComponentName("Отчество")
    @TableComponent(order = 3, render = true)
    @ModalComponent
    private String lastName;

    @Column(name = "phone")
    @ComponentName("Телефон")
    @TableComponent(order = 4, render = true)
    @ModalComponent(componentType = ModalComponent.Type.PHONE_FIELD)
    private String phone;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDAO)) return false;
        PatientDAO that = (PatientDAO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
