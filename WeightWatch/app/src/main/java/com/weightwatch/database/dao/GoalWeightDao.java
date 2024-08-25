package com.weightwatch.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weightwatch.model.GoalWeight;


@Dao
public interface GoalWeightDao {

    // Insert method
    @Insert
    void insertGoalWeight(GoalWeight weight);

    // Update method
    @Update
    void updateGoalWeight(GoalWeight weight);

    // Delete method
    @Delete
    void deleteGoalWeight(GoalWeight weight);

    @Query("SELECT * FROM goalWeight WHERE relatedUserID = :userid")
    LiveData<GoalWeight> getGoalWeight(String userid);

}
