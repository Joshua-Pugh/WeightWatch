package com.weightwatch.activity;



import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;



import com.weightwatch.R;
import com.weightwatch.model.DailyWeight;
import com.weightwatch.view.DailyWeightViewModel;
import com.weightwatch.view.WeightAdapter;


import java.util.ArrayList;
import java.util.List;



public class WeightDisplayScreen extends Fragment {

    private WeightAdapter adapter;
    private DailyWeightViewModel dailyWeightViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_weight_display, container, false);

        List<DailyWeight> dailyWeightList = new ArrayList<>();

        GridView gridView = parentView.findViewById(R.id.gridViewWeight);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);

        String userID = sharedPreferences.getString("UUID", "");

        // Set up the adapter
        adapter = new WeightAdapter(requireContext(), dailyWeightList, this::deleteWeightEntry);

        // Set the grid view
        gridView.setAdapter(adapter);

        // Initialize ViewModel
        dailyWeightViewModel = new ViewModelProvider(requireActivity()).get(DailyWeightViewModel.class);

        // Observe the weight entries
        dailyWeightViewModel.getDailyWeightList(userID).observe(getViewLifecycleOwner(), entries-> {
            // Clear the list
            dailyWeightList.clear();

            // Add the live data to the list
            dailyWeightList.addAll(entries);

            // Notify the adapter of changes
            adapter.notifyDataSetChanged();

        });

        // Inflate the layout for this fragment
        return parentView;
    }


    public void deleteWeightEntry(DailyWeight entry) {

        dailyWeightViewModel.deleteDailyWeight(entry);
    }

}