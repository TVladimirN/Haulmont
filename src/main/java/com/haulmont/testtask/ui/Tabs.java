package com.haulmont.testtask.ui;

import com.haulmont.testtask.item.Patient;
import com.haulmont.testtask.repository.DoctorRepository;
import com.haulmont.testtask.repository.PatientRepository;
import com.haulmont.testtask.repository.RecipeRepository;
import com.haulmont.testtask.ui.field.PhoneTextField;
import com.haulmont.testtask.ui.validation.NotEmptyFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.server.Setter;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UIScope
@SpringView//(ui = MainUI.class)
public class Tabs extends TabSheet implements View {

    @Autowired
    private PatientsTab patientsTab;
    @Autowired
    private DoctorsTab doctorsTab;
    @Autowired
    private RecipesTab recipesTab;

    @PostConstruct
    void init() throws NoSuchFieldException, NoSuchMethodException {

        Patient patient = new Patient();
        patient.setFirstName("Пупкин");
        patient.setMiddleName("Василий");
        patient.setLastName("Петрович");
        patient.setPhone("89274556561");
//        MyTextField<Patient> myTextField = new MyTextField<>(
//                Patient.class.getMethod("getFirstName"),
//                Patient.class.getMethod("setFirstName", String.class)
//        );
//        MyTextField<Patient> myTextField1 = new MyTextField<>(
//                Patient.class.getMethod("getMiddleName"),
//                Patient.class.getMethod("setMiddleName", String.class)
//        );
//        myTextField.readBean(patient);
//        myTextField1.readBean(patient);

        TextField textField1 = new TextField("Фамилия");
        TextField textField2 = new TextField("Имя");
        TextField textField3 = new TextField("Отчество");
        Binder<Patient> binder = new Binder<>();
        binder.bind(textField1, Patient::getFirstName, Patient::setFirstName);
        binder.bind(textField2, Patient::getMiddleName, Patient::setMiddleName);
        binder.bind(textField3, Patient::getLastName, Patient::setLastName);
        binder.readBean(patient);

        VerticalLayout layout = new VerticalLayout();
//        layout.addComponent(myTextField);
//        layout.addComponent(myTextField1);
        layout.addComponent(textField1);
        layout.addComponent(textField2);
        layout.addComponent(textField3);
        layout.addComponent(new Button("kk") {{
            addClickListener(clickEvent ->
                    {
                        try {
                            binder.writeBean(patient);
//                            myTextField.writeBean(patient);
//                            myTextField1.writeBean(patient);
                            System.out.println(patient);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }});
//        addTab(layout);


        addTab(patientsTab);
        addTab(doctorsTab);
        addTab(recipesTab);
    }

    class MyTextField<T> extends TextField {

        private Binder<T> binder;
        private Method getter;
        private Method setter;

        public MyTextField(Method getter, Method setter) {
            setCaption("Фамилия");

            binder = new Binder<>();
            binder.forField(this)
                    .asRequired(new NotEmptyFieldValidator<>())
                    .withValidator(new StringLengthValidator(
                            "Введите имя",
                            2,
                            30
                    ))
                    .bind(new ValueProvider<T, String>() {

                        @Override
                        public String apply(T patient) {
                            try {
                                return (String) getter.invoke(patient);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                    }, new Setter<T, String>() {

                        @Override
                        public void accept(T patient, String s) {
                            try {
                                setter.invoke(patient, s);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .validate();
        }

        public void setRequire(){
        }

        public void readBean(T patient) {
            binder.readBean(patient);
        }

        public void writeBean(T patient) throws ValidationException {
            binder.writeBean(patient);
        }
    }

}
