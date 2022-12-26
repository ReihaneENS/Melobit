package com.example.melobit.data;

import com.squareup.moshi.Json;

public class Image{

	@Json(name = "thumbnail_small")
	private ThumbnailSmall thumbnailSmall;

	@Json(name = "cover")
	private Cover cover;

	@Json(name = "thumbnail")
	private Thumbnail thumbnail;

	@Json(name = "cover_small")
	private CoverSmall coverSmall;

	@Json(name = "slider")
	private Slider slider;

	public ThumbnailSmall getThumbnailSmall(){
		return thumbnailSmall;
	}

	public Cover getCover(){
		return cover;
	}

	public Thumbnail getThumbnail(){
		return thumbnail;
	}

	public CoverSmall getCoverSmall(){
		return coverSmall;
	}

	public Slider getSlider(){
		return slider;
	}
}