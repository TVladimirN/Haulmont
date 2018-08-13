package com.haulmont.testtask.ui.table;

import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommonTable<ITEM> extends VerticalLayout implements View {

    private CrudRepository<ITEM, Long> repository;

    public void setRepository(CrudRepository repository) {
        this.repository = repository;
    }

    private Button edit = new Button("Изменить");
    private Button remove = new Button("Удалить");
    private Button add = new Button("Добавить");
    private Table<ITEM> table;

    private ITEM itemSelected;
    Class<ITEM> type;

    public CommonTable(Class<ITEM> type) {
        this.type = type;
        this.table = new Table<>(type);
    }

    @PostConstruct
    public void init() {
        this.edit.setEnabled(false);
        this.remove.setEnabled(false);

        addComponent(table);

        table.addItemClickListener(itemClickEvent -> {
            this.edit.setEnabled(true);
            this.remove.setEnabled(true);
            this.itemSelected = itemClickEvent.getItem();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);

        gridLayout.addComponent(add);
        add.addClickListener(clickEvent -> openModalEditorWindow(createInstanceItem()));

        gridLayout.addComponent(edit);
        edit.addClickListener(clickEvent -> {
            openModalEditorWindow(itemSelected);
        });

        gridLayout.addComponent(remove);
        remove.addClickListener(clickEvent -> {
            try {
                repository.delete(itemSelected);
                this.table.setItems(new HashSet<>((Collection<? extends ITEM>) repository.findAll()));
                this.edit.setEnabled(false);
                this.remove.setEnabled(false);
            } catch (DataIntegrityViolationException e) {
                new Notification(
                        "Вы не можете удалить врача, пока существуют выписанные им рецепты!",
                        Notification.Type.ERROR_MESSAGE
                ).show(this.getUI().getPage());
            }
        });


        addComponent(gridLayout);

        table.setItems(new HashSet<>((Collection<? extends ITEM>) repository.findAll()));
    }

    private void openModalEditorWindow(ITEM item) {
        ModalEditorWindow<ITEM> modalEditorWindow =
                new ModalEditorWindow<>("Редактирование пациента", type, item);
        modalEditorWindow.addAcceptListener(
                clickEvent -> {
                    try {
                        repository.save(item);
                        table.setItems(new HashSet<>((Collection<? extends ITEM>) repository.findAll()));
                        this.edit.setEnabled(false);
                        this.remove.setEnabled(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        UI.getCurrent().addWindow(modalEditorWindow);
    }

    @SuppressWarnings("unchecked")
    private ITEM createInstanceItem() {
        try {
            return this.type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Класс должен иметь пустой конструктор");
        }
    }

}