package com.webakruti.nirmalrail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webakruti.nirmalrail.R;


public class VisionAndObjectiveFragment extends Fragment {


    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootView = inflater.inflate(R.layout.fragment_vision_and_objective, container, false);

        return rootView;
    }
}
