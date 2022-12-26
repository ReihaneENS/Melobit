package com.example.melobit.data;

import com.squareup.moshi.Json;

public class Audio{

	@Json(name = "high")
	private High high;

	@Json(name = "medium")
	private Medium medium;

	public High getHigh(){
		return high;
	}

	public Medium getMedium(){
		return medium;
	}
}