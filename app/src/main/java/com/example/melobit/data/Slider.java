package com.example.melobit.data;

import com.squareup.moshi.Json;

public class Slider{

	@Json(name = "url")
	private String url;

	public String getUrl(){
		return url;
	}
}