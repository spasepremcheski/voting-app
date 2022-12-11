package com.example.votingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.votingapp.database.UserVoteEntity;
import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.FragmentUserResultsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserResultsFragment extends Fragment {
    private FragmentUserResultsBinding binding;

    private VoteEntity vote;

    VotesDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("NAVIGATION", "UserResultsFragment");

        database = ((UserActivity) getActivity()).database;
        vote = ((UserActivity) getActivity()).vote;

        if(vote != null) {
            showResults();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showResults() {
        binding.questionTextView.setText(vote.question);
        database.userVoteDao().getUserVotes(vote.id).observe(getViewLifecycleOwner(), userVoteEntities -> {
            float one=0, two=0, three=0, four=0, five=0, numVotes;
            for(UserVoteEntity u: userVoteEntities) {
                if(u.choice.equals(vote.choiceOne)) one+=1;
                else if(u.choice.equals(vote.choiceTwo)) two+=1;
                else if(u.choice.equals(vote.choiceThree)) three+=1;
                else if(u.choice.equals(vote.choiceFour)) four+=1;
                else if(u.choice.equals(vote.choiceFive)) five+=1;
            }
            numVotes = one + two + three + four + five;
            binding.choiceOneTextView.setText(vote.choiceOne + ": " + (one==0 ? 0 : one*100/numVotes) + " %");
            binding.choiceTwoTextView.setText(vote.choiceTwo + ": " + (two==0 ? 0 : two*100/numVotes) + " %");
            binding.choiceThreeTextView.setText(vote.choiceThree + ": " + (three==0 ? 0 : three*100/numVotes) + " %");
            binding.choiceFourTextView.setText(vote.choiceFour + ": " + (four==0 ? 0 : four*100/numVotes) + " %");
            binding.choiceFiveTextView.setText(vote.choiceFive + ": " + (five==0 ? 0 : five*100/numVotes) + " %");
        });
    }
}
