package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.melobit.data.SongsResponse;
import com.squareup.moshi.Moshi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView results;
    private RecyclerView rvLatestSongs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results =  findViewById(R.id.results);
        rvLatestSongs = findViewById(R.id.rv_latest_songs);
        viewPager = findViewById(R.id.view_pager);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvLatestSongs.setLayoutManager(layoutManager);

        Moshi moshi = new Moshi.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-beta.melobit.com/v1/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
        ApiService apiService = retrofit.create(ApiService.class);



        Call<SongsResponse> call = apiService.getLatestSongs();
        Call<SongsResponse> sliderSongs = apiService.getLatestSliders();

        call.enqueue(new Callback<SongsResponse>() {
            @Override
            public void onResponse(Call<SongsResponse> call, Response<SongsResponse> response) {
                 if (!response.isSuccessful()){
                     results.setText("Code: "+response.code());
                     return;
                 }
                SongsResponse songs = response.body();
                rvLatestSongs.setAdapter(new SongAdapter(getApplicationContext(),songs.getResults()));

            }

            @Override
            public void onFailure(Call<SongsResponse> call, Throwable t) {
                    results.setText(t.getMessage());
            }
        });
        sliderSongs.enqueue(new Callback<SongsResponse>() {
            @Override
            public void onResponse(Call<SongsResponse> call, Response<SongsResponse> response) {
                if (!response.isSuccessful()){
                    results.setText("Code: "+response.code());
                    return;
                }
                SongsResponse sliders = response.body();
                viewPager.setAdapter(new SliderAdapter(MainActivity.this,sliders.getResults()));
            }

            @Override
            public void onFailure(Call<SongsResponse> call, Throwable t) {
                results.setText(t.getMessage());
            }
        });
    }
}