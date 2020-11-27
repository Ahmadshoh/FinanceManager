package com.example.financemanager;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.financemanager.data.AppDatabase;
import com.example.financemanager.data.CategoryDao;
import com.example.financemanager.data.DailyExpenseDao;
import com.example.financemanager.data.IncomeDao;
import com.example.financemanager.data.OutlayDao;

public class App extends Application {

    private AppDatabase database;
    private IncomeDao incomeDao;
    private DailyExpenseDao dailyExpenseDao;
    private OutlayDao outlayDao;
    private CategoryDao categoryDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "finance_manager").allowMainThreadQueries().build();

        incomeDao = database.incomeDao();
        dailyExpenseDao = database.dailyExpenseDao();
        outlayDao = database.outlayDao();
        categoryDao = database.categoryDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }


    public IncomeDao getIncomeDao() {
        return incomeDao;
    }

    public void setIncomeDao(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public DailyExpenseDao getDailyExpenseDao() {
        return dailyExpenseDao;
    }

    public void setDailyExpenseDao(DailyExpenseDao dailyExpenseDao) {
        this.dailyExpenseDao = dailyExpenseDao;
    }

    public OutlayDao getOutlayDao() {
        return outlayDao;
    }

    public void setOutlayDao(OutlayDao outlayDao) {
        this.outlayDao = outlayDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
