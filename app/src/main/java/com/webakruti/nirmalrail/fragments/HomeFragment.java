package com.webakruti.nirmalrail.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.CauroselPageAdapter;
import com.webakruti.nirmalrail.pageindicator.PageControl;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private View rootView;
    private ViewPager viewPager;
    private PageControl page_control;
    private CauroselPageAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        List<Drawable> list = new ArrayList<>();
        list.add(getResources().getDrawable(R.drawable.image1));
        list.add(getResources().getDrawable(R.drawable.image2));
        list.add(getResources().getDrawable(R.drawable.image3));
        list.add(getResources().getDrawable(R.drawable.image4));





        // send this list to carousel adapter.

        initCarouselData(list);

        return rootView;
    }


    private void initCarouselData(List<Drawable> imageList) {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        page_control = (PageControl) rootView.findViewById(R.id.page_control);

        viewPager.setPageMargin(10);
        viewPager.setClipToPadding(false);
        pagerAdapter = new CauroselPageAdapter(getActivity(), imageList);
        viewPager.setAdapter(pagerAdapter);
        page_control.setViewPager(viewPager);
        page_control.setPosition(0);

    }
}
