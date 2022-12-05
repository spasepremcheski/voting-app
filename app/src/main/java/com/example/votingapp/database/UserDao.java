package com.example.votingapp.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    UserEntity getUser(int id);

    @Query("SELECT * FROM users WHERE username = :username & password = :password")
    UserEntity getUser(String username, String password);

    @Query("INSERT INTO users (username, password) VALUES (:username, :password)")
    void addUser(String username, String password);
}
