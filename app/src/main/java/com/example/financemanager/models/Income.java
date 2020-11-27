package com.example.financemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "incomes")
public class Income implements Parcelable {

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

    public Income() {}

    protected Income(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readFloat();
        description = in.readString();
        currency = in.readString();
    }

    public static final Creator<Income> CREATOR = new Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        @Override
        public Income[] newArray(int size) {
            return new Income[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return id == income.id &&
                Float.compare(income.amount, amount) == 0 &&
                Objects.equals(name, income.name) &&
                Objects.equals(description, income.description) &&
                Objects.equals(currency, income.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, description, currency);
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
    }
}
