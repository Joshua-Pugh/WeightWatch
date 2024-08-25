package com.weightwatch.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.weightwatch.database.dao.DailyWeightDao;
import com.weightwatch.model.DailyWeight;
import com.weightwatch.model.GoalWeight;
import com.weightwatch.database.dao.GoalWeightDao;
import com.weightwatch.model.User;
import com.weightwatch.database.dao.UserDao;

@androidx.room.Database(entities = {User.class, DailyWeight.class, GoalWeight.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // Initialize the Dao's
    public abstract UserDao userDao();
    public abstract DailyWeightDao dailyWeightDao();
    public abstract GoalWeightDao goalWeightDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "user-Database").build(); // For testing only

        }
        return instance;
    }
}
