package com.haulmont.testtask.ui.validation;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

public class NotEmptyFieldValidator<T> implements Validator<T> {

    @Override
    public ValidationResult apply(T t, ValueContext valueContext) {
        //Не было времени ковырять, на момент использования юзаются только строки.
        if (t instanceof String && !String.valueOf(t).isEmpty()) {
            return ValidationResult.ok();
        }
        return ValidationResult.error("Поле не может быть пустым.");
    }


}
