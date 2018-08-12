package com.haulmont.testtask.ui;


import com.haulmont.testtask.item.Patient;
import com.haulmont.testtask.repository.PatientRepository;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.haulmont.testtask.ui.table.Table;
import com.sun.istack.internal.Nullable;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@UIScope
@SpringView
public class PatientsTab extends VerticalLayout implements View {

    @Autowired
    private PatientRepository patientRepository;

    private Button editPatient = new Button("Изменить");
    private Button removePatient = new Button("Удалить");
    private Table<Patient> patientsTable = new Table<>(Patient.class);

    private Patient itemSelected;
    private Set<Patient> patientList;

    @PostConstruct
    public void init() {
        this.editPatient.setEnabled(false);
        this.removePatient.setEnabled(false);
        setCaption("Пациенты");

        addComponent(patientsTable);

        patientsTable.addItemClickListener(itemClickEvent -> {
            this.editPatient.setEnabled(true);
            this.removePatient.setEnabled(true);
            itemSelected = itemClickEvent.getItem();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить") {{
            addClickListener(clickEvent -> {
                openModalEditorWindow(new Patient());
            });
        }});

        editPatient.addClickListener(clickEvent -> {
            openModalEditorWindow(itemSelected);
        });
        gridLayout.addComponent(editPatient);

        removePatient.addClickListener(clickEvent -> {
            try {
                patientRepository.delete(Patient.convertPatientItemToPatientDAO(itemSelected));
                this.patientList.remove(itemSelected);
                this.patientsTable.setItems(patientList);
                this.editPatient.setEnabled(false);
                this.removePatient.setEnabled(false);
            } catch (DataIntegrityViolationException e) {
                new Notification(
                        "Вы не можете удалить пациента для которого есть рецепт!",
                        Notification.Type.ERROR_MESSAGE
                ).show(this.getUI().getPage());
            }
        });
        gridLayout.addComponent(removePatient);

        addComponent(gridLayout);

        patientList = new HashSet<>(Patient.convertListPatientDaoToListPatient(patientRepository.findAll()));
        patientsTable.setItems(patientList);
    }

    private void openModalEditorWindow(@Nullable Patient p) {
        ModalEditorWindow<Patient> modalEditorWindow =
                new ModalEditorWindow<>("Редактирование пациента", Patient.class, p);
        modalEditorWindow.addAcceptListener(
                clickEvent -> {
                    try {
                        patientList.add(p);
                        patientRepository.save(Patient.convertPatientItemToPatientDAO(p));
                        patientsTable.setItems(patientList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        UI.getCurrent().addWindow(modalEditorWindow);
        int a = 2;
    }

}
