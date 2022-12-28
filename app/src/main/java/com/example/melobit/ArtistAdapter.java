package com.example.melobit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.melobit.data.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    Context context;
    List<Artist> artists;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_view_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.tvName.setText(artists.get(position).getFullName());
        Glide.with(context)
                .load(artists.get(position).getImage().getCover().getUrl())
                .circleCrop()
                .into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}