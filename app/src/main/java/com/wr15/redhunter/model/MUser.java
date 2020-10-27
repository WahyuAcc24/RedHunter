package com.wr15.redhunter.model;

import com.google.gson.annotations.SerializedName;

public class MUser {

    @SerializedName("id")
    private String id;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    @SerializedName("divisi")
    private String divisi;

    @SerializedName("password")
    private String password;


    public String getId() {
        return id;
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

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
