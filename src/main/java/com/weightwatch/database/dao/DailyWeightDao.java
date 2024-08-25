package com.weightwatch.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weightwatch.model.DailyWeight;

import java.util.List;

@Dao
public interface DailyWeightDao {

    // Insert method
    @Insert
    void insertDailyWeight(DailyWeight dailyWeight);

    // Update method
    @Update
    void updateDailyWeight(DailyWeight entry);

    // Delete method
    @Delete
    void deleteDailyWeight(DailyWeight entry);

    // Query to get a single weight entry ordered by date
    @Query("SELECT * FROM dailyWeight WHERE relatedUserID = :userID AND :date ORDER BY timestamp DESC LIMIT 1")
    LiveData<DailyWeight> getDailyWeight(String userID, String date);

    // Query to get all weight entries for the user
    @Query("SELECT * FROM dailyWeight WHERE relatedUserID = :userid")
    LiveData<List<DailyWeight>> getDailyWeightList(String userid);

}


