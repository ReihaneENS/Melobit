package com.example.melobit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private TextView results;
    private RecyclerView rvLatestSongs, rvTopSingers;
    private EditText etQuery;
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
        FloatingActionButton btnSearch = findViewById(R.id.btn_search);
        etQuery = findViewById(R.id.et_query);

        hitsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HitsActivity.class);
            startActivity(intent);
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query",etQuery.getText().toString());
                startActivity(intent);
            }
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