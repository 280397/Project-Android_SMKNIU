package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("merk")
	private String merk;

	@SerializedName("id_lokasi")
	private String idLokasi;

	@SerializedName("sumber")
	private String sumber;

	@SerializedName("created")
	private String created;

	@SerializedName("dtl_lokasi")
	private String dtlLokasi;

	@SerializedName("tgl_masuk")
	private Object tglMasuk;

	@SerializedName("id_kondisi")
	private String idKondisi;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("model")
	private String model;

	@SerializedName("id")
	private String id;

	@SerializedName("barcode")
	private String barcode;

	@SerializedName("updated")
	private String updated;

	public String getMerk(){
		return merk;
	}

	public String getIdLokasi(){
		return idLokasi;
	}

	public String getSumber(){
		return sumber;
	}

	public String getCreated(){
		return created;
	}

	public String getDtlLokasi(){
		return dtlLokasi;
	}

	public Object getTglMasuk(){
		return tglMasuk;
	}

	public String getIdKondisi(){
		return idKondisi;
	}

	public String getGambar(){
		return gambar;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public String getModel(){
		return model;
	}

	public String getId(){
		return id;
	}

	public String getBarcode(){
		return barcode;
	}

	public String getUpdated(){
		return updated;
	}
}
