package com.example.votingapp;

import android.os.Bundle;

import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityUserBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    VotesDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;
    }
}
