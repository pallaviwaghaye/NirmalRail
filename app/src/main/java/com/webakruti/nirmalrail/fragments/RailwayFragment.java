package com.webakruti.nirmalrail.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.RailwayCategoryAdapter;
import com.webakruti.nirmalrail.model.Category;
import com.webakruti.nirmalrail.utils.GridSpacingItemDecoration;
import com.webakruti.nirmalrail.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class RailwayFragment extends Fragment {


    private View rootView;
    private RecyclerView recyclerView;
    private RailwayCategoryAdapter railwayCategoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_railway, container, false);

        initViews();
        return rootView;
    }


    private void initViews() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        int spacing = (int) Utils.DpToPixel(getActivity(), 11); // 40px

        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(2, spacing, true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setNestedScrollingEnabled(false);


        List<Category> listOfCategories = new ArrayList<Category>();
        listOfCategories.add(new Category("Waiting Hall",getResources().getDrawable(R.drawable.icons_01)));
        listOfCategories.add(new Category("Urinal",getResources().getDrawable(R.drawable.icons_02)));
        listOfCategories.add(new Category("Lavatories",getResources().getDrawable(R.drawable.icons_03)));
        listOfCategories.add(new Category("Divyanhjan Toilet",getResources().getDrawable(R.drawable.icons_10)));
        listOfCategories.add(new Category("Foot Over Bridge",getResources().getDrawable(R.drawable.icons_09)));
        listOfCategories.add(new Category("Water Cooler",getResources().getDrawable(R.drawable.icons_08)));
        listOfCategories.add(new Category("Parking",getResources().getDrawable(R.drawable.icons_07)));
        listOfCategories.add(new Category("Dustbin",getResources().getDrawable(R.drawable.icons_06)));
        listOfCategories.add(new Category("Catering",getResources().getDrawable(R.drawable.icons_05)));
        listOfCategories.add(new Category("Waiting Room",getResources().getDrawable(R.drawable.icons_01)));
        listOfCategories.add(new Category("Any Other Places",getResources().getDrawable(R.drawable.icons_04)));
        railwayCategoryAdapter = new RailwayCategoryAdapter(getActivity(), listOfCategories);
        recyclerView.setAdapter(railwayCategoryAdapter);

    }


}
