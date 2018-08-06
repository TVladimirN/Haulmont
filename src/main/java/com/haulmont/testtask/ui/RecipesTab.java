package com.haulmont.testtask.ui;

import com.haulmont.testtask.item.Patient;
import com.haulmont.testtask.item.Recept;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.ui.*;

public class RecipesTab extends VerticalLayout {

    private final UI ui;
    private Button editRecept = new Button("Изменить");
    private Button removeRecept = new Button("Удалить");
    private Tabs.MyTable<Recept> receptTable = new Tabs.MyTable<>(null, Recept.class);

    public RecipesTab(UI parentUI) {
        this.ui = parentUI;
        this.editRecept.setEnabled(false);
        this.removeRecept.setEnabled(false);
        setCaption("Рецепты");
        setSpacing(true);


        GridLayout gridLayoutFilter = new GridLayout(3, 1);
        gridLayoutFilter.setSpacing(true);
        gridLayoutFilter.setCaption("Фильтр");
        gridLayoutFilter.addComponent(new TextField("Описание"));
        gridLayoutFilter.addComponent(new TextField("Пациент"));
        gridLayoutFilter.addComponent(new TextField("Врач"));
        addComponent(gridLayoutFilter);

        addComponent(receptTable);

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить"));
        gridLayout.addComponent(editRecept);
        gridLayout.addComponent(removeRecept);

        addComponent(gridLayout);

    }

    private void addModaleWindow(UI ui){
    }
}
