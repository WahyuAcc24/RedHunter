package com.wr15.redhunter.presenter

import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MUser
import com.wr15.redhunter.model.MPengajuan
import com.wr15.redhunter.model.UserStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryUserPresenter(
    private val services: ApiServices,
    private val view: HistoryUserView
) {

    fun searchGoodUserBy(keyword: String) {
        services.searchGoodsUserBy(keyword)
            .enqueue(object : Callback<UserStatus<MUser>> {
                override fun onFailure(call: Call<UserStatus<MUser>>, t: Throwable) {
                    view.onSearchUserFail()
                }

                override fun onResponse(call: Call<UserStatus<MUser>>, response: Response<UserStatus<MUser>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListUser(it)
                        }
                    }
                }
            })
    }

    fun getGoodsUserList() {
        services.getUser()
            .enqueue(object : Callback<UserStatus<MUser>> {
                override fun onFailure(call: Call<UserStatus<MUser>>, t: Throwable) {
                    view.onGoodsListUserFail()
                }

                override fun onResponse(call: Call<UserStatus<MUser>>, response: Response<UserStatus<MUser>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListUser(it)
                        }
                    }
                }
            })
    }

    interface HistoryUserView {
        fun onGoodsListUser(result: UserStatus<MUser>)
        fun onGoodsListUserFail()
        fun onSearchUserFail()
    }
}