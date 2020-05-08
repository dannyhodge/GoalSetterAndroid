package com.example.goalsetting;

public class Goal {

   private int id;
   private String title;
   private double startValue;
   private double endValue;
   private double progress;
   private boolean expanded;

   public Goal(String title, double startValue, double endValue, double progress, int id) {
       this.title = title;
       this.startValue = startValue;
       this.endValue = endValue;
       this.progress = progress;
       this.expanded = false;
       this.id = id;
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getId() {
        return id;
    }
}
