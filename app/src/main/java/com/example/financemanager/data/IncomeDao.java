package com.example.financemanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financemanager.models.Income;

import java.util.List;

@Dao
public interface IncomeDao {
    @Query("SELECT * FROM incomes")
    List<Income> getAll();

    @Query("SELECT * FROM incomes")
    LiveData<List<Income>> getAllLiveData();

//    @Query("SELECT * FROM note WHERE id IN (:userIds)")
//    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM incomes WHERE id = :id LIMIT 1")
    Income findById(String id);

    @Query("SELECT SUM(amount) FROM incomes")
    String getSum();

    @Query("SELECT currency FROM incomes")
    String getCurrency();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);
}
