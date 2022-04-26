package com.example.bahruztutorial;

import java.time.LocalDate;


/**
 * This class represents the Todo object
 */
public class Todo {


    private String title;
    private LocalDate deadline;
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline= deadline;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
