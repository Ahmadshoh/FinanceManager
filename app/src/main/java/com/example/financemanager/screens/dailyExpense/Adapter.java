package com.example.financemanager.screens.dailyExpense;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.financemanager.App;
import com.example.financemanager.models.DailyExpense;
import com.example.financemanager.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.financemanager.screens.dailyExpense.Adapter.DailyExpenseViewHolder>{

    private SortedList<DailyExpense> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(DailyExpense.class, new SortedList.Callback<DailyExpense>() {
            @Override
            public int compare(DailyExpense o1, DailyExpense o2) {
                return (int) (o2.amount - o1.amount);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(DailyExpense oldItem, DailyExpense newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(DailyExpense item1, DailyExpense item2) {
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
    public com.example.financemanager.screens.dailyExpense.Adapter.DailyExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.example.financemanager.screens.dailyExpense.Adapter.DailyExpenseViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.financemanager.screens.dailyExpense.Adapter.DailyExpenseViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<DailyExpense> dailyExpenses) {
        sortedList.replaceAll(dailyExpenses);
    }

    static class DailyExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView daily_expense_name, daily_expense_amount, sum_text;
        View delete;
        DailyExpense dailyExpense;


        public DailyExpenseViewHolder(@NonNull final View itemView) {
            super(itemView);

            daily_expense_name = itemView.findViewById(R.id.daily_expense_name);
            daily_expense_amount = itemView.findViewById(R.id.daily_expense_amount);
            sum_text = itemView.findViewById(R.id.sum_text);
            delete = itemView.findViewById(R.id.delete_daily_expense);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddDailyExpenseActivity.start((Activity) itemView.getContext(), dailyExpense, null);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    App.getInstance().getDailyExpenseDao().delete(dailyExpense);

                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(DailyExpense dailyExpense) {
            this.dailyExpense = dailyExpense;

            daily_expense_name.setText(dailyExpense.name);
            daily_expense_amount.setText(String.valueOf(dailyExpense.amount) + " " + dailyExpense.currency);
        }
    }
}
