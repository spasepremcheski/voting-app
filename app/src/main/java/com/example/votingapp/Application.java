package com.example.votingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.votingapp.database.VotesDatabase;

public class Application extends android.app.Application {
    VotesDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = VotesDatabase.getDatabase(this);

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.vote_channel);
            String description = getString(R.string.vote_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
