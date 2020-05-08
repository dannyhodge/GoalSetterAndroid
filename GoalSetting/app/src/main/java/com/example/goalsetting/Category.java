package com.example.goalsetting;

import java.util.List;

public class Category {

    private String title;
    private List<Goal> goals;

    public Category(String title, List<Goal> goals) {
        this.title = title;
        this.goals = goals;
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
