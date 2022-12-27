package com.example.melobit.data;

import java.util.List;
import com.squareup.moshi.Json;

public class Album{

	@Json(name = "image")
	private Image image;

	@Json(name = "artists")
	private List<Artist> artists;

	@Json(name = "releaseDate")
	private String releaseDate;

	@Json(name = "name")
	private String name;

	public Image getImage(){
		return image;
	}

	public List<Artist> getArtists(){
		return artists;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public String getName(){
		return name;
	}
}