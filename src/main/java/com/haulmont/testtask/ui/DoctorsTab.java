package com.haulmont.testtask.ui;

import com.haulmont.testtask.item.Doctor;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class DoctorsTab extends VerticalLayout {

    private final UI ui;

    private Button editDoctor = new Button("Изменить");
    private Button removeDoctor = new Button("Удалить");
    private Tabs.MyTable<Doctor> doctorsTable = new Tabs.MyTable<>(null, Doctor.class);

    public DoctorsTab(UI paretnUI) {
        this.ui = paretnUI;
        this.editDoctor.setEnabled(false);
        this.removeDoctor.setEnabled(false);
        setCaption("Врачи");

        addComponent(doctorsTable);


        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить"));
        gridLayout.addComponent(editDoctor);
        gridLayout.addComponent(removeDoctor);

        addComponent(gridLayout);
    }
}
