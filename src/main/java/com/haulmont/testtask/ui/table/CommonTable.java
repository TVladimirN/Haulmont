package com.haulmont.testtask.ui.table;

import com.haulmont.testtask.item.Doctor;
import com.haulmont.testtask.repository.DoctorRepository;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.sun.istack.internal.Nullable;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

public class CommonTable<ITEM> extends VerticalLayout implements View {

    @Autowired
    private DoctorRepository doctorRepository;

    private Button editDoctor = new Button("Изменить");
    private Button removeDoctor = new Button("Удалить");
    private Table<ITEM> doctorsTable = new Table<>(ITEM.class);


    private ITEM itemSelected;
    private Set<ITEM> doctorList;

    @PostConstruct
    public void init() {
        this.editDoctor.setEnabled(false);
        this.removeDoctor.setEnabled(false);
        setCaption("Врачи");

        addComponent(doctorsTable);

        doctorsTable.addItemClickListener(itemClickEvent -> {
            this.editDoctor.setEnabled(true);
            this.removeDoctor.setEnabled(true);
            itemSelected = itemClickEvent.getItem();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить") {{
            addClickListener(clickEvent -> {
                openModalEditorWindow(new Doctor());
            });
        }});

        editDoctor.addClickListener(clickEvent -> {
            openModalEditorWindow(itemSelected);
        });
        gridLayout.addComponent(editDoctor);
        removeDoctor.addClickListener(clickEvent -> {
            try {
                doctorRepository.delete(itemSelected.getId());
                this.doctorList.remove(itemSelected);
                this.doctorsTable.setItems(doctorList);
                this.editDoctor.setEnabled(false);
                this.removeDoctor.setEnabled(false);
            } catch (DataIntegrityViolationException e) {
                new Notification(
                        "Вы не можете удалить врача, пока существуют выписанные им рецепты!",
                        Notification.Type.ERROR_MESSAGE
                ).show(this.getUI().getPage());
            }
        });
        gridLayout.addComponent(removeDoctor);

        addComponent(gridLayout);

        doctorList = new HashSet<>(Doctor.convertListDoctorDaoToListDoctor(doctorRepository.findAll()));
        doctorsTable.setItems(doctorList);
    }

    private void openModalEditorWindow(@Nullable Doctor d) {
        ModalEditorWindow<Doctor> modalEditorWindow =
                new ModalEditorWindow<>("Редактирование пациента", Doctor.class, d);
        modalEditorWindow.addAcceptListener(
                clickEvent -> {
                    try {
                        doctorList.add(d);
                        doctorRepository.save(Doctor.converDoctorToDoctorDAO(d));
                        doctorsTable.setItems(doctorList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        UI.getCurrent().addWindow(modalEditorWindow);
    }

}
