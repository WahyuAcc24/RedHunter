package com.wr15.redhunter.Retrofit

import com.wr15.redhunter.Server.ServerHost
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun builder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ServerHost.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



}