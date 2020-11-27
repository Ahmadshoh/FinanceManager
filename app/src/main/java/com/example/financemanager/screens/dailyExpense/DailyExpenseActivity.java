package com.example.financemanager.screens.dailyExpense;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.DailyExpense;
import com.example.financemanager.screens.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DailyExpenseActivity extends AppCompatActivity {

    public static void start(Activity caller) {
        Intent intent = new Intent(caller, DailyExpenseActivity.class);

        caller.startActivity(intent);
    }

    DatePickerDialog.OnDateSetListener setListener;
    private static String selected_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily_expense);
        Toolbar toolbar = findViewById(R.id.dailyExpenseToolbar);
        setSupportActionBar(toolbar);
        setTitle("Дневные расходы");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.daily_expense_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton addDailyExpenseButton = (FloatingActionButton) findViewById(R.id.addDailyExpenseButton);
        final FloatingActionButton datePicker = (FloatingActionButton) findViewById(R.id.showDatePicker);

        addDailyExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDailyExpenseActivity.start(DailyExpenseActivity.this, null, selected_date);
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog;

                if (!selected_date.equals("")) {

                    String[] dateArray = selected_date.split("/");
                    int selectedDay = Integer.parseInt(dateArray[0]);
                    int selectedMonth = Integer.parseInt(dateArray[1]);
                    int selectedYear = Integer.parseInt(dateArray[2]);

                    datePickerDialog = new DatePickerDialog(DailyExpenseActivity.this, R.style.DailyExpenseDatePickerDialog, setListener, selectedYear, selectedMonth-1, selectedDay);

                } else {
                    Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(DailyExpenseActivity.this, R.style.DailyExpenseDatePickerDialog, setListener, year, month, day);
                }

                Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;

                selected_date = day + "/" + month + "/" + year;

                mainViewModel(adapter, selected_date);
                getDailySum(selected_date);
            }
        };

        if (!selected_date.equals("")) {
            mainViewModel(adapter, selected_date);
            getDailySum(selected_date);
        } else {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH) + 1;
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            mainViewModel(adapter, day + "/" + month + "/" + year);
            getDailySum(day + "/" + month + "/" + year);
        }
    }

    protected void mainViewModel(final Adapter adapter, String date) {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getDailyExpenseLiveData(date).observe(this, new Observer<List<DailyExpense>>() {
            @Override
            public void onChanged(List<DailyExpense> dailyExpenses) {
                adapter.setItems(dailyExpenses);
            }
        });
    }

    protected void getDailySum(String date) {
        String sum = App.getInstance().getDailyExpenseDao().getDailySum(date);;

        TextView sum_text = (TextView) findViewById(R.id.daily_expense_sum_text);

        if (sum == null)
            sum_text.setText("Итого: 0");
        else
            sum_text.setText("Итого: " + sum + " " + App.getInstance().getDailyExpenseDao().getCurrency());
    }
}