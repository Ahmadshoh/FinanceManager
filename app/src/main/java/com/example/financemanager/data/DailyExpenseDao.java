package com.example.financemanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanager.models.CategoryWithExpenses;
import com.example.financemanager.models.DailyExpense;

import java.util.List;

@Dao
public interface DailyExpenseDao {
    @Query("SELECT * FROM daily_expenses")
    List<DailyExpense> getAll();

    @Query("SELECT * FROM daily_expenses")
    LiveData<List<DailyExpense>> getAllLiveData();

    @Query("SELECT SUM(amount) FROM daily_expenses")
    String getSum();

    @Query("SELECT * FROM daily_expenses WHERE created_at = :created_at")
    LiveData<List<DailyExpense>> getAllDayLiveData(String created_at);

    @Query("SELECT SUM(amount) FROM daily_expenses WHERE created_at = :created_at")
//    LiveData<String> getDailySum(String created_at);
    String getDailySum(String created_at);

    @Query("SELECT currency FROM daily_expenses")
    String getCurrency();

//    @Query("SELECT daily_expenses.")
//    public List<DailyExpense> getEmployeeWithDepartment();

//    @Query("SELECT * FROM note WHERE id IN (:userIds)")
//    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM daily_expenses WHERE id = :id LIMIT 1")
    DailyExpense findById(String id);

    @Query("SELECT SUM(amount) FROM daily_expenses WHERE addedMonth = :month")
    String getDailyExpenseMonthSum(String month);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DailyExpense dailyExpense);

    @Update
    void update(DailyExpense dailyExpense);

    @Delete
    void delete(DailyExpense dailyExpense);
}
