package com.example.financemanager.screens.statistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> month = new MutableLiveData<>();
    private final MutableLiveData<String> year = new MutableLiveData<>();

    public void setMonth(String Innermonth) {
        month.setValue(Innermonth);
    }

    public LiveData<String> getMonth() {
        return month;
    }

    public void setYear(String innerYear) {
        year.setValue(innerYear);
    }

    public LiveData<String> getYear() {
        return year;
    }
}