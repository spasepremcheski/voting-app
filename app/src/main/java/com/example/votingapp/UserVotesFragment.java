package com.example.votingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.FragmentUserVotesBinding;

import java.util.Calendar;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class UserVotesFragment extends Fragment {
    private FragmentUserVotesBinding binding;

    private VoteEntity vote;
    private int userId;

    VotesDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserVotesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("NAVIGATION", "UserVotesFragment");

        database = ((UserActivity) getActivity()).database;
        userId = ((UserActivity) getActivity()).userId;

        vote = ((UserActivity) getActivity()).vote;

        if(vote != null) {
            setUpView();
            initSubmitButton();
        } else {
            Navigation.findNavController(binding.getRoot()).popBackStack();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.userNoResultsFragment);
        }
    }

    private void initSubmitButton() {
        final String[] choice = {""};
        binding.voteSubmitButton.setOnClickListener(view -> {
            int id = binding.votesRadioGroup.getCheckedRadioButtonId();
            switch (id) {
                case R.id.voteOneRadioButton: choice[0] = vote.choiceOne;
                case R.id.voteTwoRadioButton: choice[0] = vote.choiceTwo;
                case R.id.voteThreeRadioButton: choice[0] = vote.choiceThree;
                case R.id.voteFourRadioButton: choice[0] = vote.choiceFour;
                case R.id.voteFiveRadioButton: choice[0] = vote.choiceFive;
                default:
                    Toast.makeText(requireContext(), "Please vote", Toast.LENGTH_SHORT).show();
            }

            if(!choice[0].equals("")) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    database.userVoteDao().addUserVote(userId, vote.id, choice[0], getCurrentTime(), 42, 43);
                });
            }
        });
    }

    private void setUpView() {
        binding.voteEndTimeTextView.setText(vote.endTime);
        binding.questionTextView.setText(vote.question);
        binding.voteOneRadioButton.setText(vote.choiceOne);
        binding.voteTwoRadioButton.setText(vote.choiceTwo);
        binding.voteThreeRadioButton.setText(vote.choiceThree);
        binding.voteFourRadioButton.setText(vote.choiceFour);
        binding.voteFiveRadioButton.setText(vote.choiceFive);
    }

    private String getCurrentTime() {
        return String.valueOf(Calendar.getInstance().getTime()).substring(11,16);
    }
}
