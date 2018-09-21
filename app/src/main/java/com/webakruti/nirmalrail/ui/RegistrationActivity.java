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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName;
    private EditText editTextMobileNumber;
    private Button buttonRegister;
    private ImageView imageViewBack;
    private LinearLayout linearLayoutGotoLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextMobileNumber = (EditText)findViewById(R.id.editTextMobileNumber);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        linearLayoutGotoLogin = (LinearLayout)findViewById(R.id.linearLayoutGotoLogin);
        linearLayoutGotoLogin.setOnClickListener(this);

        imageViewBack=(ImageView)findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonRegister:
                if (editTextName.getText().toString().length() > 0) {
                    if (editTextMobileNumber.getText().toString().length() > 0) {
                        if (editTextMobileNumber.getText().toString().length() == 10) {
                            Intent intent = new Intent(RegistrationActivity.this, OtpActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Mobile number must be valid", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Mobile number Can't be empty", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegistrationActivity.this, "Name Can't be empty", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.linearLayoutGotoLogin:
                Intent intent2 = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();

                break;


        }
    }

}
