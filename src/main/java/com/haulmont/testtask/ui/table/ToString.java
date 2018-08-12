package com.haulmont.testtask.ui.table;

public @interface ToString {

    String[] parameter() default {};

    String delimiter() default " ";

}