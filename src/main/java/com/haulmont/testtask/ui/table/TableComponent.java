package com.haulmont.testtask.ui.table;

import com.haulmont.testtask.ui.annotation.ToString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
