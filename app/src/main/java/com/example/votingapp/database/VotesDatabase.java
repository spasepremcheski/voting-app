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
    version = 2
)
public abstract class VotesDatabase extends RoomDatabase {

    @Volatile
    private static VotesDatabase INSTANCE;

    public static VotesDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            synchronized (VotesDatabase.class) {
                VotesDatabase database = Room.databaseBuilder(context, VotesDatabase.class, "votes_db")
                    .fallbackToDestructiveMigration()
                    .build();
                INSTANCE = database;
            }
        }
        return INSTANCE;
    }

    public abstract UserDao usersDao();

    public abstract VoteDao votesDao();

    public abstract UserVoteDao userVoteDao();
}
