package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.webakruti.nirmalrail.model.MyRequestStatusResponse;
import com.webakruti.nirmalrail.ui.MyRequestsActivity;
import com.webakruti.nirmalrail.ui.RailwayCategoryFormActivity;

import java.util.List;

/**
 * Created by DELL on 9/22/2018.
 */

public class MyRequestStatusAdapter extends RecyclerView.Adapter<MyRequestStatusAdapter.ViewHolder> {

    Activity context;
    List<MyRequestStatusResponse> list;

    public MyRequestStatusAdapter(Activity context, List<MyRequestStatusResponse> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyRequestStatusAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_request_status, viewGroup, false);
        MyRequestStatusAdapter.ViewHolder viewHolder = new MyRequestStatusAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( MyRequestStatusAdapter.ViewHolder viewHolder, final int position) {

        final MyRequestStatusResponse myRequestStatus = list.get(position);


        viewHolder.textViewRequestStations.setText(myRequestStatus.getSuccess().getData().get(0).getStationname());
        viewHolder.textViewRequestPlatform.setText(myRequestStatus.getSuccess().getData().get(0).getAtPlatform());
        viewHolder.textViewRequestDate.setText(myRequestStatus.getSuccess().getData().get(0).getComplaintDate());
        viewHolder.textViewRequestStatus.setText(myRequestStatus.getSuccess().getData().get(0).getStatus());

       // viewHolder.imageViewRequestImage.setImageDrawable(R.drawable.request_image);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RailwayCategoryFormActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        private ImageView imageViewRequestImage;
        private TextView textViewRequestStations;
        private TextView textViewRequestPlatform;
        private TextView textViewRequestDate;
        private TextView textViewRequestStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            //textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            imageViewRequestImage = (ImageView) itemView.findViewById(R.id.imageViewRequestImage);
            textViewRequestStations = (TextView) itemView.findViewById(R.id.textViewRequestStations);
            textViewRequestPlatform = (TextView) itemView.findViewById(R.id.textViewRequestPlatform);
            textViewRequestDate = (TextView) itemView.findViewById(R.id.textViewRequestDate);
            textViewRequestStatus = (TextView) itemView.findViewById(R.id.textViewRequestStatus);

        }
    }

}
