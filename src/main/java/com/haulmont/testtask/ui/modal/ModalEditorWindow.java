package com.haulmont.testtask.ui.modal;

import com.haulmont.testtask.payload.dao.PatientDAO;
import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.field.PhoneTextField;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalEditorWindow<T> extends Window {

    private Map<String, Component> components;
    private Button acceptButton;
    private final Class type;
    private T defaultItem;
    private Binder<T> binder;

    private Map<Class, CrudRepository<?, ?>> repositories = new HashMap<>();

    public Map<Class, CrudRepository<?, ?>> getRepositories() {
        return this.repositories;
    }

    public void setRepositories(
            Map<Class, CrudRepository<?, ?>> repositories) {
        this.repositories = repositories;
    }

    public ModalEditorWindow(String label, Class<?> type, T defaultItem) {
        this(label, type);
        if (null != defaultItem) {
            this.binder.readBean(defaultItem);
            this.defaultItem = defaultItem;
        }
    }

    public ModalEditorWindow(String label, Class<?> type) {
        this.binder = new Binder<>();
        this.type = type;
        this.components = new HashMap<>();
        setClosable(false);
        setModal(true);
        setResizable(false);
        setDraggable(false);

        setCaption(label);

        Component[] actions = {
                okButton(),
                cancelButton()
        };

        setContent(renderComponents(buildComponents(type), actions));
    }

    public void addAcceptListener(Button.ClickListener clickListener) {
        this.acceptButton.addClickListener(clickListener);
    }

    public void readBean(T bean) {
        this.binder.readBean(bean);
    }

    public void writeBean(T bean) throws ValidationException {
        this.binder.writeBean(bean);
    }

//    public T getItem() throws Exception {
//        if (null == defaultItem) {
//            defaultItem = (T) type.newInstance();
//        }
//
//        for (String key : components.keySet()) {
//            Field field = ReflectionUtils.findField(type, key);
//            field.setAccessible(true);
//            field.set(defaultItem, getValue(components.get(key)));
//        }
//
//        return defaultItem;
//    }

//    private void setDefaultValuesFromItem(T item) {
//        for (String key : components.keySet()) {
//            Field field = ReflectionUtils.findField(type, key);
//            field.setAccessible(true);
//            try {
//                setValue(components.get(key), field.get(item));
//            } catch (IllegalAccessException ignore) {
//                ignore.printStackTrace();
//            }
//        }
//    }

//    @SuppressWarnings("unchecked")
//    private void setValue(Component component, Object value) {
//        if (null == value) {
//            return;
//        }
//        if (component instanceof TextField) {
//            ((TextField) component).setValue(String.valueOf(value));
//        } else if (component instanceof ComboBox) {
//            ((ComboBox) component).setValue(value);
//        } else if (component instanceof DateField) {
//            ((DateField) component).setValue(LocalDate.parse((CharSequence) value));
//        } else if (component instanceof PhoneTextField) {
//            ((PhoneTextField) component).setValue(String.valueOf(value));
//        }
//    }
//
//    private Object getValue(Component component) {
//        if (component instanceof TextField) {
//            return ((TextField) component).getValue();
//        } else if (component instanceof ComboBox) {
//            return ((ComboBox) component).getValue();
//        } else if (component instanceof DateField) {
//            return ((DateField) component).getValue();
//        } else if (component instanceof PhoneTextField) {
//            return ((PhoneTextField) component).getValue();
//        }
//        return null;
//    }

    private Component[] buildComponents(Class<?> type) {
        List<Component> components = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            ComponentName componentName = field.getAnnotation(ComponentName.class);
            if (null == componentName) {
                continue;
            }
            ModalComponent modalComponent = field.getAnnotation(ModalComponent.class);
            if (null == modalComponent) {
                continue;
            }
            String caption = componentName.value();
            boolean componentEditable = modalComponent.editable();

            String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Component component = buildComponentByType(modalComponent, fieldName);
            component.setCaption(caption);
            component.setEnabled(componentEditable);

            this.components.put(field.getName(), component);

            components.add(component);
        }

        return components.toArray(new Component[0]);
    }

    private Component buildComponentByType(ModalComponent modalComponent, String fieldName) {
        switch (modalComponent.componentType()) {
            case DATE:
                DateField dateField = new DateField();
                binderOperation(dateField, modalComponent);
                binder.bind(dateField, getter(fieldName, LocalDate.class), setter(fieldName, LocalDate.class));
                return dateField;
            case COMBO_BOX:
                ComboBox comboBox = new ComboBox<>();
                comboBox.setEmptySelectionAllowed(false);
                binderOperation(comboBox, modalComponent);

                try {
                    if (!(modalComponent.object().isPrimitive())) {
                        Object c = repositories.get(modalComponent.object());
                        comboBox.setItems(
                                ReflectionUtils.findMethod(c.getClass(), modalComponent.dataSource())
                                        .invoke(c)
                        );
                    }
                } catch (Exception ignore) {
                }

                binder.bind(comboBox, getter(fieldName, String.class), setter(fieldName, String.class));
                return comboBox;
            case TEXT_FIELD:
                return _buildTextField(fieldName, modalComponent);
            case PHONE_FIELD:
                PhoneTextField phoneTextField = new PhoneTextField();
                binderOperation(phoneTextField, modalComponent);
                binder.bind(phoneTextField, getter(fieldName, String.class), setter(fieldName, String.class));
                return phoneTextField;
            case TEXT_AREA:
                TextArea textArea = new TextArea();
                textArea.setRows(3);
                textArea.setMaxLength(255);
                binderOperation(textArea, modalComponent);
                binder.bind(textArea, getter(fieldName, String.class), setter(fieldName, String.class));
                return textArea;
            default:
                return _buildTextField(fieldName, modalComponent);
        }
    }

    private void binderOperation(HasValue<?> hasValue, ModalComponent modalComponent) {
        hasValue.setReadOnly(!modalComponent.editable());
    }

    private TextField _buildTextField(String fieldName, ModalComponent modalComponent) {
        TextField textField = new TextField();
        binderOperation(textField, modalComponent);
        binder.bind(textField, getter(fieldName, String.class), setter(fieldName, String.class));
        return textField;
    }

    @SuppressWarnings("unchecked")
    private <TYPE> ValueProvider<T, TYPE> getter(String fn, Class<TYPE> type) {
        String fieldName = "get" + fn;
        return (ValueProvider<T, TYPE>) t -> (TYPE) ReflectionUtils.
                invokeMethod(ReflectionUtils.findMethod(t.getClass(), fieldName), t);
    }

    private <TYPE> Setter<T, TYPE> setter(String fn, Class<TYPE> type) {
        String fieldName = "set" + fn;
        return (Setter<T, TYPE>) (t, s) -> ReflectionUtils.
                invokeMethod(ReflectionUtils.findMethod(t.getClass(), fieldName, s.getClass()), t, s);
    }

    private Component renderComponents(Component[] components, Component[] actions) {
        FormLayout layout = new FormLayout();
        layout.setMargin(new MarginInfo(true, true, false, true));
        layout.addComponents(components);
        //Кнопки Ок и Отмена
        GridLayout gridLayout = new GridLayout(actions.length, 1, actions);
        gridLayout.setMargin(new MarginInfo(true, false, true, true));
        gridLayout.setSpacing(true);
        layout.addComponent(gridLayout);

        return layout;
    }

    private Component cancelButton() {
        Button button = new Button("Отмена");
        button.addClickListener((Button.ClickListener) clickEvent -> close());
        return button;
    }

    private Component okButton() {
        acceptButton = new Button("Ок");
        acceptButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    writeBean(defaultItem);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                close();
            }
        });

        return acceptButton;
    }

}
