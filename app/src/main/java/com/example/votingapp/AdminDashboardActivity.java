package com.example.votingapp;

import android.os.Bundle;
import android.view.View;

import com.example.votingapp.databinding.ActivityAdminDashboardBinding;
import com.example.votingapp.databinding.DialogAddVoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    private VotesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addNewVote();
        initRecyclerView();
        refreshVotes();
    }

    private void initRecyclerView() {
        adapter = new VotesAdapter(Arrays.asList("prashanje 1 ", "prashanje 2"));
        binding.votesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.votesRecyclerView.setAdapter(adapter);
        // TODO: fetch votes from database and add them to the recycler view
    }

    private void refreshVotes() {
        // TODO: refetch votes from database and add them to the recycler view
    }

    private void addNewVote() {
        binding.addNewVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewVoteDialog();
            }
        });
    }

    private void addNewVoteDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        DialogAddVoteBinding bottomSheetBinding = DialogAddVoteBinding.inflate((getLayoutInflater()));
        dialog.setContentView(bottomSheetBinding.getRoot());

        bottomSheetBinding.startVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: start the vote (save it to database, send notification)

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
