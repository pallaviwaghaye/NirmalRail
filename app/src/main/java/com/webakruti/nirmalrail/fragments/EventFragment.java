package com.webakruti.nirmalrail.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.EventsAdapter;
import com.webakruti.nirmalrail.adapter.UseOfTechnologyAdapter;
import com.webakruti.nirmalrail.model.EventImageResponse;
import com.webakruti.nirmalrail.model.TechnologyImageResponse;
import com.webakruti.nirmalrail.retrofit.ApiConstants;
import com.webakruti.nirmalrail.retrofit.service.RestClient;
import com.webakruti.nirmalrail.utils.NetworkUtil;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerViewEvents;
    private TextView textViewNoData;
    private EventsAdapter adapter;
    private ProgressDialog progressDialogForAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootView = inflater.inflate(R.layout.fragment_event, container, false);

        initViews();

        if (NetworkUtil.hasConnectivity(getActivity())) {
            callGetEventsAPI();
        } else {
            Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    private void initViews() {
        textViewNoData = (TextView) rootView.findViewById(R.id.textViewNoData);
        recyclerViewEvents = (RecyclerView) rootView.findViewById(R.id.recyclerViewEvents);
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setNestedScrollingEnabled(false);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    private void callGetEventsAPI() {


        progressDialogForAPI = new ProgressDialog(getActivity());
        progressDialogForAPI.setCancelable(false);
        progressDialogForAPI.setIndeterminate(true);
        progressDialogForAPI.setMessage("Please wait...");
        progressDialogForAPI.show();

        SharedPreferenceManager.setApplicationContext(getActivity());
        String token = SharedPreferenceManager.getUserObjectFromSharedPreference().getSuccess().getToken();

        String headers = "Bearer " + token;
        Call<EventImageResponse> requestCallback = RestClient.getApiService(ApiConstants.BASE_URL).getEvents(headers);
        requestCallback.enqueue(new Callback<EventImageResponse>() {
            @Override
            public void onResponse(Call<EventImageResponse> call, Response<EventImageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                    EventImageResponse details = response.body();
                    //  Toast.makeText(getActivity(),"Data : " + details ,Toast.LENGTH_LONG).show();
                    if (details.getSuccess().getStatus() && details.getSuccess().getEvents() != null && details.getSuccess().getEvents().size() > 0) {
                        textViewNoData.setVisibility(View.GONE);
                        recyclerViewEvents.setVisibility(View.VISIBLE);
                        List<EventImageResponse.Event> list = details.getSuccess().getEvents();
                        adapter = new EventsAdapter(getActivity(), list);
                        recyclerViewEvents.setAdapter(adapter);
                    } else {
                        textViewNoData.setVisibility(View.VISIBLE);
                        recyclerViewEvents.setVisibility(View.GONE);
                    }

                } else {
                    // Response code is 401
                }

                if (progressDialogForAPI != null) {
                    progressDialogForAPI.cancel();
                }
            }

            @Override
            public void onFailure(Call<EventImageResponse> call, Throwable t) {

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
