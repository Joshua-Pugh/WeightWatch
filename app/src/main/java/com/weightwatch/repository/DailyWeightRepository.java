package com.weightwatch.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.weightwatch.database.AppDatabase;
import com.weightwatch.database.dao.DailyWeightDao;
import com.weightwatch.model.DailyWeight;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DailyWeightRepository {
    private static DailyWeightRepository instance;
    private final DailyWeightDao dailyWeightDao;
    private final ExecutorService executorService;

    public static DailyWeightRepository getInstance(Application application) {
        if (instance == null) {
            instance = new DailyWeightRepository(application);
        }
        return instance;
    }

    // Default constructor
    private DailyWeightRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dailyWeightDao = db.dailyWeightDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Get most recent single daily weight
    public LiveData<DailyWeight> getDailyWeight(String userID, String date) {
        return dailyWeightDao.getDailyWeight(userID, date);
    }

    // Get all daily weights
    public LiveData<List<DailyWeight>> getAllDailyWeights(String userID) {
        return dailyWeightDao.getDailyWeightList(userID);
    }


    // Insert daily weight
    public void insertDailyWeight(DailyWeight entry) {
        executorService.execute(()-> dailyWeightDao.insertDailyWeight(entry));
    }

    // Update daily weight
    public void updateDailyWeight(DailyWeight entry) {
        executorService.execute(()-> dailyWeightDao.updateDailyWeight(entry));
    }

    // Delete daily weight
    public void deleteDailyWeight(DailyWeight entry) {
        executorService.execute(()-> dailyWeightDao.deleteDailyWeight(entry));
    }
}
