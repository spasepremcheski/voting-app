package com.example.votingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.votingapp.databinding.QuestionCardViewBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

interface OnAdapterItemClickListener {
    void onAdapterItemClickListener(int position);
}

public class VotesAdapter extends RecyclerView.Adapter<VotesAdapter.VotesViewHolder> {

    private OnAdapterItemClickListener adapterItemClickListener = null;

    private List<String> votes;

    public class VotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final QuestionCardViewBinding binding;

        public VotesViewHolder(QuestionCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            adapterItemClickListener.onAdapterItemClickListener(getAdapterPosition());
        }
    }

    public VotesAdapter(List<String> votes, OnAdapterItemClickListener listener) {
        this.votes = votes;
        this.adapterItemClickListener = listener;
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
