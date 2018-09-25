package com.webakruti.nirmalrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webakruti.nirmalrail.R;

public class LandingPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(this);

        buttonRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonLogin:
                Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonRegister:
                Intent intent1 = new Intent(LandingPageActivity.this, RegistrationActivity.class);
                startActivity(intent1);
                finish();
                break;

        }
    }

}
