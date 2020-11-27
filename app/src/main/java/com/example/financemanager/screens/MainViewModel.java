package com.example.financemanager.screens;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.financemanager.App;
import com.example.financemanager.models.Category;
import com.example.financemanager.models.CategoryWithExpenses;
import com.example.financemanager.models.DailyExpense;
import com.example.financemanager.models.Income;
import com.example.financemanager.models.Outlay;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainViewModel extends ViewModel {

    protected static LiveData<List<DailyExpense>> dailyExpenseLiveData(String date) {
        if (date != null) {
            return App.getInstance().getDailyExpenseDao().getAllDayLiveData(date);
        }

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        String todayDate = day + "/" + month + "/" + year;

        return App.getInstance().getDailyExpenseDao().getAllDayLiveData(todayDate);
    }

    protected static LiveData<List<CategoryWithExpenses>> categoryWithExpensesLiveData(String month) {
        return App.getInstance().getCategoryDao().getCategoryWithExpensesLiveData(month);
    }

    private LiveData<List<Income>> incomeLiveData = App.getInstance().getIncomeDao().getAllLiveData();
    private LiveData<List<Outlay>> outlayLiveData = App.getInstance().getOutlayDao().getAllLiveData();
    private LiveData<List<Category>> categoryLiveData = App.getInstance().getCategoryDao().getAllLiveData();
//    private LiveData<List<CategoryWithExpenses>> categoryWitExpensesLiveData = App.getInstance().getCategoryDao().getCategoryWithExpensesLiveData();

    public LiveData<List<Income>> getIncomeLiveData() {
        return incomeLiveData;
    }

    public LiveData<List<DailyExpense>> getDailyExpenseLiveData(String date) {
        return dailyExpenseLiveData(date);
    }

    public LiveData<List<Outlay>> getOutlayLiveData() {
        return outlayLiveData;
    }

    public LiveData<List<Category>> getCategoryLiveData() {
        return categoryLiveData;
    }

    public LiveData<List<CategoryWithExpenses>> getCategoryWithExpensesLiveData(String month) {
        return categoryWithExpensesLiveData(month);
    }
}