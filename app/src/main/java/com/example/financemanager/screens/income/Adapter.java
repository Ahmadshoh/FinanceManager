package com.example.financemanager.screens.income;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.financemanager.App;
import com.example.financemanager.models.Income;
import com.example.financemanager.R;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.IncomeViewHolder>{

    private SortedList<Income> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Income.class, new SortedList.Callback<Income>() {
            @Override
            public int compare(Income o1, Income o2) {
                return (int) (o2.amount - o1.amount);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Income oldItem, Income newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Income item1, Income item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IncomeViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.income_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Income> incomes) {
        sortedList.replaceAll(incomes);
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {

        TextView income_amount, income_name, sum_text;
        View delete;
        Income income;


        public IncomeViewHolder(@NonNull final View itemView) {
            super(itemView);

            income_name = itemView.findViewById(R.id.income_name);
            income_amount = itemView.findViewById(R.id.income_amount);
            sum_text = itemView.findViewById(R.id.sum_text);
            delete = itemView.findViewById(R.id.delete_income);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddIncomeActivity.start((Activity) itemView.getContext(), income);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    App.getInstance().getIncomeDao().delete(income);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Income income) {
            this.income = income;

            income_name.setText(income.name);
            income_amount.setText(String.valueOf(income.amount) + " " + income.currency);
        }
    }
}
