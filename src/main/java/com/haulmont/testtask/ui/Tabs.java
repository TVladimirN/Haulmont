package com.haulmont.testtask.ui;

import com.haulmont.testtask.item.Item;
import com.haulmont.testtask.ui.modal.ModalEditorWindow;
import com.vaadin.ui.*;

import java.util.Collection;

public class Tabs extends TabSheet {

    private final UI ui;

    public Tabs(UI parentUI) {
        this.ui = parentUI;

        this.addTab(new PatientsTab(parentUI));
        this.addTab(new DoctorsTab(parentUI));
        this.addTab(new RecipesTab(parentUI));
    }

    static class MyTable<ITEM extends Item> extends Table {

        public MyTable() {
            super("");
        }

        public MyTable(String caption, Class<?> type) {
            super(caption);
            setSelectable(true);

            for (Item.ItemContainerInfo info : type.getAnnotation(Item.ItemContainerInfos.class).value()) {
                addContainerProperty(
                        info.propertyId(),
                        info.type(),
                        info.defaultValue(),
                        info.header(),
                        null,
                        null
                );
            }
        }

        @SuppressWarnings("unchecked")
        public void addItem(ITEM item, Object itemId) {
            for (Object id : item.getIds()) {
                getContainerProperty(itemId, id).setValue(item.getValueById(id));
            }
        }

        @SuppressWarnings("unchecked")
        public void addItem(ITEM item) {
            addItem(item, addItem());
        }

        @SuppressWarnings("unchecked")
        public void addCollectionItem(Collection<ITEM> items) {
            for (ITEM item : items) {
                addItem(item);
            }
        }

        @Override
        @Deprecated
        public Object addItem(Object[] cells, Object itemId) throws UnsupportedOperationException {
            return super.addItem(cells, itemId);
        }

        @Override
        @Deprecated
        public com.vaadin.data.Item addItem(Object itemId) throws UnsupportedOperationException {
            return super.addItem(itemId);
        }

        @Override
        @Deprecated
        public void addItems(Object... itemId) throws UnsupportedOperationException {
            super.addItems(itemId);
        }

        @Override
        @Deprecated
        public void addItems(Collection<?> itemIds) throws UnsupportedOperationException {
            super.addItems(itemIds);
        }
    }

}
