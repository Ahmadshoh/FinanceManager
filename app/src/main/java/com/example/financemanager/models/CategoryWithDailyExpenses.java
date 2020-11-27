package com.example.financemanager.models;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithDailyExpenses {

//    public String categoryName;
//    public String name;
//    public float amount;
//    public String created_at;
//    public String addedMonth;
    @Embedded
    public Category category;

    @Relation(parentColumn = "id", entityColumn = "category_id")
    public DailyExpense dailyExpense;
}