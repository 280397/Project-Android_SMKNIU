package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataAjuKembaliItem{

	@SerializedName("id_user_pjm")
	private String idUserPjm;

	@SerializedName("kode")
	private String kode;

	@SerializedName("name")
	private String name;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id")
	private String id;

	@SerializedName("barcode")
	private String barcode;

	public String getIdUserPjm(){
		return idUserPjm;
	}

	public String getKode(){
		return kode;
	}

	public String getName(){
		return name;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public String getId(){
		return id;
	}

	public String getBarcode(){
		return barcode;
	}
}