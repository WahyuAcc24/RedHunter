package com.wr15.redhunter.Develop.Barang

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wr15.redhunter.Adapter.HistoryFileAdapter
import com.wr15.redhunter.Adapter.HistoryInventoryAdapter
import com.wr15.redhunter.Adapter.ItemClickListener
import com.wr15.redhunter.GA.DetailHistoryBrgGa
import com.wr15.redhunter.R
import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.Retrofit.RetrofitBuilder
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MFile
import com.wr15.redhunter.presenter.HistoryBarangPresenter
import kotlinx.android.synthetic.main.history_inventory.*
import java.lang.Exception
import java.lang.reflect.Type

class FragmentHistoryBrgActivity : Fragment() ,
    HistoryBarangPresenter.HistoryFileView,
    SearchView.OnQueryTextListener {

    private val apiServices by lazy { RetrofitBuilder.builder().create(ApiServices::class.java) }
    private val presenter by lazy { HistoryBarangPresenter(apiServices, this) }

    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var theme : Context = ContextThemeWrapper(getActivity(),R.style.AppTheme)

        var localInflater : LayoutInflater = inflater.cloneInContext(theme)

        return localInflater.inflate(
            R.layout.history_inventory,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListinventory?.layoutManager = LinearLayoutManager(requireContext())
        progressBarinventory?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        // get goods list
        presenter.getGoodsListInventory()


        swipeRefreshLayout = view.findViewById(R.id.swipehistoryinventory)
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        presenter.getGoodsListInventory()

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
            presenter.getGoodsListInventory()
            return false
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            presenter.getGoodsListInventory()
            return false
        }

        if (query.length >= 2) {
            presenter.searchGoodInventoryBy(query)
        }

        return true
    }

    override fun onGoodsListFile(result: BrgStatus<MFile>) {
        progressBarinventory?.visibility = View.GONE

        if (result.isStatus) {
            val adapter = HistoryInventoryAdapter(result.dataBarang)
            adapter.setListener { barang, _, _ ->
                val intent = Intent(context, DetailHistoryBrgGa::class.java)
                intent.putExtra("data", Gson().toJson(barang))
                startActivity(intent)
            }

            rvListinventory?.adapter = adapter
        } else {
            Toast.makeText(
                requireContext(),
                "Data yang anda cari tidak ditemukan.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onGoodsListFileFail() {
        progressBarinventory?.visibility = View.GONE

        AlertDialog.Builder(context).apply {
            setTitle("Terjadi Kesalahan")
            setMessage("Periksa Kembali Koneksi Internet Anda")
            setNegativeButton("Muat Ulang") { _, _ ->
                presenter.getGoodsListInventory()
            }
        }.show()
    }

    override fun onSearchFileFail() {
        Toast.makeText(
            requireContext(),
            "Ops, gagal nyari.",
            Toast.LENGTH_SHORT
        ).show()
    }

}