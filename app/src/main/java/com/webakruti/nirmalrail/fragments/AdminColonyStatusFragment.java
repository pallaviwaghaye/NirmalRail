package com.webakruti.nirmalrail.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.AdminColonyStatusAdapter;
import com.webakruti.nirmalrail.adapter.MyRequestColonyStatusAdapter;
import com.webakruti.nirmalrail.model.MyRequestStatusResponse;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminColonyStatusFragment extends Fragment {

    private View rootView;
    private com.webakruti.nirmalrail.utils.CustomSwipeToRefresh swipeContainer;
    private RecyclerView recyclerView;
    private TextView textViewNoData;
    private ProgressDialog progressDialogForAPI;
    private AdminColonyStatusAdapter adminColonyStatusAdapter;
    boolean isCallFromPullDown = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_admin_colony_status, container, false);

        swipeContainer = (com.webakruti.nirmalrail.utils.CustomSwipeToRefresh) rootView.findViewById(R.id.swipeContainer);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        textViewNoData = (TextView) rootView.findViewById(R.id.textViewNoData);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        //recyclerView.setAdapter(new MyRequestStatusAdapter(MyRequestsActivity.this, list));

        initSwipeLayout();

        progressDialogForAPI = new ProgressDialog(getActivity());
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        if (NetworkUtil.hasConnectivity(getActivity())) {
            callGetRequestAPI();
        } else {
            Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    private void initSwipeLayout() {

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("", "swipe to refresh");
                if (NetworkUtil.hasConnectivity(getActivity())) {
                    // call API
                    isCallFromPullDown = true;
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

        SharedPreferenceManager.setApplicationContext(getActivity());
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
                    if (details.getSuccess().getStatus() && details.getSuccess().getColony() != null && details.getSuccess().getColony().size() > 0) {
                        textViewNoData.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        List<MyRequestStatusResponse.Colony> list = details.getSuccess().getColony();
                        adminColonyStatusAdapter = new AdminColonyStatusAdapter(getActivity(), list);
                        recyclerView.setAdapter(adminColonyStatusAdapter);
                    } else {
                        textViewNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }


                } else {
                    // Response code is 401
                }

                if (isCallFromPullDown) {
                    swipeContainer.setRefreshing(false);
                    isCallFromPullDown = false;
                } else {
                    if (progressDialogForAPI != null) {
                        progressDialogForAPI.cancel();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyRequestStatusResponse> call, Throwable t) {

                if (t != null) {

                    if (isCallFromPullDown) {
                        swipeContainer.setRefreshing(false);
                        isCallFromPullDown = false;
                    } else {
                        if (progressDialogForAPI != null) {
                            progressDialogForAPI.cancel();
                        }
                    }
                    if (t.getMessage() != null)
                        Log.e("error", t.getMessage());
                }

            }
        });

    }
}
