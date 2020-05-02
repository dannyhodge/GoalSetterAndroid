package com.example.goalsetting;

import java.util.List;

public class Category {

    private String title;
    private List<Goal> goals;

    public Category(String title, List<Goal> goals) {
        this.title = title;
        this.goals = goals;
    }

}
