package com.example.financemanager.screens.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Category;
import com.example.financemanager.screens.MainViewModel;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    public static void start(Activity caller) {
        Intent intent = new Intent(caller, CategoryActivity.class);

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.dailyExpenseToolbar);
        setSupportActionBar(toolbar);
        setTitle("Категории");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final Category category = new Category();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);


        Button addButton = findViewById(R.id.addCategoryButton);
        final EditText categoryName = findViewById(R.id.categoryName);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = categoryName.getText().toString();

                if (name.length() > 0) {
                    category.name = name;

                    App.getInstance().getCategoryDao().insert(category);

                    Toast.makeText(getApplicationContext(), "Категория " + name + " успешно добавлена!", Toast.LENGTH_LONG).show();

                    categoryName.setText("");
                }
            }
        });

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setItems(categories);
            }
        });
    }
}
