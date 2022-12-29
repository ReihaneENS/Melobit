package com.example.melobit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.melobit.adapters.ArtistAdapter;
import com.example.melobit.adapters.SliderAdapter;
import com.example.melobit.adapters.SongAdapter;
import com.example.melobit.data.ArtistResponse;
import com.example.melobit.data.SongResponse;

public class MainActivity extends AppCompatActivity{

    private TextView results;
    private RecyclerView rvLatestSongs, rvTopSingers;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = findViewById(R.id.results);
        rvLatestSongs = findViewById(R.id.rv_latest_songs);
        rvTopSingers = findViewById(R.id.rv_top_singers);
        viewPager = findViewById(R.id.view_pager);
        Button hitsBtn = findViewById(R.id.button);

        hitsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HitsActivity.class);
            startActivity(intent);
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvLatestSongs.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTopSingers.setLayoutManager(layoutManager1);

        RequestManager manager = new RequestManager(this);

        SongListRequestListener latestListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                rvLatestSongs.setAdapter(new SongAdapter(getApplicationContext(), response.getResults(), (position, v,id) -> {
                    Intent intent = new Intent(MainActivity.this,SongActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }));
            }
            @Override
            public void didError(String errorMessage) {
                results.setText(errorMessage);
            }
        };
        SongListRequestListener sliderListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                viewPager.setAdapter(new SliderAdapter(MainActivity.this,response.getResults()));
            }
            @Override
            public void didError(String errorMessage) {
                results.setText(errorMessage);
            }
        };
        ArtistsRequestListener artistsListener = new ArtistsRequestListener() {
            @Override
            public void didFetch(ArtistResponse response) {
                rvTopSingers.setAdapter(new ArtistAdapter(MainActivity.this, response.getResults()));
            }
            @Override
            public void didError(String errorMessage) {
                results.setText(errorMessage);
            }
        };
        manager.getLatestSongs(latestListener);
        manager.getTrendingArtist(artistsListener);
        manager.getSliders(sliderListener);
    }
}