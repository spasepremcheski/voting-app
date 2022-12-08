package com.example.votingapp.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface VoteDao {

    @Query("INSERT INTO votes (question, choice_one, choice_two, choice_three, choice_four, choice_five, start_time, end_time) VALUES "
        + "(:question, :choiceOne, :choiceTwo, :choiceThree, :choiceFour, :choiceFive, :startTime, :endTime)")
    void addVote(String question, String choiceOne, String choiceTwo, String choiceThree, String choiceFour, String choiceFive, String startTime, String endTime);

    @Query("SELECT * FROM votes")
    List<VoteEntity> getAllVotes();

    @Query("SELECT * FROM votes WHERE id = :id")
    VoteEntity getVote(int id);
}
