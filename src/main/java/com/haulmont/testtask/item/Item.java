package com.haulmont.testtask.item;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

public interface Item {

    Collection<?> getIds();

    Object getValueById(Object o);


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ItemContainerInfos {

        ItemContainerInfo[] value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ItemContainerInfo {

        String propertyId();

        Class<?> type();

        String defaultValue();

        String header();

    }


}
