package com.example.melobit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.melobit.R;
import com.example.melobit.data.SearchResultItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        Context context;
        List<SearchResultItem> results;
        private final ClickListener clickListener;

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView ivCover;
    TextView tvName;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCover = itemView.findViewById(R.id.iv_cover);
        tvName = itemView.findViewById(R.id.tv_name);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position>=0 && results.get(position).getType().equals("song")){
            clickListener.onSongClick(position,view,results.get(position).getSong().getId());
        }
    }
}
public interface ClickListener {
    void onSongClick(int position,View v,String id);
}
    public SearchAdapter(Context context, List<SearchResultItem> results, SearchAdapter.ClickListener clickListener) {
        this.context = context;
        this.results = results;
        this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item_view,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        if (results.get(position).getType().equals("song")){
            holder.tvName.setText(results.get(position).getSong().getTitle());
            Glide.with(context)
                    .load(results.get(position).getSong().getImage().getCover().getUrl())
                    .into(holder.ivCover);
        }else {
            holder.tvName.setText(results.get(position).getArtist().getFullName());
            Glide.with(context)
                    .load(results.get(position).getArtist().getImage().getCover().getUrl())
                    .circleCrop()
                    .into(holder.ivCover);
        }
    }
    @Override
    public int getItemCount() {
        return results.size();
    }
}
