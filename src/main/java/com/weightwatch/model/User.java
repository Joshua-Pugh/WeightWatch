package com.weightwatch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;


@Entity(tableName = "user")
public class User {


    @PrimaryKey
    @ColumnInfo(name = "userID")
    @NonNull
    private String userID;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "password")
    private String userPassword;

    // No argument constructor
    public User() {
        userID = UUID.randomUUID().toString();
    }

    // Argument constructor
    public User(String name, String password) {
        this.userID = UUID.randomUUID().toString();
        this.userName = name;
        this.userPassword = password;
    }

    // Getters
    @NonNull
    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    // Setters
    public void setUserID(@NonNull String t_ID) {
        this.userID = t_ID;
    }

    public void setUserName(String t_userName) {
        this.userName = t_userName;
    }

    public void setUserPassword(String t_userPassword) {
        this.userPassword = t_userPassword;
    }

}
