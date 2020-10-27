package com.wr15.redhunter.presenter

import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MPengajuan
import io.isfaaghyth.rak.Rak
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryPengajuanUserPresenter(
    private val services: ApiServices,
    private val view: HistoryPengajuanView
) {

    fun searchGoodBy(keyword: String) {
        services.searchGoodsBy(keyword)
            .enqueue(object : Callback<BrgStatus<MPengajuan>> {
                override fun onFailure(call: Call<BrgStatus<MPengajuan>>, t: Throwable) {
                    view.onSearchFail()
                }

                override fun onResponse(call: Call<BrgStatus<MPengajuan>>, response: Response<BrgStatus<MPengajuan>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsList(it)
                        }
                    }
                }
            })
    }

    fun getGoodsList() {
        services.getBarangUser(Rak.grab("iduser"))
            .enqueue(object : Callback<BrgStatus<MPengajuan>> {
                override fun onFailure(call: Call<BrgStatus<MPengajuan>>, t: Throwable) {
                    view.onGoodsListFail()
                }

                override fun onResponse(call: Call<BrgStatus<MPengajuan>>, response: Response<BrgStatus<MPengajuan>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsList(it)
                        }
                    }
                }
            })
    }

    interface HistoryPengajuanView {
        fun onGoodsList(result: BrgStatus<MPengajuan>)
        fun onGoodsListFail()
        fun onSearchFail()
    }
}