package com.example.financemanager.screens.outlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.models.Outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddOutlayActivity extends AppCompatActivity {

    private static final String EXTRA_ADD_OUTLAY  = "AddIncomeActivity.EXTRA_ADD_OUTLAY";
    private EditText outlayName, outlayAmount, outlayDescription;

    Outlay outlay;

    public static void start(Activity caller, Outlay outlay) {
        Intent intent = new Intent(caller, AddOutlayActivity.class);

        if (outlay != null) {
            intent.putExtra(EXTRA_ADD_OUTLAY, outlay);
        }

        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_outlay);
        Toolbar toolbar = findViewById(R.id.outlayToolbar);
        setSupportActionBar(toolbar);
        setTitle("Добавить расход");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Button addButton = findViewById(R.id.addOutlayButton);
        outlayName = findViewById(R.id.addOutlayName);
        outlayAmount = findViewById(R.id.addOutlayAmount);
        outlayDescription = findViewById(R.id.addOutlayDescription);


        if (getIntent().hasExtra(EXTRA_ADD_OUTLAY)) {
            outlay = getIntent().getParcelableExtra(EXTRA_ADD_OUTLAY);
            outlayName.setText(String.valueOf(outlay.name));
            outlayAmount.setText(String.valueOf(outlay.amount));
            outlayDescription.setText(outlay.description);
        } else{
            outlay = new Outlay();
        }

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = outlayName.getText().toString();
                String amount = outlayAmount.getText().toString();
                String description = outlayDescription.getText().toString();

                if (name.length() > 0 && amount.length() > 0) {
                    outlay.name = name;
                    outlay.amount = Float.parseFloat(amount);
                    outlay.currency = "tjs";

                    if (description.length() > 0) {
                        outlay.description = description;
                    }

                    if (getIntent().hasExtra(EXTRA_ADD_OUTLAY)) {
                        App.getInstance().getOutlayDao().update(outlay);
                    } else {
                        App.getInstance().getOutlayDao().insert(outlay);
                    }

                    finish();
                }
            }
        });
    }
}