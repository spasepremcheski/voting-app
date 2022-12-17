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
import com.example.votingapp.databinding.FragmentAdminResultsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminResultsFragment extends Fragment {
    private FragmentAdminResultsBinding binding;

    private VoteEntity vote;
    private int voteId;

    VotesDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Log.d("NAVIGATION", "AdminResultsFragment");

        database = ((AdminResultsActivity) getActivity()).database;
        voteId = ((AdminResultsActivity) getActivity()).voteId;

        database.votesDao().getVote(voteId).observe(getViewLifecycleOwner(), voteEntity -> {
            vote = voteEntity;
//            Log.d("Vote: ", String.valueOf(voteId));
            showResults();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showResults() {
        binding.questionTextView.setText(vote.question);
        database.userVoteDao().getUserVotes(voteId).observe(getViewLifecycleOwner(), userVoteEntities -> {
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

        initMapsButton();
    }

    private void initMapsButton() {
        binding.mapsButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.toAdminMapsFragment);
        });
    }
}