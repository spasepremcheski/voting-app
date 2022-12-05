package com.example.votingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_vote")
public class UserVoteEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "user_id")
    int userId;

    @ColumnInfo(name = "vote_id")
    int voteId;

    @ColumnInfo(name = "choice")
    String choice;

    @ColumnInfo(name = "time_voted")
    String timeVoted;

    @ColumnInfo(name = "latitude")
    double latitude;

    @ColumnInfo(name = "longitude")
    double longitude;
}
