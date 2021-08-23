package com.roktest.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roktest.R;
import com.roktest.login.LoginActivity;
import com.roktest.login.LoginInfo;
import com.roktest.login.LoginReturn;
import com.roktest.retrofit.ApiClient;
import com.roktest.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    private EditText userNameText, emailText,passwordText;
    private TextView loginText;
    private Button sign_up;
    ApiInterface api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        userNameText = findViewById(R.id.user_name);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);


        sign_up = findViewById(R.id.sign_up);
        loginText = findViewById(R.id.login);


        api = ApiClient.getClient().create(ApiInterface.class);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrationInfo details = new RegistrationInfo();
                details.setUsername(userNameText.getText().toString());
                details.setEmail(emailText.getText().toString());
                details.setPassword(passwordText.getText().toString());

                authenticateUser(details);


            }
        });


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }

    private void authenticateUser(RegistrationInfo registrationInfo) {

        try {

            Call<RegistrationReturn> call = api.registration(registrationInfo);
            call.enqueue(new Callback<RegistrationReturn>() {
                @Override
                public void onResponse(Call<RegistrationReturn> call, Response<RegistrationReturn> response) {
                    if (response.isSuccessful()) {
                        RegistrationReturn loginReturn = response.body();
                        if (loginReturn == null) {
                            Toast.makeText(AccountActivity.this, "Please Input Valid User Name and Password", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AccountActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegistrationReturn> call, Throwable t) {
                    Toast.makeText(AccountActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}