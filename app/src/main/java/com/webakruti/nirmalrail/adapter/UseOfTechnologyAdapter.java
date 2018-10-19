package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
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
import com.webakruti.nirmalrail.model.TechnologyImageResponse;

import java.util.List;

/**
 * Created by DELL on 9/29/2018.
 */

public class UseOfTechnologyAdapter extends RecyclerView.Adapter<UseOfTechnologyAdapter.ViewHolder> {

    Activity context;
    List<TechnologyImageResponse.Technology> list;


    public UseOfTechnologyAdapter(Activity context, List<TechnologyImageResponse.Technology> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_use_of_technology, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final TechnologyImageResponse.Technology technology = list.get(position);


        viewHolder.textViewTechnology.setText(technology.getName());

        Picasso.with(context).load(technology.getImgUrl().toString()).placeholder(R.drawable.image_back)
                .into(viewHolder.imageViewTechnology, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("Success", "Picasso Success - user profile pic");
                    }

                    public void onError() {
                        Log.i("", "Picasso Error - user profile pic");
                        viewHolder.imageViewTechnology.setImageResource(R.drawable.image_back);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        private ImageView imageViewTechnology;
        private TextView textViewHeadingTechnology;
        private TextView textViewTechnology;
        private View viewTechnology;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            //textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            imageViewTechnology = (ImageView) itemView.findViewById(R.id.imageViewTechnology);
            textViewTechnology = (TextView) itemView.findViewById(R.id.textViewTechnology);


        }
    }

}

