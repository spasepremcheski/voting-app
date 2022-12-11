package com.example.votingapp;

import android.os.Bundle;

import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityAdminResultsBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminResultsActivity extends AppCompatActivity {
    private ActivityAdminResultsBinding binding;

    VotesDatabase database;
    int voteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;
        voteId = getIntent().getIntExtra("VOTE_ID", 0);

    }
}
