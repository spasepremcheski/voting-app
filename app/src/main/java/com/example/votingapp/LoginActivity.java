package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.votingapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private final String adminUser = "admin";
    private final String adminPass = "admin";

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

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