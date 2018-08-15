package com.haulmont.testtask.spring;

import com.haulmont.testtask.repository.MyRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

public class RepositoryEntityBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JpaRepositoryFactoryBean) {
            Class<?> c = ((JpaMetamodelEntityInformation) ((JpaRepositoryFactoryBean) bean)
                    .getEntityInformation()).getIdAttribute().getJavaMember().getDeclaringClass();
            repositoryService.addRepository(c, (MyRepository)((JpaRepositoryFactoryBean) bean).getObject());
        }
        return bean;
    }
}
