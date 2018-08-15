package com.haulmont.testtask.config;

import com.haulmont.testtask.spring.RepositoryEntityBeanPostProcessor;
import com.haulmont.testtask.spring.RepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RepositoryEntityBeanPostProcessor repositoryEntityBeanPostProcessor() {
        return new RepositoryEntityBeanPostProcessor();
    }

    @Bean
    public RepositoryService repositoryService() {
        return new RepositoryService();
    }
}
