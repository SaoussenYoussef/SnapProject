package com.saoussen.snapanonyme.presentation.activity.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saoussen.snapanonyme.R;

import java.util.List;



public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.SnapitemViewHolder> {


    private LayoutInflater mInflater;


    public SnapAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    @NonNull
    @Override
    public SnapAdapter.SnapitemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mItemView = mInflater.inflate(R.layout.snap_item, viewGroup, false);
        SnapitemViewHolder snapitemViewHolder = new SnapitemViewHolder(mItemView);
        return snapitemViewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull SnapAdapter.SnapitemViewHolder snapitemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    ImageView snapimage;

    public class SnapitemViewHolder extends RecyclerView.ViewHolder {

        public SnapitemViewHolder(@NonNull View itemView) {
            super(itemView);

            snapimage = itemView.findViewById(R.id.snap_image);

        }
    }
}
