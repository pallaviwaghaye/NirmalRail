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
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.OTPResponse;
import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewBack;
    private EditText editTextLoginMobileNo;
    private Button buttonLogin;

    private TextView textViewRegisterNow;

    private LinearLayout linearLayoutGotoRegister;
    private ProgressDialog progressDialogForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferenceManager.setApplicationContext(LoginActivity.this);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        editTextLoginMobileNo = (EditText) findViewById(R.id.editTextLoginMobileNo);
        textViewRegisterNow = (TextView) findViewById(R.id.textViewRegisterNow);
        textViewRegisterNow.setOnClickListener(this);

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
                    /*    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        startActivity(intent);
                        finish();*/

                        if (NetworkUtil.hasConnectivity(LoginActivity.this)) {
                            callOTPApi();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Mobile number must be valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Mobile number Can't be empty", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.textViewRegisterNow:
                Intent intent2 = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent2);
                finish();

                break;


        }
    }

    private void callOTPApi() {

        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<OTPResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).otpVerification(editTextLoginMobileNo.getText().toString());
        requestCallback.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    OTPResponse result = response.body();
                    if(result.getSuccess() != null) {
                        if (result.getSuccess().getStatus()) {

                            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                            intent.putExtra("MOBILE_NO", editTextLoginMobileNo.getText().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "OTP Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Mobile number is not registered. Please register first.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(LoginActivity.this, "OTP Error", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }


            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {

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


