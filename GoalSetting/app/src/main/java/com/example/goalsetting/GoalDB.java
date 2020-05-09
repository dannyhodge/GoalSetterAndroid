package com.example.goalsetting;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = CategoryDB.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = ForeignKey.CASCADE))

public class GoalDB {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category_id")
    public Integer categoryId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "start_value")
    public double startValue;

    @ColumnInfo(name = "end_value")
    public double endValue;

    @ColumnInfo(name = "progress")
    public double progress;

    public GoalDB(@NonNull String title, @NonNull double startValue, @NonNull double endValue, @NonNull double progress, @NonNull Integer categoryId) {
        this.title = title;
        this.startValue = startValue;
        this.endValue = endValue;
        this.progress = progress;
        this.categoryId = categoryId;
    }

    @NonNull
    public String getGoalTitle() {
        return this.title;
    }


}

