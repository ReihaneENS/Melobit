package com.example.melobit.data;

import com.squareup.moshi.Json;

public class Artist {

	@Json(name = "image")
	private Image image;

	@Json(name = "fullName")
	private String fullName;

	@Json(name = "sumSongsDownloadsCount")
	private String sumSongsDownloadsCount;

	@Json(name = "id")
	private String id;

	@Json(name = "followersCount")
	private int followersCount;

	@Json(name = "type")
	private String type;

	public Image getImage(){
		return image;
	}

	public String getFullName(){
		return fullName;
	}

	public String getSumSongsDownloadsCount(){
		return sumSongsDownloadsCount;
	}

	public String getId(){
		return id;
	}

	public int getFollowersCount(){
		return followersCount;
	}

	public String getType(){
		return type;
	}
}