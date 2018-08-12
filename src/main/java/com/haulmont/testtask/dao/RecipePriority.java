package com.haulmont.testtask.dao;

public enum RecipePriority {
    NORMAL("Нормальный"),
    CITO("Срочный"),
    STATIM("Немедленный");

    private String s;

    RecipePriority(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
