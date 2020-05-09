package com.example.goalsetting;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categorydb")
    List<CategoryDB> getAll();

    @Query("SELECT * FROM categorydb WHERE id IN (:userIds)")
    List<CategoryDB> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM categorydb WHERE title LIKE :title LIMIT 1")
    CategoryDB findByName(String title);

    @Query("SELECT MAX(id) FROM categorydb")
    int findMaxId();

    @Insert
    void insertAll(CategoryDB... categories);

    @Delete
    void delete(CategoryDB category);
}