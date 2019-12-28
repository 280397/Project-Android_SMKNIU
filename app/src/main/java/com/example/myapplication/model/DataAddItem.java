package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;


public class DataAddItem{

	@SerializedName("keperluan")
	private String keperluan;

	@SerializedName("id_user_pjm")
	private String idUserPjm;

	@SerializedName("kode")
	private String kode;

	@SerializedName("tgl_kembali")
	private String tglKembali;

	@SerializedName("tgl_aju_kembali")
	private String tglAjuKembali;

	@SerializedName("name")
	private String name;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id")
	private String id;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("barcode")
	private String barcode;

	@SerializedName("tgl_pinjam")
	private String tglPinjam;

	@SerializedName("status")
	private String status;

	public String getKeperluan(){
		return keperluan;
	}

	public String getIdUserPjm(){
		return idUserPjm;
	}

	public String getKode(){
		return kode;
	}

	public String getTglAjuKembali(){
		return tglAjuKembali;
	}

	public String getTglKembali(){
		return tglKembali;
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

	public String getIdUser(){
		return idUser;
	}

	public String getBarcode(){
		return barcode;
	}

	public String getTglPinjam(){
		return tglPinjam;
	}

	public String getStatus(){
		return status;
	}
}