package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.votingapp.database.UserEntity;
import com.example.votingapp.database.VotesDatabase;
import com.example.votingapp.databinding.ActivityLoginBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private final String adminUser = "admin";
    private final String adminPass = "admin";

    private ActivityLoginBinding binding;

    VotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        database = ((Application) getApplication()).database;

        binding.registerNavigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterNavigateButtonClick();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClick();
            }
        });
    }

    private void onLoginButtonClick() {
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            if(username.equals(adminUser) && password.equals(adminPass)) {
                Intent intent = new Intent(this, AdminDashboardActivity.class);
                startActivity(intent);
            } else {
                // TODO: check user if registered in database then navigate to user dashboard
                Executors.newSingleThreadExecutor().execute(() -> {
                    UserEntity user = database.usersDao().getUser(username, password);
                    if(user != null){
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                        intent.putExtra("USER_ID", user.id);
                        startActivity(intent);
                    }
                });
            }

        } else {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRegisterNavigateButtonClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}