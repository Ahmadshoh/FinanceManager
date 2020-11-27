package com.example.financemanager.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.financemanager.models.Category;
import com.example.financemanager.models.DailyExpense;
import com.example.financemanager.models.Income;
import com.example.financemanager.models.Outlay;

@Database(entities = {Income.class, DailyExpense.class, Outlay.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IncomeDao incomeDao();
    public abstract DailyExpenseDao dailyExpenseDao();
    public abstract OutlayDao outlayDao();
    public abstract CategoryDao categoryDao();
}
