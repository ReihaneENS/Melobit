package com.example.melobit.data;

import java.util.List;
import com.squareup.moshi.Json;

public class ArtistResponse{

	@Json(name = "total")
	private int total;

	@Json(name = "results")
	private List<Artist> results;

	public int getTotal(){
		return total;
	}

	public List<Artist> getResults(){
		return results;
	}
}