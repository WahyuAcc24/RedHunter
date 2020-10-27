package com.wr15.redhunter.Retrofit

import com.wr15.redhunter.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @Multipart
    @POST("insert_brg.php")
    fun uploadGambar(
        @Part namaBarang: MultipartBody.Part,
        @Part satuanBarang: MultipartBody.Part,
        @Part hargaBarang: MultipartBody.Part,
        @Part jumlahBarang: MultipartBody.Part,
        @Part status : MultipartBody.Part,
        @Part namaUser: MultipartBody.Part,
        @Part divisi : MultipartBody.Part,
        @Part file: MultipartBody.Part
    ): Call<ServerResponse>

    @GET("list_pengajuan_brg_hrd.php")
    fun getBarang(): Call<BrgStatus<MPengajuan>>

    @GET("list_pengajuan_brg.php")
    fun getBarangUser(@Query("id_user") id_user: String): Call<BrgStatus<MPengajuan>>

    @GET("search_data.php")
    fun searchGoodsBy(
        @Query("keyword") keyword: String
    ): Call<BrgStatus<MPengajuan>>


    @GET("list_userdev.php")
    fun getUser(): Call<UserStatus<MUser>>

    @GET("search_user.php")
    fun searchGoodsUserBy(
        @Query("keyword") keyword: String
    ): Call<UserStatus<MUser>>


    @GET("list_inventory_brg.php")
    fun getInventoryUser(@Query("id_user") id_user: String): Call<BrgStatus<MFile>>

    @GET("list_brgdev.php")
    fun getInventory(): Call<BrgStatus<MFile>>


    @GET("search_inventory.php")
    fun searchGoodsInventoryBy(
        @Query("keyword") keyword: String
    ): Call<BrgStatus<MFile>>


}