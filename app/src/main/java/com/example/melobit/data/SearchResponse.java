package com.example.melobit.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

	@SerializedName("total")
	private int total;

	@SerializedName("results")
	private List<SearchResultItem> results;

	public int getTotal(){
		return total;
	}

	public List<SearchResultItem> getResults(){
		return results;
	}
}