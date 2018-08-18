package com.haulmont.testtask.ui.field;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.TextField;

public class WorldTextField extends TextField {

    public WorldTextField() {
        super();
        setChangeListener();
    }

    public WorldTextField(String caption) {
        super(caption);
        setChangeListener();
    }

    private void setChangeListener() {
        setValueChangeMode(ValueChangeMode.EAGER);
        addValueChangeListener(
                event -> doSetValue(event.getValue().replaceAll("[[^а-яА-яa-zA-Z]_]+", ""))
        );
    }

}
