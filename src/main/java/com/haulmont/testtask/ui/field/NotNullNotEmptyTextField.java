package com.haulmont.testtask.ui.field;


import com.vaadin.data.Binder;
import com.vaadin.ui.TextField;

public class NotNullNotEmptyTextField extends TextField {

    public NotNullNotEmptyTextField() {
        new Binder<String>().forField(this)
                .asRequired();
    }
}
