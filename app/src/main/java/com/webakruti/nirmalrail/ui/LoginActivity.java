package com.webakruti.nirmalrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewBack;
    private EditText editTextLoginMobileNo;
    private Button buttonLogin;
    private LinearLayout linearLayoutGotoRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewBack=(ImageView)findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextLoginMobileNo = (EditText)findViewById(R.id.editTextLoginMobileNo);
        linearLayoutGotoRegister = (LinearLayout)findViewById(R.id.linearLayoutGotoRegister);
        linearLayoutGotoRegister.setOnClickListener(this);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
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

}


