package com.example.votingapp.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserVoteDao {

    @Query("INSERT INTO user_vote (user_id, vote_id, choice, time_voted, latitude, longitude) VALUES (:userId, :voteId, :choice, :timeVoted, :latitude, :longitude)")
    void addUserVote(int userId, int voteId, String choice, String timeVoted, double latitude, double longitude);

    @Query("SELECT * FROM user_vote")
    List<UserVoteEntity> getAllVotes();
}
