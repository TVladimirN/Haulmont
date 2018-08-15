package com.haulmont.testtask.spring;

import com.haulmont.testtask.repository.MyRepository;

import java.util.HashMap;
import java.util.Map;


public final class RepositoryService {

    private final Map<Class<?>, MyRepository> repositories;

    public RepositoryService() {
        this.repositories = new HashMap<>();
    }

    public RepositoryService(Map<Class<?>, MyRepository> repos) {
        this.repositories = repos;
    }

    public void addRepository(Class<?> classDAO, MyRepository repository) {
        this.repositories.put(classDAO, repository);
    }

    public <T> MyRepository<T, ?> getRepositoryByClassDao(Class<T> classDAO) {
        return this.repositories.get(classDAO);
    }

}
