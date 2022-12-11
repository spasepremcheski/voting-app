package com.example.votingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "votes")
public class VoteEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "question")
    public String question;

    @ColumnInfo(name = "choice_one")
    public String choiceOne;
    @ColumnInfo(name = "choice_two")
    public String choiceTwo;
    @ColumnInfo(name = "choice_three")
    public String choiceThree;
    @ColumnInfo(name = "choice_four")
    public String choiceFour;
    @ColumnInfo(name = "choice_five")
    public String choiceFive;

    @ColumnInfo(name = "start_time")
    public String startTime;

    @ColumnInfo(name = "end_time")
    public String endTime;

    @Ignore
    public String getQuestion() {
        return question;
    }
}

