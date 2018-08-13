package com.haulmont.testtask.ui.table;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableComponent {

    /**
     * Ключ порядка вывода
     * @return
     */
    int order();

    /**
     * Флаг отрисовки поля
     * @return
     */
    boolean render();

    /**
     * Флаг редактируемости
     * @return true/false or default ""
     */
    String editable() default "";

    ToString string() default @ToString();

}