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
import com.webakruti.nirmalrail.ui.AdminStatusFormActivity;

import java.util.List;

public class AdminColonyStatusAdapter extends RecyclerView.Adapter<AdminColonyStatusAdapter.ViewHolder> {

    Activity context;
    List<MyRequestStatusResponse.Colony> list;


    public AdminColonyStatusAdapter(Activity context, List<MyRequestStatusResponse.Colony> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public AdminColonyStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_request_colony_status, viewGroup, false);
        AdminColonyStatusAdapter.ViewHolder viewHolder = new AdminColonyStatusAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdminColonyStatusAdapter.ViewHolder viewHolder, final int position) {

        final MyRequestStatusResponse.Colony myRequestStatus = list.get(position);


        viewHolder.textViewRequestColonyName.setText(myRequestStatus.getColonyname());
        viewHolder.textViewRequestAddress.setText(myRequestStatus.getAddress());
        viewHolder.textViewRequestDate.setText(myRequestStatus.getComplaintDate());
        viewHolder.textViewRequestDesc.setText(myRequestStatus.getDescription());


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
       /* Picasso.with(context).load(myRequestStatus.getBeforeImgUrl()).placeholder(R.drawable.image_back)
                .into(viewHolder.imageViewRequestImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success", "Picasso Success - user profile pic");
                    }

                    public void onError() {
                        Log.i("", "Picasso Error - user profile pic");
                        viewHolder.imageViewRequestImage.setImageResource(R.drawable.image_back);
                    }
                });*/

        Picasso.with(context).load(myRequestStatus.getBeforeImgUrl()).placeholder(R.drawable.image_not_found).resize(300,300).into(viewHolder.imageViewRequestImage);


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminStatusFormActivity.class);
                intent.putExtra("id",myRequestStatus.getId());
                intent.putExtra("STATUS_INFO", myRequestStatus.getStatus());
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
        private TextView textViewRequestColonyName;
        private TextView textViewRequestDesc;
        private TextView textViewRequestDate;
        private TextView textViewRequestStatus;
        private TextView textViewRequestAddress;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            //textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            imageViewRequestImage = (ImageView) itemView.findViewById(R.id.imageViewRequestImage);
            textViewRequestColonyName = (TextView) itemView.findViewById(R.id.textViewRequestColonyName);
            textViewRequestDesc = (TextView) itemView.findViewById(R.id.textViewRequestDesc);

            textViewRequestAddress = (TextView) itemView.findViewById(R.id.textViewRequestAddress);

            textViewRequestDate = (TextView) itemView.findViewById(R.id.textViewRequestDate);
            textViewRequestStatus = (TextView) itemView.findViewById(R.id.textViewRequestStatus);

        }
    }

}
