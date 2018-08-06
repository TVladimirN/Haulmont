package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.item.Patient;
import com.haulmont.testtask.ui.field.PhoneTextField;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.communication.UIInitHandler;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.utils.ComponentUtil.buildTextField;

public class PatientsTab extends VerticalLayout {

    private final UI ui;
    private Button editPatient = new Button("Изменить");
    private Button removePatient = new Button("Удалить");
    private Tabs.MyTable<Patient> patientsTable = new Tabs.MyTable<>(null, Patient.class);

    private Object itemIdSelected;

    public PatientsTab(UI parentUI) {
        this.ui = parentUI;
        this.editPatient.setEnabled(false);
        this.removePatient.setEnabled(false);
        setCaption("Пациенты");


        addComponent(patientsTable);
        patientsTable.addItemClickListener(itemClickEvent -> {
            this.editPatient.setEnabled(true);
            this.removePatient.setEnabled(true);
            itemIdSelected = itemClickEvent.getItemId();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить") {{
            addClickListener(clickEvent -> addModalWindow());
        }});


        gridLayout.addComponent(editPatient);

        removePatient.addClickListener(clickEvent -> {
            this.patientsTable.removeItem(this.itemIdSelected);
            this.editPatient.setEnabled(false);
            this.removePatient.setEnabled(false);
        });
        gridLayout.addComponent(removePatient);

        addComponent(gridLayout);

    }

    private void addModalWindow(){
        this.ui.addWindow(new ModalEditorWindow(
                "Добавление нового пользователя",
                new Component[]{
                        buildTextField("Имя"),
                        buildTextField("Фамилия"),
                        buildTextField("Отчество"),
                        new PhoneTextField()
                }

        ){{addAcceptListener(clickEvent1 -> {
            patientsTable.addItem(new Patient(){{
                setFirstName("first");
                setMiddleName("middle");
                setLastName("last");
                setPhone("phone");
            }});
            close();
        });}});
    }

    private void editModalWindow() {

    }


}
