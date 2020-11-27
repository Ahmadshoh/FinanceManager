package com.example.financemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "outlays")
public class Outlay implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amount")
    public float amount;

    @ColumnInfo(name = "currency", defaultValue = "tjs")
    public String currency;

    @ColumnInfo(name = "description", defaultValue = "")
    public String description;

    public Outlay() {}

    protected Outlay(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readInt();
        description = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outlay outlay = (Outlay) o;
        return id == outlay.id &&
                amount == outlay.amount &&
                Objects.equals(name, outlay.name) &&
                Objects.equals(description, outlay.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, description);
    }

    public static final Creator<Outlay> CREATOR = new Creator<Outlay>() {
        @Override
        public Outlay createFromParcel(Parcel in) {
            return new Outlay(in);
        }

        @Override
        public Outlay[] newArray(int size) {
            return new Outlay[size];
        }
    };

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
    }
}
