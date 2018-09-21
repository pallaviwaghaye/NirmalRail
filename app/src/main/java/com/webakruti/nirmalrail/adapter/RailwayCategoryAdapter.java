package com.webakruti.nirmalrail.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.ui.RailwayCategoryFormActivity;


public class RailwayCategoryAdapter extends RecyclerView.Adapter<RailwayCategoryAdapter.ViewHolder> {

    Activity context;
    int size;

    public RailwayCategoryAdapter(Activity context, int size) {
        this.context = context;
        this.size = size;
    }

    @Override
    public RailwayCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_railway_category, viewGroup, false);
        RailwayCategoryAdapter.ViewHolder viewHolder = new RailwayCategoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RailwayCategoryAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.textViewCategory.setText("Category " + position);
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
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView textViewCategory;
        /*TextView textViewCategoryName;
        ImageView imageViewCategory;*/

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
            /*textViewCategoryName = (TextView) itemView.findViewById(R.id.textViewCategoryName);
            imageViewCategory = (ImageView) itemView.findViewById(R.id.imageViewCategory);*/
        }
    }

}


