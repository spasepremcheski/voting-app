package com.example.votingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.votingapp.database.UserEntity;
import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityAdminDashboardBinding;
import com.example.votingapp.databinding.DialogAddVoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    private VotesAdapter adapter;
    private List<VoteEntity> votes;

    VotesDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;

        addNewVote();
        initRecyclerView();
//        refreshVotes();
    }

    private void initRecyclerView() {
        //        List<String> questions = null;
        //        Executors.newSingleThreadExecutor().execute(() -> {
        //            List<VoteEntity> votes = database.votesDao().getAllVotes();
        //            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        //                List<String> q = votes.stream().map(VoteEntity::getQuestion).collect(Collectors.toList());
        //                questions.addAll(q);
        //            }
        //        });

        // fetch votes from database and add them to the recycler view
        database.votesDao().getAllVotes().observe(this, voteEntities -> {
            votes = voteEntities;
            List<String> questions = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N && votes != null) {
                questions = votes.stream().map(VoteEntity::getQuestion).collect(Collectors.toList());
            }

            adapter = new VotesAdapter(questions);
            binding.votesRecyclerView.setLayoutManager(new LinearLayoutManager(AdminDashboardActivity.this));
            binding.votesRecyclerView.setAdapter(adapter);
        });

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

        bottomSheetBinding.startVoteButton.setOnClickListener(view -> {
            // TODO: start the vote (check empty fields, save it to database, send notification)
            String question = bottomSheetBinding.voteQuestionEditText.getText().toString();
            String choiceOne = bottomSheetBinding.choiceOneEditText.getText().toString();
            String choiceTwo = bottomSheetBinding.choiceTwoEditText.getText().toString();
            String choiceThree = bottomSheetBinding.choiceThreeEditText.getText().toString();
            String choiceFour =  bottomSheetBinding.choiceFourEditText.getText().toString();
            String choiceFive = bottomSheetBinding.choiceFiveEditText.getText().toString();
            String endTime = bottomSheetBinding.endTimeEditText.getText().toString();

            Log.d("Time", getCurrentTime());

            if(!question.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> database.votesDao().addVote(question, choiceOne, choiceTwo,
                    choiceThree, choiceFour, choiceFive, getCurrentTime(), endTime));
//                    TODO: send notification
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private String getCurrentTime() {
        return String.valueOf(Calendar.getInstance().getTime()).substring(11,16);
    }
}
