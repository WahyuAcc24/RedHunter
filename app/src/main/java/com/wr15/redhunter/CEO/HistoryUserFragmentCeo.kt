package com.wr15.redhunter.CEO

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
import com.wr15.redhunter.Adapter.HistoryUserAdapter
import com.wr15.redhunter.Develop.User.DetailHistoryUser
import com.wr15.redhunter.R
import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.Retrofit.RetrofitBuilder
import com.wr15.redhunter.model.*
import com.wr15.redhunter.presenter.HistoryPengajuanPresenter
import com.wr15.redhunter.presenter.HistoryUserPresenter
import kotlinx.android.synthetic.main.historypengajuan_barang_hrd.*
import kotlinx.android.synthetic.main.historyuserceo_main.*
import kotlinx.android.synthetic.main.historyuserdev_main.*

class HistoryUserFragmentCeo: Fragment(),
    HistoryUserPresenter.HistoryUserView,
    SearchView.OnQueryTextListener {

    private val apiServices by lazy { RetrofitBuilder.builder().create(ApiServices::class.java) }
    private val presenter by lazy { HistoryUserPresenter(apiServices, this) }

    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         var theme : Context = ContextThemeWrapper(getActivity(),R.style.AppTheme)

        var localInflater : LayoutInflater = inflater.cloneInContext(theme)

        return localInflater.inflate(
            R.layout.historyuserceo_main,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListuserceo?.layoutManager = LinearLayoutManager(requireContext())
        progressBaruserceo?.visibility = View.VISIBLE
        setHasOptionsMenu(true)

        // get goods list
        presenter.getGoodsUserList()


        swipeRefreshLayout = view.findViewById(R.id.swipehistoryuserceo)
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        presenter.getGoodsUserList()

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
            presenter.getGoodsUserList()
            return false
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            presenter.getGoodsUserList()
            return false
        }

        if (query.length >= 2) {
            presenter.searchGoodUserBy(query)
        }

        return true
    }

    override fun onGoodsListUser(result: UserStatus<MUser>) {
        progressBaruserceo?.visibility = View.GONE

        if (result.isStatus) {
            val adapter = HistoryUserAdapter(result.dataUser)
            adapter.setListener { user, _, _ ->
                val intent = Intent(context, DetailHistoryUserCeo::class.java)
                intent.putExtra("data", Gson().toJson(user))
                startActivity(intent)
            }

            rvListuserceo?.adapter = adapter
        } else {
            Toast.makeText(
                requireContext(),
                "Data yang anda cari tidak ditemukan.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onGoodsListUserFail() {
        progressBaruserceo?.visibility = View.GONE

        AlertDialog.Builder(context).apply {
            setTitle("Terjadi Kesalahan")
            setMessage("Periksa Kembali Koneksi Internet Anda")
            setNegativeButton("Muat Ulang") { _, _ ->
                presenter.getGoodsUserList()
            }
        }.show()
    }

    override fun onSearchUserFail() {
        Toast.makeText(
            requireContext(),
            "Ops, gagal nyari.",
            Toast.LENGTH_SHORT
        ).show()
    }

}