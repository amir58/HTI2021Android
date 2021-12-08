package com.amirmohammed.hti2021androidone.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amirmohammed.hti2021androidone.R;
import com.amirmohammed.hti2021androidone.RegisterActivity;
import com.amirmohammed.hti2021androidone.databinding.ActivityLoginBinding;
import com.amirmohammed.hti2021androidone.maps.MapHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Design Patterns => Singelton, Builder, Observer, Factory
// Architecture pattern => MVC, MVP, MVVM, MVI
// MVVM => Model, View, ViewModel
// View => Activity, Fragments ( Blank, Dialog, BottomSheet, etc...)

// VIEW
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        observers();
    }

    private void observers() {
        loginViewModel.isLoginSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String uid) {
                Log.i(TAG, "onComplete: " + uid);
                Intent intent = new Intent(LoginActivity.this, MapHomeActivity.class);
                startActivity(intent);
            }
        });

        loginViewModel.isLoginFailure().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Log.i(TAG, "onComplete: " + errorMessage);
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(View view) {
        String email = binding.etEmail.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "email required", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = binding.etPassword.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "password required", Toast.LENGTH_SHORT).show();
            return;
        }

        loginViewModel.login(email, password);
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}