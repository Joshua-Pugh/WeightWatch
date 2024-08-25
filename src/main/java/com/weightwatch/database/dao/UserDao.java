package com.weightwatch.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weightwatch.model.User;

@Dao
public interface UserDao {

    // Insert method
    @Insert
    void insertUser(User user);

    // Update methods
    @Update
    void updateUser(User user);

    // Delete method
    @Delete
    void deleteUser(User user);

    // Query to find the user by their name and password
    @Query("SELECT * FROM user WHERE user_name = :username AND password = :password")
    LiveData<User> getUserByCredentials(String username, String password);

    // Query to find user by their id
    @Query("SELECT * FROM user WHERE userID = :userid")
    LiveData<User> getUserById(String userid);
}
