package com.haulmont.testtask.config;

import org.hsqldb.jdbcDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.*;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.haulmont.testtask.repository")
public class JpaConfig {

//    @Bean
//    public DataSourceFactory dataSourceFactory(){
//        DataSourceFactory dataSourceFactory = new DataSourceFactory() {
//            SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
//
//            @Override
//            public ConnectionProperties getConnectionProperties() {
//                return new ConnectionProperties() {
//
//                    @Override
//                    public void setDriverClass(Class<? extends Driver> aClass) {
//                        simpleDriverDataSource.setDriverClass(aClass);
//                    }
//
//                    @Override
//                    public void setUrl(String s) {
//                        simpleDriverDataSource.setUrl(s);
//                    }
//
//                    @Override
//                    public void setUsername(String s) {
//                        simpleDriverDataSource.setUsername(s);
//                    }
//
//                    @Override
//                    public void setPassword(String s) {
//                        simpleDriverDataSource.setPassword(s);
//                    }
//                };
//            }
//
//            @Override
//            public DataSource getDataSource() {
//                return this.simpleDriverDataSource;
//            }
//        };
//        ConnectionProperties connectionProperties = dataSourceFactory.getConnectionProperties();
//        connectionProperties.setDriverClass(jdbcDriver.class);
//        connectionProperties.setUrl("jdbc:hsqldb:file:D:/Projects/forTest/test-task/hsqldb/testdb");
//        connectionProperties.setUsername("sa");
//        connectionProperties.setPassword("");
//
//        return dataSourceFactory;
//    }
//
////    @Bean
////    public EmbeddedDatabaseFactory embeddedDatabaseFactory(){
////        EmbeddedDatabaseFactory embeddedDatabaseFactory = new EmbeddedDatabaseFactory();
////
////        embeddedDatabaseFactory.setDatabaseType(EmbeddedDatabaseType.HSQL);
////        embeddedDatabaseFactory.setDatabaseConfigurer(new EmbeddedDatabaseConfigurer() {});
////
////        return embeddedDatabaseFactory;
////    }
//
//    @Bean
//    public DataSource dataSource(DataSourceFactory dataSourceFactory) {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
////        builder.setType(EmbeddedDatabaseType.HSQL);
//        builder.setDataSourceFactory(dataSourceFactory);
//
//        return builder.build();
//    }
//
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.HSQL);
//        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);


        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
                getClass().getPackage().getName(),
                "com.haulmont.testtask.dao"
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
