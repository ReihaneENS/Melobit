package com.example.melobit;


import com.example.melobit.data.Response;
import com.example.melobit.data.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("song/new/0/11")
    Call<Response> getLatestSongs();

}
