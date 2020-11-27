package com.example.financemanager.screens.outlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Outlay;
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

public class OutlayActivity extends AppCompatActivity {

    public static void start(Activity caller) {
        Intent intent = new Intent(caller, OutlayActivity.class);

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_outlay);
        Toolbar toolbar = findViewById(R.id.outlayToolbar);
        setSupportActionBar(toolbar);
        setTitle("Расходы");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.outlay_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        String sum = App.getInstance().getOutlayDao().getSum();
        TextView sum_text = (TextView) findViewById(R.id.sum_text);
        if (sum == null)
            sum_text.setText("Итого: 0 " + App.getInstance().getOutlayDao().getCurrency());
        else
            sum_text.setText("Итого: " + sum + " " + App.getInstance().getOutlayDao().getCurrency());


        FloatingActionButton addOutlayButton = (FloatingActionButton) findViewById(R.id.addOutlayButton);
        addOutlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOutlayActivity.start(OutlayActivity.this, null);
            }
        });

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getOutlayLiveData().observe(this, new Observer<List<Outlay>>() {
            @Override
            public void onChanged(List<Outlay> outlays) {
                adapter.setItems(outlays);
            }
        });
    }
}