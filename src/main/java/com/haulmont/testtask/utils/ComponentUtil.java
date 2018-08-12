package com.haulmont.testtask.utils;

import com.haulmont.testtask.ui.field.NotNullNotEmptyTextField;
import com.haulmont.testtask.ui.validation.NotEmptyFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class ComponentUtil {


    public static TextField buildTextField(String caption) {
        int maxLength = 30;
        NotNullNotEmptyTextField textField = new NotNullNotEmptyTextField();
        textField.setCaption(caption);
        textField.setMaxLength(maxLength);
        textField.setWidth("285");

        new Binder<String>().forField(textField)
                .asRequired(new NotEmptyFieldValidator<>())
                .withValidator(new StringLengthValidator(
                        "Введите имя",
                        2,
                        maxLength
                ))
                .bind(s -> s, (s, s2) -> s = s2)
                .validate();

        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(textChangeEvent ->
                textChangeEvent.getSource()
                        .setValue(textChangeEvent.getValue().replaceAll("[^a-zA-Z][0-9]?", "")));

        return textField;
    }

    public static void resetValueComponent(Component component) {
        if (component instanceof TextField) {
            ((TextField) component).setValue("");
        } else if (component instanceof ComboBox) {
            ComboBox comboBox = (ComboBox) component;
            comboBox.setSelectedItem("+7");
        } else if (component instanceof GridLayout) {
            GridLayout gridLayout = (GridLayout) component;
            for (int i = 0; i < gridLayout.getRows(); i++) {
                for (int j = 0; j < gridLayout.getColumns(); j++) {
                    resetValueComponent(gridLayout.getComponent(j, i));
                }
            }
        }
    }

}
