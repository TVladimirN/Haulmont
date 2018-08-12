package com.haulmont.testtask.item;


public interface Item {

    Object converItemToDao();

    void fillItemFromDAO(Object object);

}
