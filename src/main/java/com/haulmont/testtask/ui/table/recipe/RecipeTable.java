package com.haulmont.testtask.ui.table.recipe;

import com.haulmont.testtask.payload.dao.RecipeDAO;
import com.haulmont.testtask.repository.RecipeRepository;
import com.haulmont.testtask.ui.field.WorldTextField;
import com.haulmont.testtask.ui.modal.recipe.RecipeModalEditorWindow;
import com.haulmont.testtask.ui.table.CommonTable;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class RecipeTable extends CommonTable<RecipeDAO> {

    private TextField searchBarDescription;
    private TextField searchBarDoctor;
    private TextField searchBarPatient;

    public RecipeTable() {
        super(RecipeDAO.class);

        renderSearchBar();
    }

    @Override
    protected RecipeModalEditorWindow createModalEditorWindow(RecipeDAO item) {
        RecipeModalEditorWindow modalEditorWindow =
                new RecipeModalEditorWindow("Редактирование\\Добавление рецепта", item, repositoryService);
        modalEditorWindow.init();

        return modalEditorWindow;
    }

    private void renderSearchBar() {
        this.searchBarDescription = new TextField("Описание");
        this.searchBarDoctor = new WorldTextField("Врач");
        this.searchBarPatient = new WorldTextField("Пациент");
        Button searchButton = new Button("Поиск");
        searchButton.addClickListener(buildSearchBarListener());

        GridLayout gridLayoutFilter = new GridLayout(3, 1);
        gridLayoutFilter.setSpacing(true);
        gridLayoutFilter.setCaption("Фильтр");
        gridLayoutFilter.addComponent(this.searchBarDescription);
        gridLayoutFilter.addComponent(this.searchBarPatient);
        gridLayoutFilter.addComponent(this.searchBarDoctor);
        gridLayoutFilter.addComponent(searchButton);

        addComponent(gridLayoutFilter);
    }


    private Button.ClickListener buildSearchBarListener() {
        return (Button.ClickListener) event -> {
            String desc = searchBarDescription.getValue().isEmpty()
                    ? "%"
                    : "%" + searchBarDescription.getValue() + "%";
            String pat = searchBarPatient.getValue().isEmpty()
                    ? "%"
                    : "%" + String.join("%", searchBarPatient.getValue().split(" ")) + "%";
            String doc = searchBarDoctor.getValue().isEmpty()
                    ? "%"
                    : "%" + String.join("%", searchBarDoctor.getValue().split(" ")) + "%";

            table.setItems(
                    ((RecipeRepository) repository).findByParams(desc, pat, doc)
            );
        };
    }
}
