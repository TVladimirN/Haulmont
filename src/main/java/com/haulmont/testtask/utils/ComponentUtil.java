package com.haulmont.testtask.utils;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

public class ComponentUtil {


    public static TextField buildTextField(String caption) {
        int maxLength = 30;
        TextField textField = new TextField();
        textField.setCaption(caption);
        textField.setMaxLength(maxLength);
        textField.setWidth("285");

        textField.addValidator(new StringLengthValidator(
                "Введите имя",
                2,
                maxLength,
                true
        ));

        textField.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        textField.addTextChangeListener(
                (FieldEvents.TextChangeListener) textChangeEvent -> ((TextField) textChangeEvent.getSource())
                        .setValue(textChangeEvent.getText().replaceAll("[^a-zA-Z][0-9]?", "")));

        return textField;
    }

    public static void resetValueComponent(Component component) {
        if (component instanceof TextField) {
            ((TextField) component).setValue("");
        } else if (component instanceof ComboBox) {
            ComboBox comboBox = (ComboBox) component;
            comboBox.setValue(comboBox.getItemIds().iterator().next());
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
