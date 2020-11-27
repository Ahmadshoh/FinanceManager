package com.example.financemanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanager.models.Category;
import com.example.financemanager.models.CategoryWithDailyExpenses;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllLiveData();

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    Category findById(String id);
а

    @Transaction
    @Query("SELECT * FROM categories")
    LiveData<List<CategoryWithDailyExpenses>> getCategoryWithDailyExpenses(String addedMonth);


//    @Transaction
//    @Query("SELECT * FROM categories")
//    LiveData<List<CategoryWithExpenses>> getCategoryWitExpensesLiveData();

//    @Transaction
//    @Query("SELECT * FROM daily_expenses WHERE addedMonth=:month")
//    public LiveData<List<CategoryWithExpenses>> getCategoryWithExpensesLiveData(String month);

//    @Query("SELECT categories.name as categoryName, daily_expenses.name as name, daily_expenses.amount as amount, daily_expenses.created_at, daily_expenses.addedMonth FROM categories LEFT JOIN daily_expenses ON categories.id = daily_expenses.category_id WHERE addedMonth=:addedMonth")
//    LiveData<List<CategoryWithExpenses>> getCategoryWithExpensesLiveData(String addedMonth);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
