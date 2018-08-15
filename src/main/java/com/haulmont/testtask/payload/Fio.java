package com.haulmont.testtask.payload;

import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class Fio {

    private final String firstName;
    private final String middleName;
    private final String lastName;

    public Fio(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public static Fio empty() {
        return new Fio("", "", "");
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fio)) return false;
        Fio fio = (Fio) o;
        return Objects.equals(getFirstName(), fio.getFirstName()) &&
                Objects.equals(getMiddleName(), fio.getMiddleName()) &&
                Objects.equals(getLastName(), fio.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getMiddleName(), getLastName());
    }

    @Override
    public String toString() {
        return String.join(" ", this.firstName, this.middleName, this.lastName);
    }
}
