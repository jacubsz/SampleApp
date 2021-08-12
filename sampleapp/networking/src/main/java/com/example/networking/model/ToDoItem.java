package com.example.networking.model;

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
}
