package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.votingapp.database.UserEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityRegisterBinding;

import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    VotesDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClick();
            }
        });
    }

    private void onRegisterButtonClick() {
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();
        String passwordConfirmation = binding.passwordConfirmationEditText.getText().toString();

        if (!username.isEmpty() && !password.isEmpty() && password.equals(passwordConfirmation)) {
            Runnable runnable = () -> {
                UserEntity user = new UserEntity();
                user.id = 0;
                user.username = username;
                user.password = password;
                database.usersDao().addUser(user);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            };
            Executors.newSingleThreadExecutor().execute(runnable);
        } else {
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
