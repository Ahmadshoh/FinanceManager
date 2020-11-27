package com.example.financemanager.screens.outlay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.financemanager.App;
import com.example.financemanager.models.Outlay;
import com.example.financemanager.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.OutlayViewHolder>{

    private SortedList<Outlay> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Outlay.class, new SortedList.Callback<Outlay>() {
            @Override
            public int compare(Outlay o1, Outlay o2) {
                return (int) (o2.amount - o1.amount);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Outlay oldItem, Outlay newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Outlay item1, Outlay item2) {
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
    public OutlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutlayViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.outlay_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OutlayViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Outlay> outlays) {
        sortedList.replaceAll(outlays);
    }

    static class OutlayViewHolder extends RecyclerView.ViewHolder {

        TextView outlay_name, outlay_amount;
        View delete;
        Outlay outlay;


        public OutlayViewHolder(@NonNull final View itemView) {
            super(itemView);

            outlay_name = itemView.findViewById(R.id.outlay_name);
            outlay_amount = itemView.findViewById(R.id.outlay_amount);
            delete = itemView.findViewById(R.id.delete_outlay);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    App.getInstance().getOutlayDao().delete(outlay);
                }
            });
        }

        public void bind(Outlay outlay) {
            this.outlay = outlay;

            outlay_name.setText(outlay.name);
            outlay_amount.setText(String.valueOf(outlay.amount + " " + outlay.currency));
        }
    }
}
