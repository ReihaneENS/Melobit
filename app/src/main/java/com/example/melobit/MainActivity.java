package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.melobit.data.ArtistResponse;
import com.example.melobit.data.SongResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView results;
    private RecyclerView rvLatestSongs,rvTopSingers;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results =  findViewById(R.id.results);
        rvLatestSongs = findViewById(R.id.rv_latest_songs);
        rvTopSingers = findViewById(R.id.rv_top_singers);
        viewPager = findViewById(R.id.view_pager);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvLatestSongs.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTopSingers.setLayoutManager(layoutManager1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-beta.melobit.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);



        Call<SongResponse> call = apiService.getLatestSongs();
        Call<SongResponse> sliderSongs = apiService.getLatestSliders();
        Call<ArtistResponse> topSingers = apiService.getTrendingArtists();

        call.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                 if (!response.isSuccessful()){
                     results.setText("Code: "+response.code());
                     return;
                 }
                SongResponse songs = response.body();
                rvLatestSongs.setAdapter(new SongAdapter(getApplicationContext(),songs.getResults()));

            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                    results.setText(t.getMessage());
            }
        });
        sliderSongs.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()){
                    results.setText("Code: "+response.code());
                    return;
                }
                SongResponse sliders = response.body();
                viewPager.setAdapter(new SliderAdapter(MainActivity.this,sliders.getResults()));
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                results.setText(t.getMessage());
            }
        });
        topSingers.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                if (!response.isSuccessful()){
                    results.setText("Code: "+response.code());
                    return;
                }
                ArtistResponse artists = response.body();
                rvTopSingers.setAdapter(new ArtistAdapter(MainActivity.this,artists.getResults()));
            }

            @Override
            public void onFailure(Call<ArtistResponse> call, Throwable t) {
                results.setText(t.getMessage());
            }
        });
    }
}