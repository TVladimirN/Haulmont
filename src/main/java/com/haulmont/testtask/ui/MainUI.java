package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.field.PhoneTextField;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import static com.haulmont.testtask.utils.ComponentUtil.buildTextField;

@Theme(ValoTheme.THEME_NAME)
@SpringUI(path = "/")
public class MainUI extends UI {

    @Autowired
    private Tabs tabs;


    public MainUI() {
        UI.setCurrent(this);
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(tabs);

        setContent(layout);


//        addWindow(
//                new ModalEditorWindow(
//                        "Добавление нового пользователя",
//                        new Component[]{
//                                buildTextField("Имя"),
//                                buildTextField("Фамилия"),
//                                buildTextField("Отчество"),
//                                new PhoneTextField()
////                                gridLayout
//                        }
//
//                )
//        );
    }

}