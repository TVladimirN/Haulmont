package com.haulmont.testtask;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@SpringBootApplication(scanBasePackages = "com.haulmont.testtask")
public class SpringStarter extends SpringBootServletInitializer {

    private final Class<?> appClass = SpringStarter.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.appClass);
    }
}
