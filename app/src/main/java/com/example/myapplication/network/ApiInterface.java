package com.example.myapplication.network;

import com.example.myapplication.model.ResponseAdd;
import com.example.myapplication.model.ResponseAdmin;
import com.example.myapplication.model.ResponseAjuKembali;
import com.example.myapplication.model.ResponseData;
import com.example.myapplication.model.ResponseKembali;
import com.example.myapplication.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("api/auth")
    Call<ResponseLogin> getUser(@Field("username") String username,
                                @Field("password") String password
    );
//    halaman home
    @GET("api/barang")
    Call<ResponseData> getDatabarang();

    @GET("api/barang")
    Call<ResponseData> getSearch(
    );

//    peminjaman
//    scan barcode
    @GET("api/pinjam")
    Call<ResponseData> getPinjam(
            @Query("barcode") String barcode
            );

//    menampilkan pengajuan peminjaman barang
    @GET("api/pinjam/ambil")
    Call<ResponseAdd> getAddList(
            @Query("id_user_pjm") String id_user_pjm
    );

//    delete barang yang diajukan
    @DELETE("api/pinjam/{id}")
    Call<ResponseAdd> delAddList(
            @Path("id") String id
    );

//    post aju pinjam
    @FormUrlEncoded
    @POST("api/pinjam")
    Call<ResponseData> postPinjamSe(
        @Field("id_user_pjm") String id_user_pjm,
        @Field("barcode") String barcode
    );

//    scan barcode admin
    @GET("api/Aju_pinjam")
    Call<ResponseAdmin> getAdmin(
            @Query("id_admin") String id_admin
    );

//post ajukan peminjaman
    @FormUrlEncoded
    @POST("api/Aju_pinjam/{id_user_pjm}")
    Call<ResponseAdd> putData(
            @Path("id_user_pjm") String id_user_pjm,
//            @Field("kode") String kode,
//            @Field("id_user_pjm") String id_user_pjm,
//            @Field("tgl_pinjam") String tgl_pinjam,
            @Field("tgl_aju_kembali") String tgl_aju_kembali,
            @Field("id_admin") String id_admin,
            @Field("keperluan") String keperluan
    );

//    list daftar pinjaman
    @GET("api/pinjam/list")
    Call<ResponseAdd> getList(
            @Query("id_user_pjm") String id_user_pjm
    );


//    pengembalian
    @GET("api/kembali")
    Call<ResponseKembali> getDatapinjam(
            @Query("barcode") String barcode
    );

//    get data list ajukan pengembalian
    @GET("api/kembali/ambil")
    Call<ResponseAjuKembali> getfinishList(
        @Query("id_user_pjm") String id_user_pjm
        );
    //    delete barang yang diajukan
    @DELETE("api/kembali/{id}")
    Call<ResponseAjuKembali> delFinishList(
            @Path("id") String id
    );

    //    post aju pinjam
    @FormUrlEncoded
    @POST("api/kembali")
    Call<ResponseKembali> postKembaliSe(
            @Field("id_user_pjm") String id_user_pjm,
            @Field("kode") String kode,
            @Field("barcode") String barcode
    );

    //post ajukan pemngembalian
    @FormUrlEncoded
    @POST("api/Aju_kembali/{id_user_pjm}")
    Call<ResponseAjuKembali> postDataKembali(
            @Path("id_user_pjm") String id_user_pjm,
//            @Field("kode") String kode,
//            @Field("id_user_pjm") String id_user_pjm,
//            @Field("tgl_pinjam") String tgl_pinjam,
            @Field("id_admin") String id_admin
    );

}
