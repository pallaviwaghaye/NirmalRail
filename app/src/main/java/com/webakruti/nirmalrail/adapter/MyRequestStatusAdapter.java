package com.webakruti.nirmalrail.adapter;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
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
    List<MyRequestStatusResponse.Station> list;


    public MyRequestStatusAdapter(Activity context, List<MyRequestStatusResponse.Station> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_request_status, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final MyRequestStatusResponse.Station myRequestStatus = list.get(position);


        viewHolder.textViewRequestStations.setText(myRequestStatus.getStationname());
        if (myRequestStatus.getAtPlatform() != null) {
            viewHolder.textViewRequestPlatform.setText(myRequestStatus.getAtPlatform());
        } else {
            viewHolder.textViewRequestPlatform.setText("N/A");
        }
        viewHolder.textViewRequestDate.setText(myRequestStatus.getComplaintDate());

        viewHolder.textViewRequestService.setText(myRequestStatus.getServicename().toLowerCase());

        if (myRequestStatus.getStatus().equalsIgnoreCase("invalid")) {
            viewHolder.textViewRequestStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
            viewHolder.textViewRequestStatus.setText(myRequestStatus.getStatus());
        } else if (myRequestStatus.getStatus().equalsIgnoreCase("inprocess")) {
            viewHolder.textViewRequestStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            viewHolder.textViewRequestStatus.setText(myRequestStatus.getStatus());
        } else if (myRequestStatus.getStatus().equalsIgnoreCase("complete")) {
            viewHolder.textViewRequestStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
            viewHolder.textViewRequestStatus.setText(myRequestStatus.getStatus());
        } else {
            viewHolder.textViewRequestStatus.setBackgroundColor(context.getResources().getColor(R.color.sky_blue));
            viewHolder.textViewRequestStatus.setText(myRequestStatus.getStatus());
        }

        // viewHolder.imageViewRequestImage.setImageDrawable(R.drawable.request_image);

/*
        Picasso.with(context).load(myRequestStatus.getAfterImgUrl()).placeholder(R.drawable.request_image)
*/
        Picasso.with(context).load(myRequestStatus.getBeforeImgUrl()).resize(300,300).placeholder(R.drawable.image_back)
                .into(viewHolder.imageViewRequestImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success", "Picasso Success - user profile pic");
                    }

                    public void onError() {
                        Log.i("", "Picasso Error - user profile pic");
                        viewHolder.imageViewRequestImage.setImageResource(R.drawable.image_back);
                    }
                });

        /*viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RailwayCategoryFormActivity.class);
                context.startActivity(intent);
            }
        });
*/

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
        private TextView textViewRequestService;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            //textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            imageViewRequestImage = (ImageView) itemView.findViewById(R.id.imageViewRequestImage);
            textViewRequestStations = (TextView) itemView.findViewById(R.id.textViewRequestStations);
            textViewRequestPlatform = (TextView) itemView.findViewById(R.id.textViewRequestPlatform);
            textViewRequestDate = (TextView) itemView.findViewById(R.id.textViewRequestDate);
            textViewRequestStatus = (TextView) itemView.findViewById(R.id.textViewRequestStatus);
            textViewRequestService = (TextView) itemView.findViewById(R.id.textViewRequestService);
        }
    }

}
