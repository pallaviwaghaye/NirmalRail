package com.webakruti.nirmalrail.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewBack;
    private EditText editTextLoginMobileNo;
    private Button buttonLogin;
    private LinearLayout linearLayoutGotoRegister;
    private ProgressDialog progressDialogForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextLoginMobileNo = (EditText) findViewById(R.id.editTextLoginMobileNo);
        linearLayoutGotoRegister = (LinearLayout) findViewById(R.id.linearLayoutGotoRegister);
        linearLayoutGotoRegister.setOnClickListener(this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonLogin:
                if (editTextLoginMobileNo.getText().toString().length() > 0) {
                    if (editTextLoginMobileNo.getText().toString().length() == 10) {
                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        startActivity(intent);
                        finish();

                     /*   if (NetworkUtil.hasConnectivity(LoginActivity.this)) {
                            callLoginAPI();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                        }*/
                    } else {
                        Toast.makeText(LoginActivity.this, "Mobile number must be valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Mobile number Can't be empty", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.linearLayoutGotoRegister:
                Intent intent2 = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent2);
                finish();

                break;


        }
    }

    private void callLoginAPI() {

        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<UserResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).login(editTextLoginMobileNo.getText().toString(), editTextLoginMobileNo.getText().toString(), "123456");
        requestCallback.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    UserResponse result = response.body();
                    if (result.getSuccess().getStatus()) {

                        // Save UserResponse to SharedPref

                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(LoginActivity.this, "Unauthorized User", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                if (t != null) {

                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });


    }

}


