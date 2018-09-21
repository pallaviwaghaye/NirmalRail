package com.webakruti.nirmalrail.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.webakruti.nirmalrail.R;

public class MyRequestsActivity extends AppCompatActivity {
    private ImageView imageViewRequestImage;
    private TextView textViewRequestStations;
    private TextView textViewRequestPlatform;
    private TextView textViewRequestStatus;
    private TextView textViewRequestDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);


    }
}
