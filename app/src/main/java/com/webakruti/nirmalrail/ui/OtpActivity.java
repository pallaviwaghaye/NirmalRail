package com.webakruti.nirmalrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.webakruti.nirmalrail.R;

public class OtpActivity extends AppCompatActivity {
    private EditText editTextOtp;
    private Button buttonOtpVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        editTextOtp = (EditText)findViewById(R.id.editTextOtp);
        buttonOtpVerify = (Button)findViewById(R.id.buttonOtpVerify);
        buttonOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this,HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
