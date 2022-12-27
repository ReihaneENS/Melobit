package com.example.melobit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.melobit.data.Artist;
import com.example.melobit.data.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    Context context;
    List<Song> songs;

    public SongAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(context).inflate(R.layout.song_item_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.tvTitle.setText(songs.get(position).getTitle());
        List<Artist> artists = songs.get(position).getArtists();
        holder.tvSinger.setText(songs.get(position).getArtists().get(0).getFullName());
        Glide.with(context)
                .load(songs.get(position).getImage().getCover().getUrl())
                .into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
