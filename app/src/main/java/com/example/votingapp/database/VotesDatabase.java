package com.example.votingapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import kotlin.jvm.Volatile;

@Database(
    entities = {
        UserEntity.class,
        VoteEntity.class,
        UserVoteEntity.class
    },
    version = 8,
    exportSchema = false
)
public abstract class VotesDatabase extends RoomDatabase {

    private static VotesDatabase INSTANCE;

    public static synchronized VotesDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, VotesDatabase.class, "votes_db")
                .fallbackToDestructiveMigration()
                .build();
        }
        return INSTANCE;
    }

    public abstract UserDao usersDao();

    public abstract VoteDao votesDao();

    public abstract UserVoteDao userVoteDao();
}
