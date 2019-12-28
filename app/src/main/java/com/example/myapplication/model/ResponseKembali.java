package com.example.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKembali{

	@SerializedName("data_kembali")
	private List<DataKembaliItem> dataKembali;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataKembaliItem> getDataKembali(){
		return dataKembali;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}