package com.webakruti.nirmalrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webakruti.nirmalrail.R;

public class SuccessActivity extends AppCompatActivity {
    private Button buttonCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        buttonCheckStatus = (Button)findViewById(R.id.buttonCheckStatus);
        buttonCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, MyRequestsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
