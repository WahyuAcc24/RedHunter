package com.wr15.redhunter.presenter

import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MFile
import io.isfaaghyth.rak.Rak
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryBarangUserPresenter(
    val services: ApiServices,
     val view: HistoryFileView
) {

    fun searchGoodInventoryBy(keyword: String) {
        services.searchGoodsInventoryBy(keyword)
            .enqueue(object : Callback<BrgStatus<MFile>> {
                override fun onFailure(call: Call<BrgStatus<MFile>>, t: Throwable) {
                    view.onSearchFileFail()
                }

                override fun onResponse(call: Call<BrgStatus<MFile>>, response: Response<BrgStatus<MFile>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListFile(it)
                        }
                    }
                }
            })
    }

    fun getGoodsListInventory() {
        services.getInventoryUser(Rak.grab("iduser"))
            .enqueue(object : Callback<BrgStatus<MFile>> {
                override fun onFailure(call: Call<BrgStatus<MFile>>, t: Throwable) {
                    view.onGoodsListFileFail()
                }

                override fun onResponse(call: Call<BrgStatus<MFile>>, response: Response<BrgStatus<MFile>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            view.onGoodsListFile(it)
                        }
                    }
                }
            })
    }

    interface HistoryFileView {
        fun onGoodsListFile(result: BrgStatus<MFile>)
        fun onGoodsListFileFail()
        fun onSearchFileFail()
    }
}