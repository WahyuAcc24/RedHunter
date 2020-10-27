package com.wr15.redhunter.Hrd

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.wr15.redhunter.Adapter.HistoryBrgPengajuanHrdAdapter
import com.wr15.redhunter.R
import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.Retrofit.RetrofitBuilder
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MPengajuan
import com.wr15.redhunter.presenter.HistoryPengajuanPresenter
import kotlinx.android.synthetic.main.historypengajuan_barang_hrd.*

class HistoryPengajuanBarangFragmentHrd: Fragment(),
    HistoryPengajuanPresenter.HistoryPengajuanView,
    SearchView.OnQueryTextListener {

    private val apiServices by lazy { RetrofitBuilder.builder().create(ApiServices::class.java) }
    private val presenter by lazy { HistoryPengajuanPresenter(apiServices, this) }

    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         var theme : Context = ContextThemeWrapper(getActivity(),R.style.AppTheme)

        var localInflater : LayoutInflater = inflater.cloneInContext(theme)

        return localInflater.inflate(
            R.layout.historypengajuan_barang_hrd,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListpbrghrd?.layoutManager = LinearLayoutManager(requireContext())
        progressBarpbrghrd?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        // get goods list
        presenter.getGoodsList()


        swipeRefreshLayout = view.findViewById(R.id.swipehistorypbrghrd)
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        presenter.getGoodsList()

                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 3000)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main_menu_search, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        val searchManager = activity?.getSystemService(
            Context.SEARCH_SERVICE
        ) as SearchManager

        searchView.isIconified = true

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            presenter.getGoodsList()
            return false
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            presenter.getGoodsList()
            return false
        }

        if (query.length >= 2) {
            presenter.searchGoodBy(query)
        }

        return true
    }

    override fun onGoodsList(result: BrgStatus<MPengajuan>) {
        progressBarpbrghrd?.visibility = View.GONE

        if (result.isStatus) {
            val adapter = HistoryBrgPengajuanHrdAdapter(result.dataBarang)
            adapter.setListener { barang, _, _ ->
                val intent = Intent(context, DetailHrdPengajuanBarang::class.java)
                intent.putExtra("datapengajuan", Gson().toJson(barang))
                startActivity(intent)
            }

            rvListpbrghrd?.adapter = adapter
        } else {
            Toast.makeText(
                requireContext(),
                "Data yang anda cari tidak ditemukan.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onGoodsListFail() {
        progressBarpbrghrd?.visibility = View.GONE

        AlertDialog.Builder(context).apply {
            setTitle("Terjadi Kesalahan")
            setMessage("Periksa Kembali Koneksi Internet Anda")
            setNegativeButton("Muat Ulang") { _, _ ->
                presenter.getGoodsList()
            }
        }.show()
    }

    override fun onSearchFail() {
        Toast.makeText(
            requireContext(),
            "Ops, gagal nyari.",
            Toast.LENGTH_SHORT
        ).show()
    }

}