package com.haulmont.testtask.ui.modal;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import static com.haulmont.testtask.utils.ComponentUtil.resetValueComponent;

public class ModalEditorWindow extends Window {

    private Component[] components;

    public ModalEditorWindow(String label, Component[] components) {
        this.components = components;
        setClosable(false);
        setModal(true);
        setResizable(false);
        setDraggable(false);
//        setVisible(false);


        setCaption(label);

        Component[] actions = {
                okButton(),
                cancelButton()
        };

        setContent(renderComponents(components, actions));

    }

    private Component renderComponents(Component[] components, Component[] actions) {
        FormLayout layout = new FormLayout();
        layout.setMargin(new MarginInfo(true, true, false, true));
        layout.addComponents(components);
        GridLayout gridLayout = new GridLayout(actions.length, 1, actions);
        gridLayout.setMargin(new MarginInfo(true, false, true, true));
        gridLayout.setSpacing(true);
        layout.addComponent(gridLayout);
        return layout;
    }

    private Component cancelButton(){
        Button button = new Button("Отмена");
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                for (Component component : components){
//                    resetValueComponent(component);
//                }
//                setVisible(false);
                close();
            }
        });
        return button;
    }

    private Button acceptButton;

    private Component okButton(){
        acceptButton = new Button("Ок");
        acceptButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });

        return acceptButton;
    }

    public void addAcceptListener(Button.ClickListener clickListener){
        acceptButton.addClickListener(clickListener);
    }

    public void addCancelListener(Button.ClickListener clickListener){

    }
}
