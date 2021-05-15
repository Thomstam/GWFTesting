package com.example.gwftesting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gwftesting.measurements.ApiClient;
import com.example.gwftesting.measurements.ClientService;
import com.example.gwftesting.measurements.TokenSaver;
import com.example.gwftesting.userUtilities.User;
import com.example.gwftesting.userUtilities.UserLoginResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setView();

        setLoginButton();
    }

    private void setView() {
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
    }

    private void setLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                Toast.makeText(this, "Username / Password Required", Toast.LENGTH_SHORT).show();
            } else {
                login();
            }
        });
    }

    private void login() {
        String email = username.getText().toString();
        String passwordToLogin = password.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordToLogin);
        Call<UserLoginResponse> loginResponseCall = ApiClient.createService(ClientService.class).login(user);
        loginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserLoginResponse> call, @NotNull Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    TokenSaver.setSharedPrefAccess(LoginActivity.this, response.body().getAccess());
                    TokenSaver.setSharedPrefRefresh(LoginActivity.this, response.body().getRefresh());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLoginResponse> call, @NotNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Wrong Credentials" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}