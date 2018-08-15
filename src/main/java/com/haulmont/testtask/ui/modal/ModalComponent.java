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
    Type componentType() default Type.TEXT_FIELD;

    String dataSource() default "";

    Class object() default Void.class;

    ToString string() default @ToString();

    enum Type {
        TEXT_FIELD,
        TEXT_AREA,
        PHONE_FIELD,
        COMBO_BOX,
        DATE
    }

}
