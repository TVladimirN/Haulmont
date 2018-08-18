package com.haulmont.testtask.ui.table.doctor;

import com.haulmont.testtask.payload.DoctorRecipeStatistic;
import com.haulmont.testtask.payload.dao.DoctorDAO;
import com.haulmont.testtask.repository.DoctorRepository;
import com.haulmont.testtask.ui.table.CommonTable;
import com.haulmont.testtask.ui.table.Table;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.util.List;

public class DoctorTable extends CommonTable<DoctorDAO> {

    public DoctorTable() {
        super(DoctorDAO.class);
    }

    @Override
    protected Component[] addAdditionalActionButtons() {
        return new Component[]{
                new Button("Статистика", buildListenerStatisticButton())
        };
    }

    //Моделка с выводом инфы врчаей и выписанных рецептов
    private Button.ClickListener buildListenerStatisticButton() {
        return (Button.ClickListener) event -> {

            List<DoctorRecipeStatistic> statistics = ((DoctorRepository) repository).getStatistics();

            Window window = new Window("Статистика выписанных рецептов.");
            Table<DoctorRecipeStatistic> table = new Table<>(DoctorRecipeStatistic.class);
            table.setItems(statistics);
            window.setContent(table);
            window.setModal(true);
            window.setDraggable(false);
            window.setResizable(false);
            window.setHeight("900");
            window.setWidth("550");

            UI.getCurrent().addWindow(window);

        };
    }
}
