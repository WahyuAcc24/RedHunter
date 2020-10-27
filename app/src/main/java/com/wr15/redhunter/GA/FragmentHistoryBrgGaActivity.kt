package com.wr15.redhunter.GA

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
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
import com.wr15.redhunter.Adapter.ItemClickListener
import com.wr15.redhunter.Develop.Barang.DetailHistoryBrg
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MFile
import java.lang.Exception
import java.lang.reflect.Type

class FragmentHistoryBrgGaActivity : Fragment() {


    private lateinit var lstHistoribrg : RecyclerView

    var urlbrg = ServerHost.url + "list_brgdev.php"

    private lateinit var pglistbrg : ProgressBar

    var mfile : List<MFile>? = null
    private var adapterfile : HistoryFileAdapter? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.historybrgdev_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        Log.d("TAG", urlbrg)


        lstHistoribrg = view.findViewById(R.id.rvListbrg) as RecyclerView

        pglistbrg = view.findViewById(R.id.progressBarbrg) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipehistorybrg)

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistoribrg.setLayoutManager(LinearLayoutManager(context) as RecyclerView.LayoutManager?)

        mfile = ArrayList()

        ambilListBrg()
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)



        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        ambilListBrg()
                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })



    }
//    companion object {
//        fun newInstance(): FragmentHomeActivity {
//            val fragment = FragmentHomeActivity()
//            val args = Bundle()
//            fragment.arguments = args
//
//            return fragment
//        }
//    }

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
            var collectionType: Type = object: TypeToken<BrgStatus<MFile>>(){}.type
            var orderCek: BrgStatus<MFile>? = Gson().fromJson(response, collectionType) as? BrgStatus<MFile>

            if (orderCek!!.isStatus){
                try {
                    pglistbrg = view?.findViewById(R.id.progressBarbrg) as ProgressBar
                    pglistbrg.setVisibility(View.GONE)

                    adapterfile = HistoryFileAdapter(orderCek.dataBarang)

                    adapterfile!!.setListener(object: ItemClickListener<MFile> {
                        override fun onClicked(Barang: MFile?, position: Int, view: View?) {

                            val intent = Intent(context, DetailHistoryBrg::class.java)
                            intent.putExtra("data",Gson().toJson(Barang))
                            startActivity(intent)

                        }
                    })
                    lstHistoribrg.adapter = adapterfile
                }catch (ignored : Exception){

                }
            }else{
                pglistbrg.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }

        }

}