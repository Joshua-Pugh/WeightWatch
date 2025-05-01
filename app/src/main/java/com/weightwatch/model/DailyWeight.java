package com.weightwatch.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "dailyWeight", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userID",
        childColumns = "relatedUserID",
        onDelete = CASCADE))
public class DailyWeight {

    @PrimaryKey
    @ColumnInfo(name = "weightID")
    @NonNull
    private String weightID;
    @ColumnInfo(name = "relatedUserID")
    private String userID;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "daily_weight")
    private double weight;
    @ColumnInfo(name="timestamp")
    private long timestamp;

    // No argument constructor
    public DailyWeight() {

        this.weightID = UUID.randomUUID().toString();

    }

    // Argument constructor
    public DailyWeight(double t_weight, String t_formattedDate) {
        this.weightID = UUID.randomUUID().toString();
        this.date = t_formattedDate;
        this.weight = t_weight;
    }

    // Getters
    @NonNull
    public String getWeightID() {
        return weightID;
    }

    public String getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public double getWeight() {
        return weight;
    }

    public long getTimestamp() {return timestamp;}

    // Setters
    public void setDate (String t_date) {this.date = t_date;}

    public void setWeight (double t_weight) {
        this.weight = t_weight;
    }

    public void setWeightID(@NonNull String weightID) {
        this.weightID = weightID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
