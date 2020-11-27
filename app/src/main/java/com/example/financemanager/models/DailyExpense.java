package com.example.financemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "daily_expenses")
public class DailyExpense implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amount")
    public float amount;

    @ColumnInfo(name = "description", defaultValue = "")
    public String description;

    @ColumnInfo(name = "currency", defaultValue = "tjs")
    public String currency;

    @ColumnInfo(name = "category_id")
    public int category_id;

    @ColumnInfo(name = "addedMonth")
    public String addedMonth;

    @ColumnInfo(name = "created_at")
    public String created_at;

    public DailyExpense() {}

    protected DailyExpense(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readFloat();
        description = in.readString();
        currency = in.readString();
        category_id = in.readInt();
        addedMonth = in.readString();
        created_at = in.readString();
    }

    public static final Creator<DailyExpense> CREATOR = new Creator<DailyExpense>() {
        @Override
        public DailyExpense createFromParcel(Parcel in) {
            return new DailyExpense(in);
        }

        @Override
        public DailyExpense[] newArray(int size) {
            return new DailyExpense[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyExpense that = (DailyExpense) o;
        return id == that.id &&
                Float.compare(that.amount, amount) == 0 &&
                category_id == that.category_id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(addedMonth, that.addedMonth) &&
                Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, description, currency, category_id, addedMonth, created_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(amount);
        parcel.writeString(description);
        parcel.writeString(currency);
        parcel.writeInt(category_id);
        parcel.writeString(addedMonth);
        parcel.writeString(created_at);
    }
}
