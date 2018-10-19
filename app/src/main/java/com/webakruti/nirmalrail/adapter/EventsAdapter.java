package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.model.EventImageResponse;
import com.webakruti.nirmalrail.model.TechnologyImageResponse;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by DELL on 9/29/2018.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Activity context;
    List<EventImageResponse.Event> list;
    //List<EventImageResponse.Event> list2;
    //private EventsImageItemAdapter eventsImageItemAdapter;


    public EventsAdapter(Activity context, List<EventImageResponse.Event> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_events, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final EventImageResponse.Event event = list.get(position);
        //final EventImageResponse.Event eventItem = list2.get(position);

        viewHolder.recyclerViewEventsItem.setHasFixedSize(true);
        viewHolder.recyclerViewEventsItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        EventsImageItemAdapter eventsImageItemAdapter = new EventsImageItemAdapter(context, event.getGallery());

        viewHolder.textViewEvents.setText(event.getName());
        viewHolder.recyclerViewEventsItem.setAdapter(eventsImageItemAdapter);

        /*Picasso.with(context).load(event.getGallery().get(0)).placeholder(R.drawable.image_back)
                .into(viewHolder.imageViewEvents, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success", "Picasso Success - user profile pic");
                    }

                    public void onError() {
                        Log.i("", "Picasso Error - user profile pic");
                        viewHolder.imageViewEvents.setImageResource(R.drawable.image_back);
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewEvent;

        private TextView textViewEvents;
        private RecyclerView recyclerViewEventsItem;
        private ImageView imageViewEvents;


        public ViewHolder(View itemView) {
            super(itemView);
            cardViewEvent = (CardView) itemView.findViewById(R.id.cardViewEvent);

            imageViewEvents = (ImageView) itemView.findViewById(R.id.imageViewEvents);
            textViewEvents = (TextView) itemView.findViewById(R.id.textViewEvents);
            recyclerViewEventsItem = (RecyclerView) itemView.findViewById(R.id.recyclerViewEventsItem);

        }
    }

}
