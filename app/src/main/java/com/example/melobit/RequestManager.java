package com.example.melobit;

import android.content.Context;

import com.example.melobit.data.ArtistResponse;
import com.example.melobit.data.Song;
import com.example.melobit.data.SongResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-beta.melobit.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiService apiService = retrofit.create(ApiService.class);

    public RequestManager(Context context) {
        this.context = context;
    }
    public void getLatestSongs(SongListRequestListener listener){
        Call<SongResponse> call = apiService.getLatestSongs();
        call.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getSliders(SongListRequestListener listener){
        Call<SongResponse> sliderSongs = apiService.getLatestSliders();
        sliderSongs.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }
    public void getTrendingArtist(ArtistsRequestListener listener){
        Call<ArtistResponse> topSingers = apiService.getTrendingArtists();
        topSingers.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(Call<ArtistResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getTopHitsToday(SongListRequestListener listener){
        Call<SongResponse> todayHits = apiService.getTop10DaySongs();
        todayHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getTopHitsThisWeek(SongListRequestListener listener){
        Call<SongResponse> thisWeekHits = apiService.getTop10WeekSongs();
        thisWeekHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getSongById(SongRequestListener listener,String id){
        Call<Song> song = apiService.getSongById(id);
        song.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
}
interface SongListRequestListener {
    void didFetch(SongResponse response);
    void didError(String errorMessage);
}
interface ArtistsRequestListener {
    void didFetch(ArtistResponse response);
    void didError(String errorMessage);
}
interface SongRequestListener {
    void didFetch(Song response);
    void didError(String errorMessage);
}

