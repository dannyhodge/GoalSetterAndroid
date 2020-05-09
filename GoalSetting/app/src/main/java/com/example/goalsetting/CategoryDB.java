package com.example.goalsetting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryDB {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "title")
    public String title;

    public CategoryDB(@NonNull String title, @NonNull int id) {
        this.title = title;
        this.id = id;
    }

    @NonNull
    public String getCategoryTitle() {
        return this.title;
    }
}

