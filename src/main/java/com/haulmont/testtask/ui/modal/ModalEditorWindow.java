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

import javax.annotation.PostConstruct;
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
    protected T defaultItem;
    protected Binder<T> binder;


    public ModalEditorWindow(String label, Class<?> type, T defaultItem) {
        this(label, type);
        this.defaultItem = defaultItem;
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
    }

    @PostConstruct
    public void init() {
        setContent(renderComponents(buildComponents(), getActions()));
        if (null != defaultItem) {
            this.binder.readBean(defaultItem);
        }
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

    /**
     * Задаем кнопкий действий
     * @return
     */
    protected Component[] getActions() {
        Component[] actions = new Component[]{
                okButton(),
                cancelButton()
        };
        addAcceptListener();
        return actions;
    }

    /**
     * Создаем компоненты для отрисовски в модальном окне
     * @return
     */
    protected Component[] buildComponents() {
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

    protected void addAcceptListener(){
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
    };

    /**
     * Создание компонента в зависимости от типа
     * @param modalComponent
     * @param fieldName
     * @return
     */
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

    /**
     * Настройка биндера для поля
     * @param hasValue
     * @param modalComponent
     */
    private void binderOperation(HasValue<?> hasValue, ModalComponent modalComponent) {
        hasValue.setReadOnly(!modalComponent.editable());
    }

    //СОздание текстового поля чтоб не было дубля
    private TextField _buildTextField(String fieldName, ModalComponent modalComponent) {
        TextField textField = new TextField();
        binderOperation(textField, modalComponent);
        binder.bind(textField, getter(fieldName, String.class), setter(fieldName, String.class));
        return textField;
    }

    /**
     * Геттер для объекта используемый в биндере
     * @param fn
     * @param type
     * @param <TYPE>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <TYPE> ValueProvider<T, TYPE> getter(String fn, Class<TYPE> type) {
        String fieldName = "get" + fn;
        return (ValueProvider<T, TYPE>) t -> (TYPE) ReflectionUtils.
                invokeMethod(ReflectionUtils.findMethod(t.getClass(), fieldName), t);
    }

    /**
     * Сеттер для объекта используемый в биндере
     * @param fn
     * @param type
     * @param <TYPE>
     * @return
     */
    private <TYPE> Setter<T, TYPE> setter(String fn, Class<TYPE> type) {
        String fieldName = "set" + fn;
        return (Setter<T, TYPE>) (t, s) -> ReflectionUtils.
                invokeMethod(ReflectionUtils.findMethod(t.getClass(), fieldName, s.getClass()), t, s);
    }

    /**
     * Прорисовка компонентов в окошке
     * @param components
     * @param actions
     * @return
     */
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

    /**
     * Дефолтная кнопка "Отмена" и логика
     * @return
     */
    private Component cancelButton() {
        Button button = new Button("Отмена");
        button.addClickListener((Button.ClickListener) clickEvent -> close());
        return button;
    }

    /**
     * Дефолтная кнопка "Ок" и логика
     * @return
     */
    private Component okButton() {
        acceptButton = new Button("Ок");

        return acceptButton;
    }

}
