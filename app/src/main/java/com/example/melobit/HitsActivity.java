package com.example.melobit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melobit.adapters.SongAdapter;
import com.example.melobit.data.SongResponse;

public class HitsActivity extends AppCompatActivity {

    private RecyclerView rvTodayHits, rvThisWeekHits;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hits);
        rvTodayHits = findViewById(R.id.rv_today);
        rvThisWeekHits = findViewById(R.id.rv_this_week);
        tvError = findViewById(R.id.tv_error);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTodayHits.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThisWeekHits.setLayoutManager(layoutManager1);

        RequestManager manager = new RequestManager(this);
        SongListRequestListener todayListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                rvTodayHits.setAdapter(new SongAdapter(HitsActivity.this, response.getResults(),
                        new SongAdapter.ClickListener() {
                            @Override
                            public void onSongClick(int position, View v, String id) {
                                Intent intent = new Intent(HitsActivity.this, SongActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                tvError.setText(errorMessage);
            }
        };
        SongListRequestListener thisWeekListener = new SongListRequestListener() {
            @Override
            public void didFetch(SongResponse response) {
                rvThisWeekHits.setAdapter(new SongAdapter(HitsActivity.this, response.getResults(),
                        (position, v, id) -> {
                            Intent intent = new Intent(HitsActivity.this, SongActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                tvError.setText(errorMessage);
            }
        };
        manager.getTopHitsToday(todayListener);
        manager.getTopHitsThisWeek(thisWeekListener);
    }
}