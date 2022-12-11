package com.example.votingapp.database;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MyDao {
    @Query("DELETE FROM users")
    public void deleteUsers();

    @Query("DELETE FROM votes")
    public void deleteVotes();

    @Query("DELETE FROM user_vote")
    public void deleteUserVote();
}
