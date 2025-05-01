package com.weightwatch.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.weightwatch.database.AppDatabase;
import com.weightwatch.database.dao.GoalWeightDao;
import com.weightwatch.model.GoalWeight;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoalWeightRepository {
    private static GoalWeightRepository instance;
    private final GoalWeightDao goalWeightDao;
    private final ExecutorService executorService;

    public static GoalWeightRepository getInstance(Application application) {
        if (instance == null) {
            instance = new GoalWeightRepository(application);
        }
        return instance;
    }

    // Default constructor
    private GoalWeightRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        goalWeightDao = db.goalWeightDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Get goal weight
    public LiveData<GoalWeight> getGoalWeight(String userID) {
        return goalWeightDao.getGoalWeight(userID);
    }

    // Insert goal weight
    public void insertGoalWeight(GoalWeight entry) {
        executorService.execute(()-> goalWeightDao.insertGoalWeight(entry));
    }

    // Update goal weight
    public void updateGoalWeight(GoalWeight entry) {
        executorService.execute(()-> goalWeightDao.updateGoalWeight(entry));
    }

    public void deleteGoalWeight(GoalWeight entry) {
        executorService.execute(()-> goalWeightDao.deleteGoalWeight(entry));
    }
}
