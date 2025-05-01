package com.weightwatch.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.weightwatch.database.AppDatabase;
import com.weightwatch.database.dao.UserDao;
import com.weightwatch.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance;
    private final UserDao userDao;
    private final ExecutorService executorService;

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }
        return instance;
    }

    // Default constructor
    private UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Get user by id
    public LiveData<User> getUserByID(String userID) {
        return userDao.getUserById(userID);
    }

    // Get user by Credentials
    public LiveData<User> getUserByCredentials(String userName, String password) {
        return userDao.getUserByCredentials(userName, password);
    }

    // Insert user
    public void insertUser(User user) {
        executorService.execute(()-> userDao.insertUser(user));
    }

    // Update user
    public void updateUser(User user) {
        executorService.execute(()-> userDao.updateUser(user));
    }

    // Delete user
    public void deleteUser(User user) {
        executorService.execute(()-> userDao.deleteUser(user));
    }
}
