package com.example.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAdmin{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("data_admin")
	private List<DataAdminItem> dataAdmin;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public List<DataAdminItem> getDataAdmin(){
		return dataAdmin;
	}
}