package com.example.myapplication.model;


import com.google.gson.annotations.SerializedName;


public class Data{

	@SerializedName("password")
	private String password;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getUsername(){
		return username;
	}
}