package com.haulmont.testtask.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public abstract class CrudService {

    @Autowired
    private List<CrudRepository<?, ?>> crudRepository;

    @PostConstruct
    public void init() {
        int a = 2;
    }
}
