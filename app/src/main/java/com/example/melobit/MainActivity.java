package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.moshi.Moshi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView results;
    private RecyclerView rvLatestSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results =  findViewById(R.id.results);
        rvLatestSongs = findViewById(R.id.rv_latest_songs);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvLatestSongs.setLayoutManager(layoutManager);
        Moshi moshi = new Moshi.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-beta.melobit.com/v1/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<com.example.melobit.data.Response> call = apiService.getLatestSongs();

        call.enqueue(new Callback<com.example.melobit.data.Response>() {
            @Override
            public void onResponse(Call<com.example.melobit.data.Response> call, Response<com.example.melobit.data.Response> response) {
                 if (!response.isSuccessful()){
                     results.setText("Code: "+response.code());
                     return;
                 }
                com.example.melobit.data.Response  songs = response.body();
                rvLatestSongs.setAdapter(new SongAdapter(getApplicationContext(),songs.getResults()));

            }

            @Override
            public void onFailure(Call<com.example.melobit.data.Response> call, Throwable t) {
                    results.setText(t.getMessage());
            }
        });
    }
}