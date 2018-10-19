package com.webakruti.nirmalrail.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.AdminLoginSuccess;
import com.webakruti.nirmalrail.model.OTPResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonAdminLogin;

    private LinearLayout linearLayoutGotoUserLogin;
    private ProgressDialog progressDialogForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        SharedPreferenceManager.setApplicationContext(AdminLoginActivity.this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        linearLayoutGotoUserLogin = (LinearLayout) findViewById(R.id.linearLayoutGotoUserLogin);
        linearLayoutGotoUserLogin.setOnClickListener(this);

        buttonAdminLogin = (Button) findViewById(R.id.buttonAdminLogin);
        buttonAdminLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonAdminLogin:
                if (editTextEmail.getText().toString().length() > 0) {
                    if (isValidEmailAddress(editTextEmail.getText().toString().trim())) {
                        if (editTextPassword.getText().toString().length() >= 6) {
                            if (NetworkUtil.hasConnectivity(AdminLoginActivity.this)) {
                                callAdminLoginAPI();
                            } else {
                                Toast.makeText(AdminLoginActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AdminLoginActivity.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AdminLoginActivity.this, "Email Id must be valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Email Id Can't be empty", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.linearLayoutGotoUserLogin:
                Intent intent2 = new Intent(AdminLoginActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();

                break;


        }
    }

    private void callAdminLoginAPI() {


        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();


        Call<AdminLoginSuccess> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).adminLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        requestCallback.enqueue(new Callback<AdminLoginSuccess>() {
            @Override
            public void onResponse(Call<AdminLoginSuccess> call, Response<AdminLoginSuccess> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    AdminLoginSuccess result = response.body();
                    if (result.getSuccess() != null) {
                        if (result.getSuccess().getStatus()) {

                            SharedPreferenceManager.storeAdminResponseObjectInSharedPreference(result);
                            Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AdminLoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AdminLoginActivity.this, "Admin email id or password is not correct", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(AdminLoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<AdminLoginSuccess> call, Throwable t) {

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

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}
