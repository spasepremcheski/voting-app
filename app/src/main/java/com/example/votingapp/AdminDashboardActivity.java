package com.example.votingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityAdminDashboardBinding;
import com.example.votingapp.databinding.DialogAddVoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    private VotesAdapter adapter;

    VotesDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;

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
                // TODO: start the vote (check empty fields, save it to database, send notification)
                String question = bottomSheetBinding.voteQuestionEditText.getText().toString();
                String choiceOne = bottomSheetBinding.choiceOneEditText.getText().toString();
                String choiceTwo = bottomSheetBinding.choiceTwoEditText.getText().toString();
                String choiceThree = bottomSheetBinding.choiceThreeEditText.getText().toString();
                String choiceFour =  bottomSheetBinding.choiceFourEditText.getText().toString();
                String choiceFive = bottomSheetBinding.choiceFiveEditText.getText().toString();
                String endTime = bottomSheetBinding.endTimeEditText.getText().toString();

                Log.d("Time", String.valueOf(Calendar.getInstance().getTime()).substring(11,19));

                if(!question.isEmpty()) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
//                            database.votesDao().addVote(question, choiceOne, choiceTwo, choiceThree,choiceFour,choiceFive, );
                        }
                    });
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
