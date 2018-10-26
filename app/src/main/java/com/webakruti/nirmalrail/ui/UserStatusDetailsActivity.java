package com.webakruti.nirmalrail.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.AdmintGetComplaintResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserStatusDetailsActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private TextView textViewComplaintStatus;
    private TextView textViewServiceName;
    private TextView textViewColonyName;
    private TextView textViewComplaintStations;
    private TextView textViewComplaintPlatform;
    private TextView textViewComplaintDate;
    private TextView textViewComment;
    private ImageView imageViewBefore;
    private ImageView imageViewAfter;
    private TextView textViewBefore;
    private TextView textViewAfter;
    private LinearLayout linearLayoutStationPlatform;
    private LinearLayout linearLayoutColonyName;
    private LinearLayout linearLayoutServiceName;


    private String statusInfo;
    private ProgressDialog progressDialogForAPI;
    private AdmintGetComplaintResponse.Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status_details);

        SharedPreferenceManager.setApplicationContext(UserStatusDetailsActivity.this);

        initViews();
        int id = getIntent().getIntExtra("id", -1);
        statusInfo = getIntent().getStringExtra("STATUS_INFO");

        if (statusInfo.equalsIgnoreCase("complete")) {
            textViewAfter.setVisibility(View.VISIBLE);
            imageViewAfter.setVisibility(View.VISIBLE);
            textViewBefore.setVisibility(View.VISIBLE);
            imageViewBefore.setVisibility(View.VISIBLE);


        } else {
            textViewAfter.setVisibility(View.GONE);
            imageViewAfter.setVisibility(View.GONE);
            textViewBefore.setVisibility(View.VISIBLE);
            imageViewBefore.setVisibility(View.VISIBLE);


        }
        if (NetworkUtil.hasConnectivity(UserStatusDetailsActivity.this)) {
            callGetComplaintDetailsAPI(id);
        } else {
            Toast.makeText(UserStatusDetailsActivity.this, R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {

        textViewServiceName = (TextView) findViewById(R.id.textViewServiceName);
        textViewColonyName = (TextView) findViewById(R.id.textViewColonyName);
        textViewComplaintDate = (TextView) findViewById(R.id.textViewComplaintDate);
        textViewComplaintStations = (TextView) findViewById(R.id.textViewComplaintStations);
        textViewComplaintPlatform = (TextView) findViewById(R.id.textViewComplaintPlatform);
        textViewComment = (TextView) findViewById(R.id.textViewComment);
        textViewComplaintStatus = (TextView) findViewById(R.id.textViewComplaintStatus);
        linearLayoutStationPlatform = (LinearLayout)findViewById(R.id.linearLayoutStationPlatform);
        linearLayoutColonyName = (LinearLayout)findViewById(R.id.linearLayoutColonyName);
        linearLayoutServiceName = (LinearLayout)findViewById(R.id.linearLayoutServiceName);

        textViewBefore = (TextView) findViewById(R.id.textViewBefore);
        textViewAfter = (TextView) findViewById(R.id.textViewAfter);
        imageViewBefore = (ImageView) findViewById(R.id.imageViewBefore);
        imageViewAfter = (ImageView) findViewById(R.id.imageViewAfter);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void callGetComplaintDetailsAPI(int id) {


        progressDialogForAPI = new ProgressDialog(this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();
        SharedPreferenceManager.setApplicationContext(UserStatusDetailsActivity.this);
        String token = SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        String headers = "Bearer " + token;

        Call<AdmintGetComplaintResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getAdminComplaintById(headers, id + "");
            requestCallback.enqueue(new Callback<AdmintGetComplaintResponse>() {
            @Override
            public void onResponse(Call<AdmintGetComplaintResponse> call, Response<AdmintGetComplaintResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    AdmintGetComplaintResponse result = response.body();
                    if (result.getSuccess() != null) {
                        if (result.getSuccess().getStatus()) {
                            complaint = result.getSuccess().getComplaint();

                            textViewServiceName.setText(complaint.getServicename());
                            textViewComplaintDate.setText(complaint.getComplaintDate());
                            textViewComplaintStatus.setText(complaint.getStatus());

                            if (complaint.getComment() != null) {
                                textViewComment.setText(complaint.getDescription());
                            } else {
                                textViewComment.setText("N/A");

                            }
                            if(result.getSuccess().getComplaint().getColonyname() == null) {

                                linearLayoutStationPlatform.setVisibility(View.VISIBLE);
                                linearLayoutServiceName.setVisibility(View.VISIBLE);
                                linearLayoutColonyName.setVisibility(View.GONE);
                                textViewComplaintStations.setText(complaint.getStationname());
                                textViewComplaintPlatform.setText(complaint.getAtPlatform());

                            }
                            else {
                                linearLayoutStationPlatform.setVisibility(View.GONE);
                                linearLayoutServiceName.setVisibility(View.GONE);
                                linearLayoutColonyName.setVisibility(View.VISIBLE);
                                textViewColonyName.setText(complaint.getColonyname().toString());
                            }

                            if (complaint.getStatus().equalsIgnoreCase("invalid")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.red));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else if (complaint.getStatus().equalsIgnoreCase("inprocess")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.yellow));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else if (complaint.getStatus().equalsIgnoreCase("complete")) {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.green));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            } else {
                                textViewComplaintStatus.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                                textViewComplaintStatus.setText(complaint.getStatus());
                            }

                            Picasso.with(UserStatusDetailsActivity.this).load(complaint.getBeforeImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewBefore);
                            imageViewBefore.getLayoutParams().height = 600; // OR
                            imageViewBefore.getLayoutParams().width = 1000;
                            if (complaint.getStatus().equalsIgnoreCase("complete")) {
                                imageViewBefore.getLayoutParams().height = 350; // OR
                                imageViewBefore.getLayoutParams().width = 350;
                                Picasso.with(UserStatusDetailsActivity.this).load(complaint.getBeforeImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewBefore);
                                Picasso.with(UserStatusDetailsActivity.this).load(complaint.getAfterImgUrl()).placeholder(R.drawable.image_back).resize(300, 300).into(imageViewAfter);
                            }

                        } else {
                            Toast.makeText(UserStatusDetailsActivity.this, "Server error !!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserStatusDetailsActivity.this, "Error in response", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Response code is 401
                    Toast.makeText(UserStatusDetailsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }


        @Override
        public void onFailure(Call<AdmintGetComplaintResponse> call, Throwable t) {

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
