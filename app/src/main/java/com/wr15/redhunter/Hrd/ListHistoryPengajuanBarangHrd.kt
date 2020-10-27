package com.wr15.redhunter.Hrd

import android.app.AlertDialog
import android.app.ProgressDialog
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
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.wr15.redhunter.Adapter.HistoryBrgPengajuanHrdAdapter
import com.wr15.redhunter.Adapter.ItemClickListener
import com.wr15.redhunter.AppController
import com.wr15.redhunter.AppController.TAG
import com.wr15.redhunter.Develop.FragmentHomeActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MPengajuan
import kotlinx.android.synthetic.main.logindev_main.*
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.Type
import java.util.HashMap

class ListHistoryPengajuanBarangHrd : Fragment(),SearchView.OnQueryTextListener{


    private lateinit var lstHistoribrg : RecyclerView

    var urlbrg : String = ServerHost.url + "list_pengajuan_brg_hrd.php"
    var url_search : String = ServerHost.url + "search_data.php"

    private lateinit var pglistbrg : ProgressBar

    var mpengajuan : List<MPengajuan>? = null
    private var adapterpengajuan : HistoryBrgPengajuanHrdAdapter? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.historypengajuan_barang_hrd, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

//        Log.d("TAG", urlbrg)


        lstHistoribrg = view.findViewById(R.id.rvListpbrghrd) as RecyclerView

        pglistbrg = view.findViewById(R.id.progressBarpbrghrd) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipehistorypbrghrd)

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistoribrg.setLayoutManager(LinearLayoutManager(context) as RecyclerView.LayoutManager?)

        mpengajuan = ArrayList()

        ambilListBrg()
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)



        swipeRefreshLayout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        ambilListBrg()
                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 3000)
            }
        })



    }
    companion object {
        fun newInstance(): FragmentHomeActivity {
            val fragment = FragmentHomeActivity()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

    fun ambilListBrg(){

        val request = StringRequest(Request.Method.GET, urlbrg, onPostsLoaded, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(context)
                inetErr.setTitle("Terjadi Kesalahan")
                inetErr.setMessage("Periksa Kembali Koneksi Internet Anda")
                inetErr.setNegativeButton("Muat Ulang",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        ambilListBrg()

                    }

                })
                inetErr.show()
            }
        })
        requesQueue?.add(request)
    }

    val onPostsLoaded = object: Response.Listener<String> {
        override fun onResponse(response:String) {
            Log.e("TAG", response)
            var collectionType: Type = object: TypeToken<BrgStatus<MPengajuan>>(){}.type
            var orderCek: BrgStatus<MPengajuan>? = Gson().fromJson(response, collectionType) as? BrgStatus<MPengajuan>

            if (orderCek!!.isStatus){
                try {
                    pglistbrg = view?.findViewById(R.id.progressBarpbrghrd) as ProgressBar
                    pglistbrg.setVisibility(View.GONE)

                    adapterpengajuan = HistoryBrgPengajuanHrdAdapter(orderCek.dataBarang)

                    adapterpengajuan!!.setListener(object: ItemClickListener<MPengajuan> {
                        override fun onClicked(Barang: MPengajuan?, position: Int, view: View?) {

                            val intent = Intent(context, DetailHrdPengajuanBarang::class.java)
                            intent.putExtra("datapengajuan", Gson().toJson(Barang))
                            startActivity(intent)

                        }
                    })
                    lstHistoribrg.adapter = adapterpengajuan
                }catch (ignored : Exception){

                }
            }else{
                pglistbrg.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }

        }

    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu_search, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView
        searchView.setIconified(true)


//        MenuItemCompat.setShowAsAction(item,MenuItemCompat.SHOW_AS_ACTION_ALWAYS)
        val searchManager = getActivity()?.getSystemService(Context.SEARCH_SERVICE) as SearchManager


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity()?.getComponentName()))

            searchView.setOnQueryTextListener(this)

//        searchView.setQueryHint("Cari....")
        return super.onCreateOptionsMenu(menu,inflater)

    }

    override fun onQueryTextSubmit(query: String): Boolean {

        cariData(query)

        return false

    }

    override fun onQueryTextChange(newText: String): Boolean {

        return false
    }

    private fun cariData(keyword:String){

        val loading = ProgressDialog(context)
        loading.setCancelable(false)
        loading.setMessage("Mohon Tunggu...")
        loading.show()



        val stringRequest = object : StringRequest(
            Request.Method.POST, url_search,
            Response.Listener<String> { response ->

                Log.e(TAG, "Menambahkan data Response: $response")

                loading.dismiss()

                adapterpengajuan?.notifyDataSetChanged()
                lstHistoribrg.adapter = adapterpengajuan

            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading.dismiss()
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("keyword", keyword)
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
    }



//    override fun onResume() {
//        super.onResume()
//    }


}

