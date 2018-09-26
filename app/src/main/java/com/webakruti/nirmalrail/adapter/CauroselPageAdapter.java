package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webakruti.nirmalrail.R;

import java.util.List;


public class CauroselPageAdapter extends PagerAdapter {

    Activity activity;
    LayoutInflater inflater;
    List<Drawable> imageList;


    public CauroselPageAdapter(Activity activity, List<Drawable> imageList) {

        this.activity = activity;
        this.imageList = imageList;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int i) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.item_carousel, container, false);
        final ImageView frame_img = (ImageView) itemView.findViewById(R.id.frame_img);

        frame_img.setImageDrawable(imageList.get(i));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }


}
