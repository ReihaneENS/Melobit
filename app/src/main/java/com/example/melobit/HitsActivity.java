package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.melobit.adapters.SongAdapter;
import com.example.melobit.data.SongResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HitsActivity extends AppCompatActivity {

    private RecyclerView rvTodayHits,rvThisWeekHits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hits);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rvTodayHits = findViewById(R.id.rv_today);
        rvThisWeekHits = findViewById(R.id.rv_this_week);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTodayHits.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThisWeekHits.setLayoutManager(layoutManager1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-beta.melobit.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);



        Call<SongResponse> todayHits = apiService.getTop10DaySongs();
        Call<SongResponse> thisWeekHits = apiService.getTop10WeekSongs();

        todayHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()){
                    //results.setText("Code: "+response.code());
                    return;
                }
                SongResponse songs = response.body();
                rvTodayHits.setAdapter(new SongAdapter(getApplicationContext(),songs.getResults()));

            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                //results.setText(t.getMessage());
            }
        });
        thisWeekHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()){
                    //results.setText("Code: "+response.code());
                    return;
                }
                SongResponse songs = response.body();
                rvThisWeekHits
                        .setAdapter(new SongAdapter(getApplicationContext(),songs.getResults()));

            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                //results.setText(t.getMessage());
            }
        });
    }
}