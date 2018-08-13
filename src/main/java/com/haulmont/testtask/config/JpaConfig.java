package com.haulmont.testtask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.haulmont.testtask.repository")
public class JpaConfig {

//    @Autowired
//    private BeanFactory beanFactory;
//
//    @PostConstruct
//    public void crudService(){
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(CrudService.class);
//        rootBeanDefinition.setAbstract(true);
//        ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition("crudService", rootBeanDefinition);
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.HSQL);
//        vendorAdapter.setShowSql(true);


        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
                getClass().getPackage().getName(),
                "com.haulmont.testtask.payload.dao"
        );

        factory.setDataSource(dataSource);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @Bean
    @Lazy(false)
    public ResourceDatabasePopulator populateDatabase(DataSource dataSource) throws SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("scripts/createTables.sql"),
                new ClassPathResource("scripts/initData.sql")
        );

        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            populator.populate(connection);
        }

        return populator;
    }

}
