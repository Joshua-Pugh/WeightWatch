package com.weightwatch.activity;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.tasks.Task;
import com.weightwatch.R;
import com.weightwatch.model.DailyWeight;
import com.weightwatch.view.DailyWeightViewModel;
import com.weightwatch.view.GoalWeightViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/* SMS code references:
 * https://developers.google.com/identity/smartlock-passwords/android/retrieve-hints      *
 * https://stackoverflow.com/questions/47216187/phone-selector-api-in-android             *
 * https://www.geeksforgeeks.org/how-to-use-phone-selector-api-in-android/                *
 * https://developer.android.com/reference/android/telephony/SmsManager.html#getDefault() */

public class DailyWeightScreen extends Fragment {

    private String userID;
    private DailyWeightViewModel dailyWeightViewModel;
    SharedPreferences sharedPreferences;
    private boolean isToastShown = false;

    // For user with SMS
    private String userPhoneNumber;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted-> {
                if (isGranted) {
                    sendSms();
                }
                else {
                    showToast("SMS permission denied");
                }
            });


    private final ActivityResultLauncher<IntentSenderRequest> phoneNumberHintResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result-> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        userPhoneNumber = Identity.getSignInClient(requireContext()).getPhoneNumberFromIntent(result.getData());
                        requestSmsPermission();
                    }catch (Exception e) {
                        Log.e("MainActivity", "Phone Number Hint Failed", e);
                        showToast("Failed to retrieve phone number");
                    }
                }
            });


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View parentView = inflater.inflate(R.layout.fragment_daily_weight, container, false);

        EditText etDailyWeight = parentView.findViewById(R.id.etDailyWeight);
        Button submitWeight = parentView.findViewById(R.id.btnSubmitWeight);

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);

        userID = sharedPreferences.getString("UUID", "");

        dailyWeightViewModel = new ViewModelProvider(this).get(DailyWeightViewModel.class);

        // Get the current date
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        // Format the date ("MM-dd-yyyy")
        String formattedDate = dateFormat.format(currentDate);

        // Check if the users goal weight is = to the most recent daily wight
        dailyWeightViewModel.getDailyWeight(userID, formattedDate).observe(getViewLifecycleOwner(), weightEntry-> {
            if (weightEntry != null) {

                double currentWeight = weightEntry.getWeight();
                GoalWeightViewModel goalWeightViewModel = new ViewModelProvider(this).get(GoalWeightViewModel.class);

                // Get the goal weight
                goalWeightViewModel.getGoalWeight(userID).observe(getViewLifecycleOwner(), goalWeight -> {
                    if (goalWeight != null) {
                        double currentGoalWeight;
                        currentGoalWeight = goalWeight.getGoalWeight();

                        if (currentWeight == currentGoalWeight) {

                            // Request phone number hint to use to send SMS
                            requestHint();
                            goalWeightViewModel.deleteGoalWeight(goalWeight);
                        }
                    }
                });
            }
        });


        // Handle submit weight button click
        submitWeight.setOnClickListener(view -> {
            isToastShown = false;

            // Get the weight entered by the user
            String enteredWeight = etDailyWeight.getText().toString();
            if (!enteredWeight.isEmpty()) {

                double weight = Double.parseDouble(enteredWeight);

                DailyWeight dailyWeight = new DailyWeight(weight, formattedDate);
                dailyWeight.setUserID(userID);
                dailyWeight.setTimestamp(System.currentTimeMillis());

                dailyWeightViewModel.insertDailyWeight(dailyWeight);

                etDailyWeight.setText("");

                showToast("Weight submitted of: " + weight +  "lbs on "
                        + formattedDate);

            }
            else {

                showToast("Please enter a valid weight");
            }
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

    // Method to request the hint for the user phone number
    private void requestHint() {
        GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();
        Task<PendingIntent> intentTask = Identity.getSignInClient(requireContext()).getPhoneNumberHintIntent(request);

        intentTask.addOnSuccessListener(pendingIntent -> {
            try {
                IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(pendingIntent).build();

                phoneNumberHintResultLauncher.launch(intentSenderRequest);
            } catch (Exception e) {
                Log.e("MainActivity", "Phone Number Hint failed", e);
                showToast("Failed to get phone number hint");
            }
        }).addOnFailureListener(e -> {
            Log.e("MainActivity", "Phone Number Hint failed", e);
            showToast("Failed to get phone number hint");
        });
    }

    // Method to request SMS permission
    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
        }
        else {
            // Permission already granted
            sendSms();
        }
    }

    // Method to send the sms to the users phone
    private void sendSms() {
        if (userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
            String message = "Congratulations! You have reached your goal weight.";

            SmsManager smsManager = requireContext().getSystemService(SmsManager.class);

            smsManager.sendTextMessage(userPhoneNumber, null, message, null, null);
            showToast("SMS sent to " + userPhoneNumber);

        }
        else {
            showToast("Phone number not available");
        }
    }
}