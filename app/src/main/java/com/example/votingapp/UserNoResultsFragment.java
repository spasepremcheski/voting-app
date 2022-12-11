package com.example.votingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.votingapp.databinding.FragmentUserNoresultsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserNoResultsFragment extends Fragment {
    FragmentUserNoresultsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserNoresultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
