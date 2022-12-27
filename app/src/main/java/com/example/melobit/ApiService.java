package com.example.melobit;


import com.example.melobit.data.ArtistResponse;
import com.example.melobit.data.Song;
import com.example.melobit.data.SongsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("song/new/0/11")
    Call<SongsResponse> getLatestSongs();

    @GET("song/slider/latest")
    Call<SongsResponse> getLatestSliders();

    @GET("artist/trending/0/4")
    Call<ArtistResponse> getTrendingArtists();

    @GET("song/top/day/0/100")
    Call<SongsResponse> getTop10DaySongs();

    @GET("song/top/week/0/100")
    Call<SongsResponse> getTop10WeekSongs();

    @GET("song/{id}")
    Call<Song> getSongById(@Path("id") int songId);

    @GET("search/query/{query}/0/50")
    Call<SongsResponse> search(@Path("query") String query);


}
