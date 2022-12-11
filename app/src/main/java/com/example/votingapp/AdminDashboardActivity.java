package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityAdminDashboardBinding;
import com.example.votingapp.databinding.DialogAddVoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AdminDashboardActivity extends AppCompatActivity implements OnAdapterItemClickListener {

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
    }

    private void initRecyclerView() {
        // fetch votes from database and add them to the recycler view
        database.votesDao().getAllVotes().observe(this, voteEntities -> {
            votes = voteEntities;
            List<String> questions = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N && votes != null) {
                questions = votes.stream().map(VoteEntity::getQuestion).collect(Collectors.toList());
            }

            adapter = new VotesAdapter(questions, this);
            binding.votesRecyclerView.setLayoutManager(new LinearLayoutManager(AdminDashboardActivity.this));
            binding.votesRecyclerView.setAdapter(adapter);
        });

    }

    private void addNewVote() {
        binding.addNewVoteButton.setOnClickListener(view -> addNewVoteDialog());
    }

    private void addNewVoteDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        DialogAddVoteBinding bottomSheetBinding = DialogAddVoteBinding.inflate((getLayoutInflater()));
        dialog.setContentView(bottomSheetBinding.getRoot());

        bottomSheetBinding.startVoteButton.setOnClickListener(view -> {
            // start the vote (check empty fields, save it to database, send notification)
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

    @Override
    public void onAdapterItemClickListener(int position) {
        Log.d("Item clicked", votes.get(position).question + votes.get(position).id);
        Intent intent = new Intent(this, AdminResultsActivity.class);
        intent.putExtra("VOTE_ID", votes.get(position).id);
        startActivity(intent);
    }
}
