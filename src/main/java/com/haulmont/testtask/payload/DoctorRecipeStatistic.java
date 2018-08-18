package com.haulmont.testtask.payload;

import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.table.TableComponent;

public class DoctorRecipeStatistic {

    @ComponentName("Врач")
    @TableComponent(order = 0, render = true)
    private final String fio;
    @ComponentName("Колличество рецептов")
    @TableComponent(order = 1, render = true)
    private final long count;

    public DoctorRecipeStatistic(String f, String m, String l, long count) {
        this.fio = String.join(" ", f, m, l);
        this.count = count;
    }

    public String getFio() {
        return this.fio;
    }

    public long getCount() {
        return this.count;
    }

}
