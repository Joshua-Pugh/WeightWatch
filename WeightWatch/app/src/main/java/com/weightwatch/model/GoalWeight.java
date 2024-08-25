package com.weightwatch.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;


@Entity(tableName = "goalWeight", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "relatedUserID",
        onDelete = CASCADE))
public class GoalWeight {

    @PrimaryKey
    @ColumnInfo(name = "goalID")
    @NonNull
    private String goalID;
    @ColumnInfo(name = "relatedUserID")
    private String userID;
    @ColumnInfo(name = "goalWeight")
    private double goalWeight;


    // No argument constructor
    public GoalWeight() {
        this.goalID = UUID.randomUUID().toString();
    }

    // Getters
    @NonNull
    public String getGoalID() {
        return goalID;
    }

    public String getUserID() {
        return userID;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    // Setters
    public void setGoalID(@NonNull String goalID) {
        this.goalID = goalID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }


}
