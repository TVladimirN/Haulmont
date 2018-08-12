package com.haulmont.testtask.ui;

import com.haulmont.testtask.item.Patient;
import com.haulmont.testtask.item.Recipe;
import com.haulmont.testtask.repository.DoctorRepository;
import com.haulmont.testtask.repository.PatientRepository;
import com.haulmont.testtask.repository.RecipeRepository;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.haulmont.testtask.ui.table.Table;
import com.sun.istack.internal.Nullable;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@UIScope
@SpringView
public class RecipesTab extends VerticalLayout implements View {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    private Button editRecipe = new Button("Изменить");
    private Button removeRecipe = new Button("Удалить");
    private Table<Recipe> recipeTable = new Table<>(Recipe.class);

    Recipe itemSelected;
    private Set<Recipe> recipeList;

    @PostConstruct
    public void init() {
        this.editRecipe.setEnabled(false);
        this.removeRecipe.setEnabled(false);
        setCaption("Рецепты");
        setSpacing(true);

        renderSearchBar();

        addComponent(recipeTable);

        recipeTable.addItemClickListener(itemClickEvent -> {
            this.editRecipe.setEnabled(true);
            this.removeRecipe.setEnabled(true);
            itemSelected = itemClickEvent.getItem();
        });

        GridLayout gridLayout = new GridLayout(3, 1);
        gridLayout.setSpacing(true);
        gridLayout.addComponent(new Button("Добавить") {{
            addClickListener(clickEvent -> {
                Recipe recipe = new Recipe();
                openModalEditorWindow(recipe);
                int a = 2;
            });
        }});

        editRecipe.addClickListener(clickEvent -> {
            openModalEditorWindow(itemSelected);
        });
        gridLayout.addComponent(editRecipe);

        removeRecipe.addClickListener(clickEvent -> {
            recipeRepository.delete(itemSelected.getId());
            this.recipeList.remove(itemSelected);
            this.recipeTable.setItems(recipeList);
            this.editRecipe.setEnabled(false);
            this.removeRecipe.setEnabled(false);
        });
        gridLayout.addComponent(removeRecipe);

        addComponent(gridLayout);

        loadData();
    }

    public void loadData() {
        recipeList = new HashSet<>(Recipe.convertListRecipeDaoToListRecipe(recipeRepository.findAll()));
        recipeTable.setItems(recipeList);
    }

    private TextField searchBarDescription;
    private TextField searchBarDoctor;
    private TextField searchBarPatient;

    private void renderSearchBar() {
        this.searchBarDescription = new TextField("Описание");
        this.searchBarDescription.addValueChangeListener(buildSearchBarListener());
        this.searchBarDoctor = new TextField("Врач");
        this.searchBarDoctor.addValueChangeListener(buildSearchBarListener());
        this.searchBarPatient = new TextField("Пациент");
        this.searchBarPatient.addValueChangeListener(buildSearchBarListener());


        GridLayout gridLayoutFilter = new GridLayout(3, 1);
        gridLayoutFilter.setSpacing(true);
        gridLayoutFilter.setCaption("Фильтр");
        gridLayoutFilter.addComponent(this.searchBarDescription);
        gridLayoutFilter.addComponent(this.searchBarPatient);
        gridLayoutFilter.addComponent(this.searchBarDoctor);

        addComponent(gridLayoutFilter);
    }

    private HasValue.ValueChangeListener<String> buildSearchBarListener() {
        return (HasValue.ValueChangeListener<String>) valueChangeEvent -> {
            String desc = searchBarDescription.getValue().isEmpty()
                    ? "%"
                    : "%" + searchBarDescription.getValue() + "%";
            String pat = searchBarPatient.getValue().isEmpty()
                    ? "%"
                    : "%" + searchBarPatient.getValue() + "%";;
            String doc = searchBarDoctor.getValue().isEmpty()
                    ? "%"
                    : "%" + searchBarDoctor.getValue() + "%";;

            recipeTable.setItems(
                    Recipe.convertListRecipeDaoToListRecipe(
                            recipeRepository.findByParams(desc, pat, doc)
                    )
            );
        };
    }

    private void openModalEditorWindow(@Nullable Recipe p) {
        ModalEditorWindow<Recipe> modalEditorWindow =
                new ModalEditorWindow<>("Редактирование пациента", Recipe.class, p);
        modalEditorWindow.addAcceptListener(
                clickEvent -> {
                    try {
                        recipeList.add(p);
                        recipeRepository.save(Recipe.convertRecipeItemToRecipeDAO(p));
                        recipeTable.setItems(recipeList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        Map<Class, CrudRepository<?, ?>> repos = new HashMap<>();
        repos.put(RecipeRepository.class, recipeRepository);
        repos.put(PatientRepository.class, patientRepository);
        repos.put(DoctorRepository.class, doctorRepository);
        modalEditorWindow.setRepositories(repos);

//        Window modalEditorWindow = new Window();
//        modalEditorWindow.setClosable(false);
//        modalEditorWindow.setModal(true);
//        modalEditorWindow.setResizable(false);
//        modalEditorWindow.setDraggable(false);
//
//        VerticalLayout layout = new VerticalLayout();
//        modalEditorWindow.setContent(layout);
//        ComboBox comboBox = new ComboBox<>();
//        comboBox.setCaption("Пациенты");
//        comboBox.setEmptySelectionAllowed(false);
//        comboBox.setItems(Patient.convertListPatientDaoToListPatient(patientRepository.findAll()));
//        layout.addComponent(comboBox);

        //
        UI.getCurrent().addWindow(modalEditorWindow);
        int a = 2;
    }

}
