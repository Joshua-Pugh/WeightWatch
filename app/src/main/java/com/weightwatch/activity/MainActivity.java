package com.weightwatch.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.weightwatch.R;
import com.weightwatch.view.UserViewModel;



public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

        Button deleteAccount = findViewById(R.id.btnDeleteAccount);

        // Setup navigation for the app
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.navigation_dailyWeight, R.id.navigation_goalWeight, R.id.navigation_weightDisplay)
                    .build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            NavigationUI.setupWithNavController(navView, navController);

            // Handle delete account button click
            deleteAccount.setOnClickListener(v -> confirmDeleteAccount());
        }
    }

    // Methods for delete account
    private void confirmDeleteAccount() {
        new AlertDialog.Builder(this).setTitle("Delete Account").
                setMessage("Are you sure you want to delete your account? This action cannot be undone.").
                setPositiveButton("Delete", (dialog, which) -> deleteAccount()).
                setNegativeButton("Cancel", null).show();
    }

    private void deleteAccount() {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

       String userID = sharedPreferences.getString("UUID", "");

       userViewModel.getUserByID(userID).observe(this, user-> {
           if (user != null) {
               userViewModel.deleteUser(user);

               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.clear();
               editor.apply();

               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();

           }
       });
    }
}