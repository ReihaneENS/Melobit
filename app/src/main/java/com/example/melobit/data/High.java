package com.example.melobit.data;

import com.squareup.moshi.Json;

public class High{

	@Json(name = "fingerprint")
	private String fingerprint;

	@Json(name = "url")
	private String url;

	public String getFingerprint(){
		return fingerprint;
	}

	public String getUrl(){
		return url;
	}
}