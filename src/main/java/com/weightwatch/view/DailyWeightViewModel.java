package com.weightwatch.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weightwatch.model.DailyWeight;
import com.weightwatch.repository.DailyWeightRepository;

import java.util.List;

public class DailyWeightViewModel extends AndroidViewModel {
    private final DailyWeightRepository dailyWeightRepository;

    // Default constructor
    public DailyWeightViewModel(@NonNull Application application) {
        super(application);
        dailyWeightRepository = DailyWeightRepository.getInstance(application);
    }

    // Get most recent daily weight
    public LiveData<DailyWeight> getDailyWeight(String userID, String date) {
        LiveData<DailyWeight> dailyWeight;
        dailyWeight = dailyWeightRepository.getDailyWeight(userID, date);

        return dailyWeight;
    }

    // Get all daily weights for the user
    public LiveData<List<DailyWeight>> getDailyWeightList(String userID) {
        LiveData<List<DailyWeight>> dailyWeightList;
        dailyWeightList = dailyWeightRepository.getAllDailyWeights(userID);

        return dailyWeightList;
    }


    // Insert daily weight
    public void insertDailyWeight(DailyWeight entry) {
        dailyWeightRepository.insertDailyWeight(entry);
    }

    public void updateDailyWeight(DailyWeight entry) {
        dailyWeightRepository.updateDailyWeight(entry);
    }

    public void deleteDailyWeight(DailyWeight entry) {
        dailyWeightRepository.deleteDailyWeight(entry);
    }
}
