package com.example.votingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "votes")
public class VoteEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "question")
    String question;

    @ColumnInfo(name = "choice_one")
    String choiceOne;
    @ColumnInfo(name = "choice_two")
    String choiceTwo;
    @ColumnInfo(name = "choice_three")
    String choiceThree;
    @ColumnInfo(name = "choice_four")
    String choiceFour;
    @ColumnInfo(name = "choice_five")
    String choiceFive;

    @ColumnInfo(name = "start_time")
    String startTime;

    @ColumnInfo(name = "end_time")
    String endTime;
}

