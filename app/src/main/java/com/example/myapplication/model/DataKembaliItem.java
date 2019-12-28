package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataKembaliItem{

	@SerializedName("tgl_aju_kembali")
	private String tglAjuKembali;

	@SerializedName("keperluan")
	private String keperluan;

	@SerializedName("id_user_pjm")
	private String idUserPjm;

	@SerializedName("kode")
	private String kode;

	@SerializedName("tgl_kembali")
	private String tglKembali;

	@SerializedName("id_admin")
	private String idAdmin;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id")
	private String id;

	@SerializedName("barcode")
	private String barcode;

	@SerializedName("tgl_pinjam")
	private String tglPinjam;

	@SerializedName("status")
	private String status;

	public String getTglAjuKembali(){
		return tglAjuKembali;
	}

	public String getKeperluan(){
		return keperluan;
	}

	public String getIdUserPjm(){
		return idUserPjm;
	}

	public String getKode(){
		return kode;
	}

	public String getTglKembali(){
		return tglKembali;
	}

	public String getIdAdmin(){
		return idAdmin;
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

	public String getTglPinjam(){
		return tglPinjam;
	}

	public String getStatus(){
		return status;
	}
}