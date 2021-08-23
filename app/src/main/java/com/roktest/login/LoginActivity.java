package com.roktest.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roktest.R;
import com.roktest.account.AccountActivity;
import com.roktest.retrofit.ApiClient;
import com.roktest.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameText,passwordText;
    private TextView signUp;
    private Button loginBtn;
    ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameText = findViewById(R.id.user_name);
        passwordText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        signUp = findViewById(R.id.registration_button);

        api = ApiClient.getClient().create(ApiInterface.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginInfo details = new LoginInfo();
                details.setUsername(userNameText.getText().toString());
                details.setPassword(passwordText.getText().toString());

                authenticateUser(details);


            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }

    private void authenticateUser( LoginInfo loginInfo) {

        try {
            Call<LoginReturn> call = api.appLogin(loginInfo);
            call.enqueue(new Callback<LoginReturn>() {
                @Override
                public void onResponse(Call<LoginReturn> call, Response<LoginReturn> response) {
                    if (response.isSuccessful()) {
                        LoginReturn loginReturn = response.body();
                        if (loginReturn == null) {
                            Toast.makeText(LoginActivity.this, "Please Input Valid User Name and Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginReturn> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}