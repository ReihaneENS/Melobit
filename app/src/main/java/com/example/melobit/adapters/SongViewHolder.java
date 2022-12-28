package com.example.melobit.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melobit.R;

public class SongViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCover;
    TextView tvTitle,tvSinger;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCover = itemView.findViewById(R.id.iv_cover);
        tvTitle = itemView.findViewById(R.id.tv_song_title);
        tvSinger = itemView.findViewById(R.id.tv_singer);

    }
}
