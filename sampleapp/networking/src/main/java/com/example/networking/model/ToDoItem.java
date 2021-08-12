package com.example.networking.model;

import java.util.Objects;

public class ToDoItem {

    private Integer id;
    private String content;
    private Boolean checked;

    public ToDoItem() {}

    public ToDoItem(Integer id, String content, Boolean checked) {
        this.id = id;
        this.content = content;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoItem toDoItem = (ToDoItem) o;
        return Objects.equals(id, toDoItem.id) && Objects.equals(content, toDoItem.content) && Objects.equals(checked, toDoItem.checked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, checked);
    }
}
