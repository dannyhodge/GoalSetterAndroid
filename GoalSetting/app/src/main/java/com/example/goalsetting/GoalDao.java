package com.example.goalsetting;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goaldb")
    List<GoalDB> getAll();

    @Query("SELECT * FROM goaldb WHERE id IN (:userIds)")
    List<GoalDB> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM goaldb WHERE title LIKE :title LIMIT 1")
    GoalDB findByName(String title);

    @Query("SELECT COUNT(*) FROM goaldb")
    int getCount();

    @Insert
    void insertAll(GoalDB... category);

    @Delete
    void delete(GoalDB goal);
}