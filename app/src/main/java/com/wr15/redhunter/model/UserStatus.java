package com.wr15.redhunter.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserStatus<N> {
    @SerializedName("status") private boolean status;
    @SerializedName("data_user") private List<N> data_user;

    public boolean isStatus() {
        return status;
    }

    public List<N> getDataUser() {
        return data_user;
    }
}
