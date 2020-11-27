package com.example.financemanager.screens.dailyExpense;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Category;
import com.example.financemanager.models.DailyExpense;
import com.example.financemanager.screens.category.CategoryActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddDailyExpenseActivity extends AppCompatActivity {

    private static final String EXTRA_ADD_DAILY_EXPENSE  = "AddDailyExpenseActivity.EXTRA_ADD_DAILY_EXPENSE";
    private static final String SELECTED_DATE = "";
    private EditText dailyExpenseName, dailyExpenseAmount, dailyExpenseDescription, dateSelection;
    private Spinner spinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    DailyExpense dailyExpense;

    public static void start(Activity caller, DailyExpense dailyExpense, String selectedDate) {
        Intent intent = new Intent(caller, AddDailyExpenseActivity.class);

        if (dailyExpense != null) {
            intent.putExtra(EXTRA_ADD_DAILY_EXPENSE, dailyExpense);
        }

        if (selectedDate != null) {
            intent.putExtra(SELECTED_DATE, selectedDate);
        }

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_expense);
        Toolbar toolbar = findViewById(R.id.dailyExpenseToolbar);
        setSupportActionBar(toolbar);
        setTitle("Добавить дневной расход");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        /*
        Initialisation
         */
        Button addButton = findViewById(R.id.addDailyExpenseButton);
        dailyExpenseName = findViewById(R.id.addDailyExpenseName);
        dailyExpenseAmount = findViewById(R.id.addDailyExpenseAmount);
        dailyExpenseDescription = findViewById(R.id.addDailyExpenseDescription);
        dateSelection = findViewById(R.id.dateSelection);
        spinner = (Spinner) findViewById(R.id.category);


        /*
        Spinner settings
         */
        ImageView addCategory = (ImageView) findViewById(R.id.addCategoryView);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryActivity.start(AddDailyExpenseActivity.this);
            }
        });

        final List<Category> spinnerData = new ArrayList<Category>(App.getInstance().getCategoryDao().getAll());

        final ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        /*
        Check if a new expense is added or changes to an existing one
         */
        if (getIntent().hasExtra(EXTRA_ADD_DAILY_EXPENSE)) {
            dailyExpense = getIntent().getParcelableExtra(EXTRA_ADD_DAILY_EXPENSE);
            dailyExpenseName.setText(String.valueOf(dailyExpense.name));
            dailyExpenseAmount.setText(String.valueOf(dailyExpense.amount));
            dailyExpenseDescription.setText(dailyExpense.description);

            Category daily_expense_category = App.getInstance().getCategoryDao().findById(String.valueOf(dailyExpense.category_id));

            for (int counter = 0; counter < spinnerData.size(); counter++) {
                if (daily_expense_category.name.equals(spinnerData.get(counter).name)) {
                    spinner.setSelection(counter);
                }
            }

            addButton.setText("Сохранить");
        } else{
            dailyExpense = new DailyExpense();
        }

        /*
        The date Field settings
         */

        if (getIntent().hasExtra(SELECTED_DATE)) {
            dateSelection.setText(getIntent().getStringExtra(SELECTED_DATE));
        } else if(getIntent().hasExtra(EXTRA_ADD_DAILY_EXPENSE)) {
            dateSelection.setText(dailyExpense.created_at);
        } else {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            String selectedDate = day + "/" + month + "/" + year;
            dateSelection.setText(selectedDate);
        }


        dateSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddDailyExpenseActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;

                dateSelection.setText(date);
            }
        };

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = dailyExpenseName.getText().toString();
                String amount = dailyExpenseAmount.getText().toString();
                String description = dailyExpenseDescription.getText().toString();

                if (name.length() > 0 && amount.length() > 0) {
                    dailyExpense.name = name;
                    dailyExpense.amount = Float.parseFloat(amount);
                    dailyExpense.currency = "tjs";
                    dailyExpense.category_id = getSelectedCategoryId();
                    dailyExpense.created_at = dateSelection.getText().toString();

                    String[] date = dateSelection.getText().toString().split("/");

                    dailyExpense.addedMonth = date[1];

                    if (description.length() > 0) {
                        dailyExpense.description = description;
                    }

                    if (getIntent().hasExtra(EXTRA_ADD_DAILY_EXPENSE)) {
                        App.getInstance().getDailyExpenseDao().update(dailyExpense);
                    } else {
                        App.getInstance().getDailyExpenseDao().insert(dailyExpense);
                    }

                    Toast.makeText(AddDailyExpenseActivity.this, "Расход успешно добавлен", Toast.LENGTH_LONG).show();
                    dailyExpenseName.setText("");
                    dailyExpenseAmount.setText("");
                    dailyExpenseDescription.setText("");
                    spinner.setSelection(0);
                }
            }
        });
    }

    public int getSelectedCategoryId() {
        Category category = (Category) spinner.getSelectedItem();
        return category.id;
    }
}