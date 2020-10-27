package com.wr15.redhunter.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrgStatus<N> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_barang") private List<N> data_barang;

    public boolean isStatus() {
        return status;
    }

    public List<N> getDataBarang() {
        return data_barang;
    }
}
