package ru.javarest.model;

public interface BaseEntity {
    boolean isNew();
    void setId(Integer id);
    Integer getId();
}
