package com.haulmont.testtask.ui.table;

import com.haulmont.testtask.spring.RepositoryService;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;

public class CommonTable<ITEM> extends VerticalLayout implements View {

    protected CrudRepository<ITEM, ?> repository;
    protected RepositoryService repositoryService;

    private Button edit = new Button("Изменить");
    private Button remove = new Button("Удалить");
    private Button add = new Button("Добавить");
    protected Table<ITEM> table;

    private ITEM itemSelected;
    private Class<ITEM> type;

    public CommonTable(Class<ITEM> type) {
        this.type = type;
        this.table = new Table<>(type);
    }

    @PostConstruct
    public void init() {
        this.edit.setEnabled(false);
        this.remove.setEnabled(false);

        addComponent(this.table);

        this.table.addItemClickListener(itemClickEvent -> {
            this.edit.setEnabled(true);
            this.remove.setEnabled(true);
            this.itemSelected = itemClickEvent.getItem();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);

        gridLayout.addComponent(this.add);
        this.add.addClickListener(clickEvent -> openModalEditorWindow(createInstanceItem()));

        gridLayout.addComponent(this.edit);
        this.edit.addClickListener(clickEvent -> {
            openModalEditorWindow(this.itemSelected);
        });

        gridLayout.addComponent(this.remove);
        this.remove.addClickListener(clickEvent -> {
            try {
                this.repository.delete(itemSelected);
                this.table.setItems(new HashSet<>((Collection<? extends ITEM>) this.repository.findAll()));
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

        this.table.setItems(new HashSet<>((Collection<? extends ITEM>) this.repository.findAll()));
    }

    public void setRepository(CrudRepository<ITEM, ?> repository) {
        this.repository = repository;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    protected ModalEditorWindow<ITEM> createModalEditorWindow(ITEM item) {
        ModalEditorWindow<ITEM> modalEditorWindow =
                new ModalEditorWindow<>("Редактирование пациента", this.type, item);
        modalEditorWindow.init();
        return modalEditorWindow;
    }

    protected void createAcceptListener(ModalEditorWindow<ITEM> modalEditorWindow, ITEM item) {
        modalEditorWindow.addAcceptListener(
                clickEvent -> {
                    try {
                        this.repository.save(item);
                        this.table.setItems(new HashSet<>((Collection<? extends ITEM>) this.repository.findAll()));
                        this.edit.setEnabled(false);
                        this.remove.setEnabled(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void openModalEditorWindow(ITEM item) {
        ModalEditorWindow<ITEM> modalEditorWindow = createModalEditorWindow(item);
        createAcceptListener(modalEditorWindow, item);
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
