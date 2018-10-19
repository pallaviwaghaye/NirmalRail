package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.EventImageResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 9/30/2018.
 */

public class EventsImageItemAdapter extends RecyclerView.Adapter<EventsImageItemAdapter.ViewHolder> {

    Activity context;
    List<String> list1 = new ArrayList<String>();


    public EventsImageItemAdapter(Activity context, List<String> list1) {
        this.context = context;
        this.list1 = list1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_events_images, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final String eventItem = list1.get(position);


        //viewHolder.textViewTechnology.setText(technology.getName());

//        Picasso.with(context).load(eventItem).placeholder(R.drawable.image_back)
//                .into(viewHolder.imageViewEvents, new Callback.EmptyCallback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.i("Success", "Picasso Success - user profile pic");
//                    }
//
//                    public void onError() {
//                        Log.i("", "Picasso Error - user profile pic");
//                        viewHolder.imageViewEvents.setImageResource(R.drawable.image_back);
//                    }
//                });


        Glide.with(context).load(eventItem).apply(new RequestOptions()
                .dontAnimate()
                .placeholder(R.drawable.image_back)
                .dontTransform()).into(viewHolder.imageViewEvents);

    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewEventItem;

        private ImageView imageViewEvents;


        public ViewHolder(View itemView) {
            super(itemView);

            //textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            imageViewEvents = (ImageView) itemView.findViewById(R.id.imageViewEvents);

        }
    }
}
