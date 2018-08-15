package com.haulmont.testtask.ui.annotation;

public @interface ToString {

    String[] parameter() default {};

    String delimiter() default " ";

}