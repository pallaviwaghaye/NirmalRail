package com.webakruti.nirmalrail.ui;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.MyRequestStatusAdapter;
import com.webakruti.nirmalrail.adapter.RailwayCategoryAdapter;
import com.webakruti.nirmalrail.model.MyRequestStatusResponse;
import com.webakruti.nirmalrail.model.RailwayCategoryResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private LinearLayoutManager mLayoutManger;
    private ImageView imageViewBack;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialogForAPI;
    private MyRequestStatusAdapter myRequestStatusAdapter;
    private TextView textViewNoData;

//    List<MyRequestStatusResponse> list = new ArrayList<MyRequestStatusResponse>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        textViewNoData = (TextView) findViewById(R.id.textViewNoData);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(MyRequestsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        //recyclerView.setAdapter(new MyRequestStatusAdapter(MyRequestsActivity.this, list));

        //initSwipeLayout();
        callGetRequestAPI();

    }


   /* private void initSwipeLayout() {

// Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("", "swipe to refresh");
                if (NetworkUtil.hasConnectivity(MyRequestsActivity.this)) {
                    // call API

                    callGetRequestAPI();

                } else {
                    swipeContainer.setRefreshing(false);
                }
            }
        });
// Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.blue,
                R.color.red,
                R.color.blue,
                R.color.red);

    }*/

   /* private void callGetRequestAPI() {

         // just given timer to go off refreshing icon after 5 seconds., later we need to remove this and on api response success, we need to do set refreshing to false.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeContainer.setRefreshing(false);

                        }
                    }, 5000);

    }*/

    private void callGetRequestAPI() {

        progressDialogForAPI = new ProgressDialog(MyRequestsActivity.this);
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        SharedPreferenceManager.setApplicationContext(MyRequestsActivity.this);
        String token = SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        String API = "http://nirmalrail.webakruti.in/api/";
        String headers = "Bearer " + token;
        Call<MyRequestStatusResponse> requestCallback = RestClient.getApiService(API).getMyRequestStatus(headers);
        requestCallback.enqueue(new Callback<MyRequestStatusResponse>() {
            @Override
            public void onResponse(Call<MyRequestStatusResponse> call, Response<MyRequestStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    MyRequestStatusResponse details = response.body();
                    //  Toast.makeText(getActivity(),"Data : " + details ,Toast.LENGTH_LONG).show();
                    if (details.getSuccess().getStatus() && details.getSuccess().getData() != null && details.getSuccess().getData().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        textViewNoData.setVisibility(View.GONE);
                        List<MyRequestStatusResponse.Datum> list = details.getSuccess().getData();
                        myRequestStatusAdapter = new MyRequestStatusAdapter(MyRequestsActivity.this, list);
                        recyclerView.setAdapter(myRequestStatusAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        textViewNoData.setVisibility(View.VISIBLE);
                    }

                   /* // just given timer to go off refreshing icon after 5 seconds., later we need to remove this and on api response success, we need to do set refreshing to false.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeContainer.setRefreshing(false);

                        }
                    }, 5000);*/

                } else {
                    // Response code is 401
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<MyRequestStatusResponse> call, Throwable t) {

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


