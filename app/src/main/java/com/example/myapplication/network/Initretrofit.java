package com.example.myapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Initretrofit {

    private static final String URL = "http://192.168.1.14/Project-SMKNIU/";
    private static final String BASE_URL_IMAGE = URL + "assets/img/barang/";
    private static final String BASE_URL_KTP = URL + "asset/foto_ktp/";



    public static Retrofit initRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiInterface getInstance() {
        return initRetrofit().create(ApiInterface.class);
    }

    public static String getIMG_URL() {
        String IMAGE = "";
        IMAGE = BASE_URL_IMAGE;
       return IMAGE;

    }

    public static String getKtp() {
        String KTP = "";
        KTP = BASE_URL_KTP;
        return KTP;
    }
}


