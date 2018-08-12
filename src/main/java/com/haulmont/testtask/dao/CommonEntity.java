package com.haulmont.testtask.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public abstract class CommonEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
