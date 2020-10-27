package com.wr15.redhunter.Retrofit

import com.google.gson.annotations.SerializedName

data class DataBarang (
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data_barang")
    val dataBarang: List<DataBarangData>
)

data class DataBarangData(
    @SerializedName("id")
    val id: String,
    @SerializedName("id_user")
    val idUser: String,
    @SerializedName("nama_brg")
    val namaBarang: String,
    @SerializedName("satuan_brg")
    val satuanBarang: String,
    @SerializedName("harga_brg")
    val hargaBarang: String
)

data class ServerResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
//    @SerializedName("nama_brg")
//    val nama_brg: String,
//    @SerializedName("satuan_brg")
//    val satuan_brg: String,
//    @SerializedName("harga_brg")
//    val harga_brg: String,
//    @SerializedName("jmlh_brg")
//    val jmlh_brg: String,
//    @SerializedName("nama_user")
//    val nama_user: String,
//    @SerializedName("divisi")
//    val divisi: String,
//    @SerializedName("images")
//    val images: String,
//    @SerializedName("status")
//    val status: String


)