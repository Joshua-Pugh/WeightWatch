package com.weightwatch.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weightwatch.model.User;
import com.weightwatch.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private LiveData<User> user;

    // Default constructor
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    // Get user by id
    public LiveData<User> getUserByID(String userID) {
        user = userRepository.getUserByID(userID);

        return user;
    }

    // Get user by userName and password
    public LiveData<User> getUserByCredentials(String userName, String password) {
        user = userRepository.getUserByCredentials(userName, password);

        return user;
    }

    // Insert user
    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    // Update user
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    // Delete user
    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

}
