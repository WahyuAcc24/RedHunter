package com.wr15.redhunter.model;

import com.google.gson.annotations.SerializedName;

public class MPengajuan {


    @SerializedName("id")
    private String id;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("divisi")
    private String divisi;


    @SerializedName("nama_brg")
    private String nama_brg;

    @SerializedName("satuan_brg")
    private String satuan_brg;

    @SerializedName("harga_brg")
    private String harga_brg;

    @SerializedName("jumlah_brg")
    private String jumlah_brg;

    @SerializedName("alasan")
    private String alasan;

    @SerializedName("status")
    private String status;

    @SerializedName("tanggal")
    private String tanggal;


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_brg() {
        return nama_brg;
    }

    public void setNama_brg(String nama_brg) {
        this.nama_brg = nama_brg;
    }

    public String getSatuan_brg() {
        return satuan_brg;
    }

    public void setSatuan_brg(String satuan_brg) {
        this.satuan_brg = satuan_brg;
    }

    public String getHarga_brg() {
        return harga_brg;
    }

    public void setHarga_brg(String harga_brg) {
        this.harga_brg = harga_brg;
    }

    public String getJumlah_brg() {
        return jumlah_brg;
    }

    public void setJumlah_brg(String jumlah_brg) {
        this.jumlah_brg = jumlah_brg;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
