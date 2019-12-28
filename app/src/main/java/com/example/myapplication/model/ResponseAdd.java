package com.example.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAdd{

	@SerializedName("data_add")
	private List<DataAddItem> dataAdd;

	@SerializedName("data_list")
	private List<DataAddItem> dataList;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataAddItem> getDataAdd(){
		return dataAdd;
	}

	public List<DataAddItem> getDataList(){
		return dataList;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}