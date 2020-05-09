package com.example.goalsetting;

import java.util.List;

public class Category {

    private int id;
    private String title;
    private List<Goal> goals;

    public Category(String title, List<Goal> goals, int id) {
        this.title = title;
        this.goals = goals;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setStartValue(List<Goal> goals) {
        this.goals = goals;
    }

}
