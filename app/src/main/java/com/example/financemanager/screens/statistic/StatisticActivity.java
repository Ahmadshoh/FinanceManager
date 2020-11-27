package com.example.financemanager.screens.statistic;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Category;
import com.example.financemanager.models.CategoryWithExpenses;
import com.example.financemanager.screens.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class StatisticActivity extends AppCompatActivity {

    public static void start(Activity caller) {
        Intent intent = new Intent(caller, StatisticActivity.class);

        caller.startActivity(intent);
    }

    private String selectedYear;
    private String selectedMonth;
    DatePickerDialog.OnDateSetListener setListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);
        Toolbar toolbar = findViewById(R.id.statisticToolbar);
        setSupportActionBar(toolbar);
        setTitle("Статистика");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.statistic_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Button test = findViewById(R.id.test);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH) + 1;
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

//                List<CategoryWithExpenses> data = App.getInstance().getCategoryDao().getCategoryTest(String.valueOf(month));
//                System.out.println(data);

                LiveData<List<CategoryWithExpenses>> data = App.getInstance().getCategoryDao().getCategoryWithExpensesLiveData(String.valueOf(month));
                System.out.println(data.toString());
            }
        });

        final SharedViewModel model = new SharedViewModel();
        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton calendarButton = (FloatingActionButton) findViewById(R.id.statisticCalendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog pd = new MonthPickerDialog();
                pd.setListener(setListener);
                pd.show(getSupportFragmentManager(), "Choose the month");
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                selectedYear = String.valueOf(year);
                selectedMonth = String.valueOf(month);
                model.setMonth(String.valueOf(month));
                model.setYear(String.valueOf(year));

//                mainViewModel(adapter);
            }
        };

        mainViewModel(adapter);
    }

    protected void mainViewModel(final Adapter adapter) {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getCategoryWithExpensesLiveData("10").observe(this, new Observer<List<CategoryWithExpenses>>() {
            @Override
            public void onChanged(List<CategoryWithExpenses> categoryWithExpenses) {
                adapter.setItems(categoryWithExpenses);
            }
        });
    }
}
