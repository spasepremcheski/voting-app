package com.example.votingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityUserBinding;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    VotesDatabase database;

    int userId;
    VoteEntity vote;
    private List<VoteEntity> votes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = Integer.parseInt(getIntent().getExtras().get("USER_ID").toString());

        database = ((Application) getApplication()).database;

        //        DELETE TABLES
        //        Executors.newSingleThreadExecutor().execute(() -> {
        //            database.myDao().deleteUserVote();
        //            database.myDao().deleteVotes();
        //        });

        initSpinner();
    }

    private void initSpinner() {
        database.votesDao().getAllVotes().observe(this, voteEntities -> {
            votes = voteEntities;
            List<String> questions = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N && votes != null) {
                questions = votes.stream().map(VoteEntity::getQuestion).collect(Collectors.toList());
            }

            ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, questions);

            binding.voteSpinner.setAdapter(adapter);

            binding.voteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    vote = votes.get(i);
                    checkSelectedVote(vote.id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        });
    }

    private void checkSelectedVote(int voteId) {
        database.userVoteDao().getUserVote(userId, voteId).observe(this, userVoteEntity -> {
            if (userVoteEntity != null) {
                if (checkVoteEnded(vote.endTime)) {
                    Log.d("check vote", "Vote is ended");
                    // Show results
                    Navigation.findNavController(binding.navHostFragment).popBackStack();
                    Navigation.findNavController(binding.navHostFragment).navigate(R.id.userResultsFragment);
                } else {
                    // Show empty state
                    Log.d("check vote", "Already voted");
                    Navigation.findNavController(binding.navHostFragment).popBackStack();
                    Navigation.findNavController(binding.navHostFragment).navigate(R.id.userNoResultsFragment);
                }
            } else {
                if (checkVoteEnded(vote.endTime)) {
                    // Show empty state
                    Log.d("check vote", "Vote is ended, you haven't voted" + vote.endTime);
                    Navigation.findNavController(binding.navHostFragment).popBackStack();
                    Navigation.findNavController(binding.navHostFragment).navigate(R.id.userResultsFragment);
                } else {
                    // Show vote
                    Navigation.findNavController(binding.navHostFragment).popBackStack();
                    Navigation.findNavController(binding.navHostFragment).navigate(R.id.userVotesFragment);
                }
            }
        });
    }

    private boolean checkVoteEnded(String endTime) {
        boolean check;
        int[] curr = getCurrentTime();
        String[] t;
        t = endTime.split(":");
        int[] time = new int[2];
        time[0] = Integer.parseInt(t[0]);
        time[1] = Integer.parseInt(t[1]);
        Log.d("Vreme", curr[0] + "  " + curr[1] + " " + time[0] + " " + time[1]);
        if (curr[0] < time[0]) {
            check = false;
        } else if (curr[0] == time[0] && curr[1] < time[1]) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    private int[] getCurrentTime() {
        String[] current = String.valueOf(Calendar.getInstance().getTime()).substring(11, 16).split(":");
        int[] curr = new int[2];
        curr[0] = Integer.parseInt(current[0]);
        curr[1] = Integer.parseInt(current[1]);
        return curr;
    }
}
