package com.nandaadisaputra.projectakhir.network

import com.nandaadisaputra.projectakhir.model.ProductModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.ArrayList

interface ApiService {

    @GET("api/barang")
    //mengambil data dari api/barang
    fun getData(): Call<ArrayList<ProductModel>>
    //metode ambilData memanggil ArrayList dari ProdukModel

    @FormUrlEncoded
    @POST("api/login/app")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/barang/simpan")
    fun addData(
        @Field("nama_barang") nama_barang: String,
        @Field("image_barang") image_barang: String,
        @Field("deskripsi_barang") deskripsi_barang: String,
        @Field("harga_barang") harga_barang: String,
        @Field("stok_barang") stok_barang: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/barang/delete")
    fun deleteData(@Field("id_barang") id: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/barang/edit")
     fun beliData(
        @Field("id_barang") id_barang: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/barang/edit")
    fun updateData(
        @Field("id_barang") id_barang: String,
        @Field("nama_barang") nama_barang: String,
        @Field("image_barang") image_barang: String,
        @Field("deskripsi_barang") deskripsi_barang: String,
        @Field("harga_barang") harga_barang: String,
        @Field("stok_barang") stok_barang: String
    ): Call<ResponseBody>


}