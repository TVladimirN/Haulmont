package com.haulmont.testtask.ui.field;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.TextField;

public class NumberTextField extends TextField {

    public NumberTextField() {
        super();
        setChangeListener();
    }

    public NumberTextField(String caption) {
        super(caption);
        setChangeListener();
    }

    private void setChangeListener() {
        setValueChangeMode(ValueChangeMode.EAGER);
        addValueChangeListener(
                event -> doSetValue(event.getValue().replaceAll("[a-zA-Z]?[^0-9]", ""))
        );
    }
}
