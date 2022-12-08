package com.example.votingapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.votingapp.databinding.QuestionCardViewBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VotesAdapter extends RecyclerView.Adapter<VotesAdapter.VotesViewHolder> {

    private List<String> votes;

    public static class VotesViewHolder extends RecyclerView.ViewHolder {

        private final QuestionCardViewBinding binding;

        public VotesViewHolder(QuestionCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public VotesAdapter(List<String> votes) {
        this.votes = votes;
    }

    @NonNull
    @Override
    public VotesAdapter.VotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionCardViewBinding binding = QuestionCardViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new VotesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VotesAdapter.VotesViewHolder holder, int position) {
        holder.binding.questionTextView.setText(votes.get(position));
        // TODO: set click listener on cardview
    }

    @Override
    public int getItemCount() {
        return votes == null ? 0 : votes.size();
    }


}
