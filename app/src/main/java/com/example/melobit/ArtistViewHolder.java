package com.example.melobit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCover;
    TextView tvName;

    public ArtistViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCover = itemView.findViewById(R.id.iv_cover);
        tvName = itemView.findViewById(R.id.tv_name);

    }
}