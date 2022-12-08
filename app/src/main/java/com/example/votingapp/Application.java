package com.example.votingapp;

import com.example.votingapp.database.VotesDatabase;

public class Application extends android.app.Application {
    VotesDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = VotesDatabase.getDatabase(this);
    }
}
