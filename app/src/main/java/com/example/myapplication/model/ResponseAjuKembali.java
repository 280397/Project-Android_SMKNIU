package com.example.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAjuKembali{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	@SerializedName("data_aju_kembali")
	private List<DataAjuKembaliItem> dataAjuKembali;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public List<DataAjuKembaliItem> getDataAjuKembali(){
		return dataAjuKembali;
	}
}