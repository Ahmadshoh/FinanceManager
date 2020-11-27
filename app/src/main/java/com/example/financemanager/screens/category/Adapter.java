package com.example.financemanager.screens.category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.financemanager.App;
import com.example.financemanager.models.Category;
import com.example.financemanager.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<com.example.financemanager.screens.category.Adapter.CategoryViewHolder>{

    private SortedList<Category> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Category.class, new SortedList.Callback<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return (int) (o2.id - o1.id);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Category oldItem, Category newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Category item1, Category item2) {
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
    public com.example.financemanager.screens.category.Adapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.example.financemanager.screens.category.Adapter.CategoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.financemanager.screens.category.Adapter.CategoryViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        View delete;
        Category category;


        public CategoryViewHolder(@NonNull final View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.categoryName);
            delete = itemView.findViewById(R.id.delete_category);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AddDailyExpenseActivity.start((Activity) itemView.getContext(), dailyExpense, null);
//                }
//            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    App.getInstance().getCategoryDao().delete(category);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(Category category) {
            this.category = category;

            categoryName.setText(category.name);

//            daily_expense_name.setText(dailyExpense.name);
//            daily_expense_amount.setText(String.valueOf(dailyExpense.amount) + " " + dailyExpense.currency);
        }
    }
}
