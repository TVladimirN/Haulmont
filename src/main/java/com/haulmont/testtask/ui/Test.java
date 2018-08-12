package com.haulmont.testtask.ui;

import com.haulmont.testtask.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
//@EnableScheduling
public class Test {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DataSource dataSource;

//    @Scheduled(initialDelay = 20, fixedRate = 60000)
//    public void init(){
//        int a = 2;
//    }
}
