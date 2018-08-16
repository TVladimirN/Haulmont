package com.haulmont.testtask.ui.field;

import com.haulmont.testtask.ui.validation.NotEmptyFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.shared.Registration;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class PhoneTextField extends GridLayout implements HasValue<String> {

    private ComboBox<String> codeBox;
    private TextField phoneField;

    public PhoneTextField() {
        super(2, 1);

        setCaption("Телефон");
        setWidth("350");
        setSpacing(true);

        addComponent(codeBox());
        addComponent(phoneField());
    }

    public void setValue(String value) {
        if (null == value || value.isEmpty()) {
            this.codeBox.setValue("");
            this.phoneField.setValue("");
        } else {
            String[] values = value.replaceFirst("\\+?(\\d)(\\d{9,12})", "$1|$2").split("\\|");
            this.codeBox.setValue(values[0]);
            this.phoneField.setValue(values[1]);
        }
    }

    public String getValue() {
        return this.codeBox.getValue() + this.phoneField.getValue();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean visible) {
        super.setRequiredIndicatorVisible(visible);
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return super.isRequiredIndicatorVisible();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        if (readOnly != this.isReadOnly()) {
            (this.getState()).enabled = readOnly;
        }
    }

    @Override
    public boolean isReadOnly() {
        return (this.getState(false)).enabled;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<String> listener) {
        return this.addListener(ValueChangeEvent.class, listener, ValueChangeListener.VALUE_CHANGE_METHOD);
    }

    private Component codeBox() {
        codeBox = new ComboBox<>();
        codeBox.setWidth("75");
        codeBox.setEmptySelectionAllowed(false);
        //для примера
        codeBox.setItems("+7", "+1", "+13");
        //

        new Binder<String>().forField(codeBox)
                .asRequired(new NotEmptyFieldValidator<>())
                .bind(s -> s, (s, s2) -> s = s2)
                .validate();

        return codeBox;
    }

    private Component phoneField() {

        phoneField = new NumberTextField();

        new Binder<String>().forField(phoneField)
                .asRequired(
                        new NotEmptyFieldValidator<>()
                )
                .withValidator(new RegexpValidator(
                        //Для мобилок
                        "Введен неверный номер телефона! (от 10 до 13 цифр)",
                        "\\+?[0-9]{10,13}", true
                ))
                .bind(s -> s, (s, s1) -> s = s1)
                .validate();

        phoneField.setDescription("без восмерки");
        phoneField.setMaxLength(13);

        return phoneField;
    }

}
