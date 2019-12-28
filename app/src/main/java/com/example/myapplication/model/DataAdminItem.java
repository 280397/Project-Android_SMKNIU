package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataAdminItem{

	@SerializedName("image")
	private String image;

	@SerializedName("password")
	private String password;

	@SerializedName("is_active")
	private String isActive;

	@SerializedName("role_id")
	private String roleId;

	@SerializedName("date_created")
	private String dateCreated;

	@SerializedName("id_admin")
	private String idAdmin;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("username")
	private String username;

	public String getImage(){
		return image;
	}

	public String getPassword(){
		return password;
	}

	public String getIsActive(){
		return isActive;
	}

	public String getRoleId(){
		return roleId;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public String getIdAdmin(){
		return idAdmin;
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