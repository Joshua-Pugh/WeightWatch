package com.weightwatch.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weightwatch.model.GoalWeight;
import com.weightwatch.repository.GoalWeightRepository;

public class GoalWeightViewModel extends AndroidViewModel {
    private final GoalWeightRepository goalWeightRepository;

    // Default constructor
    public GoalWeightViewModel(@NonNull Application application) {
        super(application);
        goalWeightRepository = GoalWeightRepository.getInstance(application);
    }

    // Get goal weight
    public LiveData<GoalWeight> getGoalWeight(String userID) {
        LiveData<GoalWeight>  goalWeight;
        goalWeight = goalWeightRepository.getGoalWeight(userID);

        return goalWeight;
    }

    // Insert goal weight
    public void insertGoalWeight(GoalWeight entry) {
        goalWeightRepository.insertGoalWeight(entry);
    }

    // Update goal weight
    public void updateGoalWeight(GoalWeight entry) {
        goalWeightRepository.updateGoalWeight(entry);
    }

    // Delete goal weight
    public void deleteGoalWeight(GoalWeight entry) {
        goalWeightRepository.deleteGoalWeight(entry);
    }
}
