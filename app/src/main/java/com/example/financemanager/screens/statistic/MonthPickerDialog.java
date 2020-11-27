package com.example.financemanager.screens.statistic;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.financemanager.R;

import java.util.Calendar;

public class MonthPickerDialog extends AppCompatDialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar calendar = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.calendar, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(calendar.get(Calendar.MONTH) + 1);

        int year = calendar.get(Calendar.YEAR);
        yearPicker.setMinValue(1970);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog).setTitle("Выберите месяц и год").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MonthPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

//    public interface MonthPickerDialogListener() {
//        void applyTexts(String year, String month);
//    }
}
























