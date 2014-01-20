package com.kwc.todo;

/**
 * @author Marius Kristensen
 */
public class TodoItem {
    private long id;
    private String title;
    private String details;

    public TodoItem() {
    }

    public TodoItem(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return title;
    }
}
