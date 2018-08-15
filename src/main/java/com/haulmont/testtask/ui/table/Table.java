package com.haulmont.testtask.ui.table;

import com.haulmont.testtask.ui.annotation.ComponentName;
import com.haulmont.testtask.ui.annotation.ToString;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Grid;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Table<TYPE> extends Grid<TYPE> {

    public Table(Class<TYPE> type) {
        setSizeFull();

        addColumns(this, type);

    }

    /**
     * Сортировка порядка добавления по значению order
     * @param methods
     * @return
     * @see com.haulmont.testtask.ui.table.TableComponent
     */
    private List<Field> sortedFieldsWithAnnotation(Field[] methods) {
        return Arrays.stream(methods)
                .filter(f -> {
                    TableComponent annotation = f
                            .getAnnotation(TableComponent.class);
                    return annotation != null && annotation.render();
                })
                .sorted((m1, m2) -> {
                    if (m1.getAnnotation(TableComponent.class).order() >
                            m2.getAnnotation(TableComponent.class).order()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }).collect(Collectors.toList());
    }

    /**
     * Добавление колонок из аннотаций {@link com.haulmont.testtask.ui.table.TableComponent}
     * @param grid
     * @param type
     */
    private void addColumns(Grid grid, Class<?> type) {
        for (Field field : sortedFieldsWithAnnotation(type.getDeclaredFields())) {
            TableComponent columnAnnotation = field.getAnnotation(TableComponent.class);
            field.setAccessible(true);
            Grid.Column column = grid.addColumn((ValueProvider<?, Object>) value -> {
                try {
                    ToString toString = columnAnnotation.string();
                    if (toString.parameter().length == 0) {
                        return field.get(value);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (String parameter : toString.parameter()) {
                            sb.append(ReflectionUtils.findMethod(field.getType(), parameter).invoke(field.get(value)));
                            sb.append(toString.delimiter());
                        }
                        return sb.toString();
                    }
                } catch (Exception e) {
                    return new Exception();
                }
            });
            column.setCaption(field.getAnnotation(ComponentName.class).value());
            if (columnAnnotation.editable().equals("false")) {
                column.setEditable(false);
            } else if (columnAnnotation.editable().equals("true")) {
                column.setEditable(true);
            }
        }

    }

}

