package com.webakruti.nirmalrail.ui;

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
import com.webakruti.nirmalrail.utils.NetworkUtil;

import okhttp3.internal.Util;

public class MyRequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private LinearLayoutManager mLayoutManger;
    private ImageView imageViewBack;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
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
        recyclerView.setAdapter(new MyRequestStatusAdapter(MyRequestsActivity.this, 3));

        initSwipeLayout();
    }

    private void initSwipeLayout() {

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

    }

    private void callGetRequestAPI() {

        // just given timer to go off refreshing icon after 5 seconds., later we need to remove this and on api response success, we need to do set refreshing to false.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);

            }
        }, 5000);

    }


}


