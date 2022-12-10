package com.example.votingapp.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    UserEntity getUser(int id);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    UserEntity getUser(String username, String password);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addUser(UserEntity user);
}
