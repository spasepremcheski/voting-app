package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.votingapp.database.UserVoteEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.FragmentAdminResultsMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class AdminResultsMapsFragment extends Fragment {

    private FragmentAdminResultsMapsBinding binding;

    private List<LatLng> locations = new ArrayList<>();

    private int voteId;

    VotesDatabase database;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            showMap(googleMap);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminResultsMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
            (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        database = ((AdminResultsActivity) getActivity()).database;
        voteId = ((AdminResultsActivity) getActivity()).voteId;
    }

    private void showMap(GoogleMap googleMap) {
        database.userVoteDao().getUserVotes(voteId).observe(getViewLifecycleOwner(), userVoteEntities -> {
            for(UserVoteEntity u: userVoteEntities) {
                locations.add(new LatLng(u.latitude, u.longitude));
            }
            for(LatLng l: locations) {
                googleMap.addMarker(new MarkerOptions().position(l));
            }
        });
    }
}