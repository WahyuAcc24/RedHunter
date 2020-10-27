package com.wr15.redhunter.presenter

import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MPengajuan
import io.isfaaghyth.rak.Rak
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryPengajuanGaPresenter(
    private val services: ApiServices,
    private val view: HistoryPengajuanView
) {

    fun searchGoodByGa(keyword: String) {
        services.searchGoodsBy(keyword)
            .enqueue(object : Callback<BrgStatus<MPengajuan>> {
                override fun onFailure(call: Call<BrgStatus<MPengajuan>>, t: Throwable) {
                    view.onSearchFailGa()
                }

                override fun onResponse(call: Call<BrgStatus<MPengajuan>>, response: Response<BrgStatus<MPengajuan>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListGa(it)
                        }
                    }
                }
            })
    }

    fun getGoodsListGa() {
        services.getBarangUser(Rak.grab("idga"))
            .enqueue(object : Callback<BrgStatus<MPengajuan>> {
                override fun onFailure(call: Call<BrgStatus<MPengajuan>>, t: Throwable) {
                    view.onGoodsListFailGa()
                }

                override fun onResponse(call: Call<BrgStatus<MPengajuan>>, response: Response<BrgStatus<MPengajuan>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListGa(it)
                        }
                    }
                }
            })
    }

    interface HistoryPengajuanView {
        fun onGoodsListGa(result: BrgStatus<MPengajuan>)
        fun onGoodsListFailGa()
        fun onSearchFailGa()
    }
}