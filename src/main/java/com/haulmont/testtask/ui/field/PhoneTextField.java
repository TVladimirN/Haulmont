package com.haulmont.testtask.ui.field;


import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

public class PhoneTextField extends GridLayout {

    private ComboBox codeBox;
    private TextField phoneField;

    public PhoneTextField() {
        super(2, 1);

        setCaption("Телефон");
        setWidth("300");
        setSpacing(true);

        addComponent(codeBox());
        addComponent(phoneField());
    }

    public String getValue() {
        return this.codeBox.getValue() + this.phoneField.getValue();
    }

    private Component codeBox() {
        codeBox = new ComboBox();
        codeBox.setWidth("75");
        codeBox.setNullSelectionAllowed(false);
        //для примера
        codeBox.addItems("+7");
        codeBox.addItems("+13");
        codeBox.addItems("+11");
        codeBox.select("+7");
        //
        return codeBox;
    }

    private Component phoneField() {
        phoneField = new TextField();
        phoneField.addValidator(new RegexpValidator(
                "\\+?[0-9]{10,13}",
                true,
                "Введен неверный номер телефона!"
        ));

        phoneField.setDescription("без восмерки");
        phoneField.addValidator(new NullValidator(
                "Поле обязательно для заполнения",
                false)
        );
        phoneField.setMaxLength(13);
        phoneField.setNullRepresentation("+7(000)0000000");

        phoneField.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        phoneField.addTextChangeListener(textChangeEvent -> {
            ((TextField) textChangeEvent.getSource())
                    .setValue(textChangeEvent.getText().replaceAll("[a-zA-Z]?[^0-9]", ""));
        });
        return phoneField;
    }

}
