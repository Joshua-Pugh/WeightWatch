package com.weightwatch.activity;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.weightwatch.R;
import com.weightwatch.model.User;
import com.weightwatch.view.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private boolean isToastShown = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("MyAppPrefs",
                MODE_PRIVATE);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText userName = findViewById(R.id.etUsername);
        EditText userPassword = findViewById(R.id.etPassword);
        EditText confirmPassword = findViewById(R.id.etConfirmPassword);
        TextView login_register = findViewById(R.id.tvRegister_Login);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnRegister = findViewById(R.id.btnRegister);


        // Handle Login button click
        btnLogin.setOnClickListener(view -> {

            isToastShown = false;

            String enteredUsername = userName.getText().toString();
            String enteredPassword = userPassword.getText().toString();

            userViewModel.getUserByCredentials(enteredUsername, enteredPassword).observe(this, user -> {
                // Check if the user already exists
                if (user != null) {
                    // User exists

                    if (enteredPassword.equals(user.getUserPassword())) {
                        // Correct password, proceed to the next screen
                        sharedPreferences.edit().putString("UUID",
                                user.getUserID()).apply();

                        startMainActivity();

                    } else {
                        showToast("Incorrect username or password");
                    }
                } else {
                    showToast("User does not exist");
                }

            });

        });

        // Handle create account button click
        btnCreateAccount.setOnClickListener(view -> {
            // Toggle UI elements
            userName.setText("");
            userPassword.setText("");
            String buttonText = "Register";
            login_register.setText(buttonText);
            btnLogin.setEnabled(false);
            btnLogin.setVisibility(View.GONE);
            btnCreateAccount.setEnabled(false);
            btnCreateAccount.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
        });

        // Handle Register button click
        btnRegister.setOnClickListener(view -> {

            isToastShown = false;

            String enteredUsername = userName.getText().toString();
            String enteredPassword = userPassword.getText().toString();
            String enteredConfirmPassword = confirmPassword.getText().toString();


            // Validate input fields
            if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
                userViewModel.getUserByCredentials(enteredUsername, enteredPassword).observe(this, user -> {
                    if (user != null) {
                        // Username already taken
                        showToast("Username already in use.");
                    }
                    else {
                        if (!enteredConfirmPassword.equals(enteredPassword)) {
                            // Passwords do not match
                            showToast("Passwords do not match.");

                        } else {
                            // Create the new user and insert the user data into the database
                            User newUser = new User(enteredUsername, enteredPassword);

                            sharedPreferences.edit().putString("UUID",
                                    newUser.getUserID()).apply();


                            userViewModel.insertUser(newUser);

                            showToast("Account created successfully");

                            // Proceed to the next screen
                            startMainActivity();
                        }
                    }
                });
            }
            else {
               showToast("Username and Password fields cannot be left empty.");
            }
        });
    }

    // Method to start the main activity
    private void startMainActivity () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to check if toast has already been displayed
    private void showToast(String message) {
        if (!isToastShown) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            isToastShown = true;
        }
    }
}