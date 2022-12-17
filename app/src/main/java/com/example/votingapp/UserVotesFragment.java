package com.example.votingapp;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.votingapp.database.VoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.FragmentUserVotesBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.concurrent.Executors;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class UserVotesFragment extends Fragment {
    private FragmentUserVotesBinding binding;

    private FusedLocationProviderClient fusedLocationClient;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

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

//        Log.d("NAVIGATION", "UserVotesFragment");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        database = ((UserActivity) getActivity()).database;
        userId = ((UserActivity) getActivity()).userId;

        vote = ((UserActivity) getActivity()).vote;

        if(vote != null) {
            setUpView();
            initSubmitButton();
            locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            Navigation.findNavController(binding.getRoot()).popBackStack();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.userNoResultsFragment);
        }
    }

    ActivityResultLauncher<String[]> locationPermissionRequest =
        registerForActivityResult(new ActivityResultContracts
                .RequestMultiplePermissions(), result -> {
            Boolean fineLocationGranted = false;
            Boolean coarseLocationGranted = false;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                fineLocationGranted = result.getOrDefault(
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                coarseLocationGranted = result.getOrDefault(
                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
            }

            if (fineLocationGranted) {
                getLocation();
            } else if (coarseLocationGranted) {
                getLocation();
            }
            }
        );

    private void getLocation() {
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(requireActivity(), location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            });
    }

    private void initSubmitButton() {
        final String[] choice = {""};
        binding.voteSubmitButton.setOnClickListener(view -> {
            long id = binding.votesRadioGroup.getCheckedRadioButtonId();

            if(id == R.id.voteOneRadioButton) {
                choice[0] = vote.choiceOne;
            } else if(id == R.id.voteTwoRadioButton) {
                choice[0] = vote.choiceTwo;
            } else if(id == R.id.voteThreeRadioButton) {
                choice[0] = vote.choiceThree;
            } else if(id == R.id.voteFourRadioButton) {
                choice[0] = vote.choiceFour;
            } else if(id == R.id.voteFiveRadioButton) {
                choice[0] = vote.choiceFive;
            } else {
                Toast.makeText(requireContext(), "Please vote", Toast.LENGTH_SHORT).show();
            }

            if(!choice[0].equals("")) {
                Executors.newSingleThreadExecutor().execute(() -> {
//                    Log.d("LOCATION", "Location: " + latitude + " " + longitude);
                        database.userVoteDao().addUserVote(userId, vote.id, choice[0], getCurrentTime(), latitude, longitude);
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
