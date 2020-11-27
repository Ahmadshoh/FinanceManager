package com.example.financemanager.screens.income;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Income;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddIncomeActivity extends AppCompatActivity {

    private static final String EXTRA_ADD_INCOME  = "AddIncomeActivity.EXTRA_ADD_INCOME";
    private EditText incomeName, incomeAmount, incomeDescription;

    Income income;

    public static void start(Activity caller, Income income) {
        Intent intent = new Intent(caller, AddIncomeActivity.class);

        if (income != null) {
            intent.putExtra(EXTRA_ADD_INCOME, income);
        }

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        Toolbar toolbar = findViewById(R.id.incomeToolbar);
        setSupportActionBar(toolbar);
        setTitle("Добавить доход");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Button addButton = findViewById(R.id.addIncomeBtn);
        incomeName = findViewById(R.id.addIncomeName);
        incomeAmount = findViewById(R.id.addIncomeAmount);
        incomeDescription = findViewById(R.id.addIncomeDescription);


        if (getIntent().hasExtra(EXTRA_ADD_INCOME)) {
            income = getIntent().getParcelableExtra(EXTRA_ADD_INCOME);
            incomeName.setText(String.valueOf(income.name));
            incomeAmount.setText(String.valueOf(income.amount));
            incomeDescription.setText(income.description);
        } else{
            income = new Income();
        }


        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = incomeName.getText().toString();
                String amount = incomeAmount.getText().toString();
                String description = incomeDescription.getText().toString();

                if (name.length() > 0 && amount.length() > 0) {
                    income.name = name;
                    income.amount = Float.parseFloat(amount);
                    income.currency = "tjs";

                    if (description.length() > 0) {
                        income.description = description;
                    }

                    if (getIntent().hasExtra(EXTRA_ADD_INCOME)) {
                        App.getInstance().getIncomeDao().update(income);
                    } else {
                        App.getInstance().getIncomeDao().insert(income);
                    }

                    finish();
                }
            }
        });
    }
}