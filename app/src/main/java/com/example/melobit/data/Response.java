package com.example.melobit.data;

import java.util.List;
import com.squareup.moshi.Json;

public class Response{

	@Json(name = "total")
	private int total;

	@Json(name = "results")
	private List<Song> results;

	public int getTotal(){
		return total;
	}

	public List<Song> getResults(){
		return results;
	}
}