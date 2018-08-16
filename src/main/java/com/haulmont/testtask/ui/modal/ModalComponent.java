package com.haulmont.testtask.ui.modal;

import com.haulmont.testtask.ui.annotation.ToString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModalComponent {

    /**
     * Редактируемость
     * @return
     */
    boolean editable() default true;

    /**
     * Тип компонента в отрисовке
     * @return
     */
    ComponentType componentType() default ComponentType.TEXT_FIELD;

    boolean isRequire() default false;

    String dataSource() default "";

    Class object() default Void.class;

    ToString string() default @ToString();

}
