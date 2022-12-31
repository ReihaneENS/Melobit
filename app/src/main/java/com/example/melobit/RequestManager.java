package com.example.melobit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.melobit.data.ArtistResponse;
import com.example.melobit.data.SearchResponse;
import com.example.melobit.data.SearchResultItem;
import com.example.melobit.data.Song;
import com.example.melobit.data.SongResponse;

import java.io.IOException;
import java.util.List;

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

    public void getLatestSongs(SongListRequestListener listener) {
        Call<SongResponse> call = apiService.getLatestSongs();
        call.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(@NonNull Call<SongResponse> call, @NonNull Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SongResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSliders(SongListRequestListener listener) {
        Call<SongResponse> sliderSongs = apiService.getLatestSliders();
        sliderSongs.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(@NonNull Call<SongResponse> call, @NonNull Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SongResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void getTrendingArtist(ArtistsRequestListener listener) {
        Call<ArtistResponse> topSingers = apiService.getTrendingArtists();
        topSingers.enqueue(new Callback<ArtistResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArtistResponse> call, @NonNull Response<ArtistResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArtistResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getTopHitsToday(SongListRequestListener listener) {
        Call<SongResponse> todayHits = apiService.getTop10DaySongs();
        todayHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(@NonNull Call<SongResponse> call, @NonNull Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SongResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getTopHitsThisWeek(SongListRequestListener listener) {
        Call<SongResponse> thisWeekHits = apiService.getTop10WeekSongs();
        thisWeekHits.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(@NonNull Call<SongResponse> call, @NonNull Response<SongResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SongResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSongById(SongRequestListener listener, String id) {
        Call<Song> song = apiService.getSongById(id);
        song.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(@NonNull Call<Song> call, @NonNull Response<Song> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                try {
                    listener.didFetch(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Song> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void search(SearchResultsListener listener, String query) {
        Call<SearchResponse> results = apiService.search(query);
        results.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body().getResults());
            }
            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


}
interface SongListRequestListener {
    void didFetch(SongResponse response);

    void didError(String errorMessage);
}
interface SearchResultsListener {
    void didFetch(List<SearchResultItem> response);

    void didError(String errorMessage);
}

interface ArtistsRequestListener {
    void didFetch(ArtistResponse response);

    void didError(String errorMessage);
}

interface SongRequestListener {
    void didFetch(Song response) throws IOException;

    void didError(String errorMessage);
}

