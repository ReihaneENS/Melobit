package com.example.melobit.data;

import java.util.List;
import com.squareup.moshi.Json;

public class Song {

	@Json(name = "duration")
	private int duration;

	@Json(name = "hasVideo")
	private boolean hasVideo;

	@Json(name = "copyrighted")
	private boolean copyrighted;

	@Json(name = "image")
	private Image image;

	@Json(name = "artists")
	private List<Artist> artists;

	@Json(name = "releaseDate")
	private String releaseDate;

	@Json(name = "album")
	private Album album;

	@Json(name = "localized")
	private boolean localized;

	@Json(name = "id")
	private String id;

	@Json(name = "audio")
	private Audio audio;

	@Json(name = "title")
	private String title;

	@Json(name = "downloadCount")
	private String downloadCount;

	public int getDuration(){
		return duration;
	}

	public boolean isHasVideo(){
		return hasVideo;
	}

	public boolean isCopyrighted(){
		return copyrighted;
	}

	public Image getImage(){
		return image;
	}

	public List<Artist> getArtists(){
		return artists;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Album getAlbum(){
		return album;
	}

	public boolean isLocalized(){
		return localized;
	}

	public String getId(){
		return id;
	}

	public Audio getAudio(){
		return audio;
	}

	public String getTitle(){
		return title;
	}

	public String getDownloadCount(){
		return downloadCount;
	}
}