package com.example.financemanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.financemanager.models.Outlay;

import java.util.List;

@Dao
public interface OutlayDao {
    @Query("SELECT * FROM outlays")
    List<Outlay> getAll();

    @Query("SELECT * FROM outlays")
    LiveData<List<Outlay>> getAllLiveData();

    @Query("SELECT SUM(amount) FROM outlays")
    String getSum();


//    @Query("SELECT * FROM note WHERE id IN (:userIds)")
//    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM outlays WHERE id = :id LIMIT 1")
    Outlay findById(String id);

    @Query("SELECT currency FROM outlays")
    String getCurrency();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Outlay outlay);

    @Update
    void update(Outlay outlay);

    @Delete
    void delete(Outlay outlay);
}
