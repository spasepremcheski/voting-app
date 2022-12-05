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
    version = 1
)
public abstract class VotesDatabase extends RoomDatabase {

    @Volatile
    private VotesDatabase INSTANCE = null;

    public VotesDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            synchronized (this) {
                INSTANCE = Room.databaseBuilder(context, VotesDatabase.class, "votes_db")
                    .fallbackToDestructiveMigration()
                    .build();
            }
        }
        return INSTANCE;
    }

    abstract UserDao usersDao();

    abstract VoteDao votesDao();

    abstract UserVoteDao userVoteDao();
}
