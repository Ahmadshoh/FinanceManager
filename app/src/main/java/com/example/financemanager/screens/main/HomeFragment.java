package com.example.financemanager.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financemanager.App;
import com.example.financemanager.R;
import com.example.financemanager.screens.dailyExpense.DailyExpenseActivity;
import com.example.financemanager.screens.income.IncomeActivity;
import com.example.financemanager.screens.outlay.OutlayActivity;
import com.example.financemanager.screens.statistic.StatisticActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private Button outlayButton;
    private Button statisticButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button incomeButton = view.findViewById(R.id.incomeButton);
        Button dailyExpenseButton = view.findViewById(R.id.dailyExpenseButton);
        Button outlayButton = view.findViewById(R.id.outlayButton);
        Button statisticButton = view.findViewById(R.id.statisticButton);

        TextView incomeBadge = view.findViewById(R.id.incomeBadge);
        TextView outlayBadge = view.findViewById(R.id.outlayBadge);
        TextView dailyExpenseBadge = view.findViewById(R.id.dailyExpenseBadge);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int month = calendar.get(Calendar.MONTH) + 1;

        dailyExpenseBadge.setText(App.getInstance().getDailyExpenseDao().getDailyExpenseMonthSum(String.valueOf(month)));
        incomeBadge.setText(App.getInstance().getIncomeDao().getSum());
        outlayBadge.setText(App.getInstance().getOutlayDao().getSum());

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncomeActivity.start(requireActivity());
            }
        });

        dailyExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyExpenseActivity.start(requireActivity());
            }
        });

        outlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutlayActivity.start(requireActivity());
            }
        });

        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticActivity.start(requireActivity());
            }
        });
    }
}