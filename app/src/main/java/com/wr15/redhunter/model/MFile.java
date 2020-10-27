package com.wr15.redhunter.model;

import com.google.gson.annotations.SerializedName;

public class MFile {

    @SerializedName("id")
    private String id;

    @SerializedName("nama_brg")
    private String nama_brg;

    @SerializedName("satuan_brg")
    private String satuan_brg;

    @SerializedName("harga_brg")
    private String harga_brg;

    @SerializedName("jmlh_brg")
    private String jmlh_brg;

    @SerializedName("nama_user")
    private String nama_user;


    @SerializedName("divisi")
    private String divisi;

    @SerializedName("status")
    private String status;

    @SerializedName("alasan_dihapus")
    private String alasan_dihapus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getJmlh_brg() {
        return jmlh_brg;
    }

    public void setJmlh_brg(String jmlh_brg) {
        this.jmlh_brg = jmlh_brg;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlasan_dihapus() {
        return alasan_dihapus;
    }

    public void setAlasan_dihapus(String alasan_dihapus) {
        this.alasan_dihapus = alasan_dihapus;
    }
}
