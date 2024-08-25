package com.weightwatch.activity;

import static android.content.Context.MODE_PRIVATE;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.weightwatch.R;
import com.weightwatch.model.GoalWeight;
import com.weightwatch.view.GoalWeightViewModel;



public class GoalWeightScreen extends Fragment {

    private GoalWeightViewModel goalWeightViewModel;
    private String userID;
    private SharedPreferences sharedPreferences;
    private boolean isToastShown = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_goal_weight, container, false);

        TextView currentGoalWeight = parentView.findViewById(R.id.tvCurrentGoalWeight);
        EditText etGoalWeight = parentView.findViewById(R.id.etGoalWeight);
        Button submitGoalWeight = parentView.findViewById(R.id.btnSubmitGoalWeight);
        Button deleteGoalWeight = parentView.findViewById(R.id.btnDeleteGoal);

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);
        goalWeightViewModel = new ViewModelProvider(this).get(GoalWeightViewModel.class);
        userID = sharedPreferences.getString("UUID", "");

        String savedGoalWeight = sharedPreferences.getString("savedGoalWeight", "");

        // Check if the user has created a goal weight already and update UI accordingly
        if (!savedGoalWeight.isEmpty()) {
            String displayMessage = "Current Goal Weight: " + savedGoalWeight;
            currentGoalWeight.setText(displayMessage);

            etGoalWeight.setEnabled(false);
            etGoalWeight.setVisibility(View.GONE);
            submitGoalWeight.setEnabled(false);
            submitGoalWeight.setVisibility(View.GONE);
            currentGoalWeight.setVisibility(View.VISIBLE);
            deleteGoalWeight.setEnabled(true);
            deleteGoalWeight.setVisibility(View.VISIBLE);
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("savedGoalWeight", ""); // delete the goal weight
            editor.apply();
            currentGoalWeight.setText("");
            currentGoalWeight.setVisibility(View.GONE);
            deleteGoalWeight.setEnabled(false);
            deleteGoalWeight.setVisibility(View.GONE);
            etGoalWeight.setEnabled(true);
            etGoalWeight.setVisibility(View.VISIBLE);
            submitGoalWeight.setEnabled(true);
            submitGoalWeight.setVisibility(View.VISIBLE);
        }



        goalWeightViewModel.getGoalWeight(userID).observe(getViewLifecycleOwner(), goalWeight -> {
            // Update UI when goal weight changes
            if (goalWeight != null) {

                String weightString = "Current Goal Weight: " + (goalWeight.getGoalWeight()) + " lbs";

                etGoalWeight.setEnabled(false);
                etGoalWeight.setVisibility(View.GONE);
                submitGoalWeight.setEnabled(false);
                submitGoalWeight.setVisibility(View.GONE);
                currentGoalWeight.setText(weightString);
                currentGoalWeight.setVisibility(View.VISIBLE);
                deleteGoalWeight.setEnabled(true);
                deleteGoalWeight.setVisibility(View.VISIBLE);

            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("savedGoalWeight", ""); // delete the goal weight
                editor.apply();
                currentGoalWeight.setText("");
                currentGoalWeight.setVisibility(View.GONE);
                deleteGoalWeight.setEnabled(false);
                deleteGoalWeight.setVisibility(View.GONE);
                etGoalWeight.setEnabled(true);
                etGoalWeight.setVisibility(View.VISIBLE);
                submitGoalWeight.setEnabled(true);
                submitGoalWeight.setVisibility(View.VISIBLE);
            }

        });

        // Handle new goal weight entry
        submitGoalWeight.setOnClickListener(view -> {
            isToastShown = false;

            GoalWeight goalWeight = new GoalWeight();

            String enteredWeight = etGoalWeight.getText().toString();
            if (!enteredWeight.isEmpty()) {
                double weight = Double.parseDouble(enteredWeight);
                String weightString = Double.toString(weight);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("savedGoalWeight", weightString); // Save the goal weight
                editor.apply();

                if (currentGoalWeight.getText().toString().isEmpty()) {
                    goalWeight.setUserID(userID);
                    goalWeight.setGoalWeight(weight);

                    goalWeightViewModel.insertGoalWeight(goalWeight);

                    etGoalWeight.setText("");

                    showToast("Goal Weight submitted of: " + weight
                            + " lbs on");
                } else {

                    showToast("Please enter a valid weight");
                }
            }
        });

        // Handle delete goal weight
        deleteGoalWeight.setOnClickListener(view -> {
            isToastShown = false;

            goalWeightViewModel.getGoalWeight(userID).observe(getViewLifecycleOwner(), entry-> {
                if (entry != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("savedGoalWeight"); // Remove the goal weight
                    editor.apply();

                    currentGoalWeight.setText("");
                    goalWeightViewModel.deleteGoalWeight(entry);


                    showToast("Goal Weight deleted");

                } else {
                    showToast("There is nothing to delete");
                }
            });

        });


        // Inflate the layout for this fragment
        return parentView;
    }


    // Method to check if toast has already been displayed
    private void showToast(String message) {
        if (!isToastShown) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            isToastShown = true;
        }
    }
}