package com.haulmont.testtask.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

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
    }

}