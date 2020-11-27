package com.example.financemanager.screens.income;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Income;
import com.example.financemanager.screens.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class IncomeActivity extends AppCompatActivity {

    public static void start(Activity caller) {
        Intent intent = new Intent(caller, IncomeActivity.class);

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_income);
        Toolbar toolbar = findViewById(R.id.incomeToolbar);
        setSupportActionBar(toolbar);
        setTitle("Доходы");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.income_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        String sum = App.getInstance().getIncomeDao().getSum();
        TextView sum_text = (TextView) findViewById(R.id.sum_text);
        if (sum == null)
            sum_text.setText("Итого: 0 " + App.getInstance().getIncomeDao().getCurrency());
        else
            sum_text.setText("Итого: " + sum + " " + App.getInstance().getIncomeDao().getCurrency());


        FloatingActionButton addIncomeButton = (FloatingActionButton) findViewById(R.id.addIncomeButton);
        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddIncomeActivity.start(IncomeActivity.this, null);
            }
        });

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getIncomeLiveData().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                adapter.setItems(incomes);
            }
        });
    }
}