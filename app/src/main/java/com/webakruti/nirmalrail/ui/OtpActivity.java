package com.webakruti.nirmalrail.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.UserResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    private EditText editTextOtp;
    private Button buttonOtpVerify;
    private String mobileNo;
    private ProgressDialog progressDialogForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mobileNo = getIntent().getStringExtra("MOBILE_NO");

        editTextOtp = (EditText) findViewById(R.id.editTextOtp);
        buttonOtpVerify = (Button) findViewById(R.id.buttonOtpVerify);
        buttonOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(OtpActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();*/

                if (NetworkUtil.hasConnectivity(OtpActivity.this)) {
                    callLoginAPI();
                } else {
                    Toast.makeText(OtpActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void callLoginAPI() {

        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<UserResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).login(mobileNo, mobileNo, editTextOtp.getText().toString());
        requestCallback.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    UserResponse result = response.body();
                    if (result.getSuccess().getStatus()) {

                        // Save UserResponse to SharedPref
                        SharedPreferenceManager.storeUserResponseObjectInSharedPreference(result);
                        Intent intent = new Intent(OtpActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(OtpActivity.this, "Error!! OTP Not Correct.", Toast.LENGTH_SHORT).show();
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
