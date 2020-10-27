package com.wr15.redhunter.CEO

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
import com.wr15.redhunter.Adapter.HistoryUserAdapter
import com.wr15.redhunter.Adapter.ItemClickListener
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MUser
import com.wr15.redhunter.model.UserStatus
import java.lang.Exception
import java.lang.reflect.Type

class FragmentHistoryUserCeoActivity : Fragment() {


    private lateinit var lstHistoriuser : RecyclerView

    var urluser  = ServerHost.url + "list_userdev.php"

    private lateinit var pglistuser : ProgressBar

    var muser : List<MUser>? = null
    private var adapterCek : HistoryUserAdapter? = null

    private var requesQueue : RequestQueue? = null

    private var gson : Gson? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.historyuserceo_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("TAG", urluser)


        lstHistoriuser = view.findViewById(R.id.rvListuserceo) as RecyclerView

        pglistuser = view.findViewById(R.id.progressBaruserceo) as ProgressBar

        swipeRefreshLayout = view.findViewById(R.id.swipehistoryuserceo)

        requesQueue = Volley.newRequestQueue(context)

        var gsonBuilder : GsonBuilder = GsonBuilder()

        gson = gsonBuilder.create()

        lstHistoriuser.setLayoutManager(LinearLayoutManager(context) as RecyclerView.LayoutManager?)

        muser = ArrayList()

        ambilListUser()
        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)



        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        ambilListUser()
                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })


    }
//    companion object {
//        fun newInstance(): FragmentHistoryUserActivity {
//            val fragment = FragmentHistoryUserActivity()
//            val args = Bundle()
//            fragment.arguments = args
//
//            return fragment
//        }
//    }

    fun ambilListUser(){
        val request = StringRequest(Request.Method.GET, urluser, onPostsLoaded, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

                var inetErr: AlertDialog.Builder = AlertDialog.Builder(context)
                inetErr.setTitle("Terjadi Kesalahan")
                inetErr.setMessage("Periksa Kembali Koneksi Internet Anda")
                inetErr.setNegativeButton("Muat Ulang",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        ambilListUser()

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
            var collectionType: Type = object: TypeToken<UserStatus<MUser>>(){}.type
            var orderCek: UserStatus<MUser>? = Gson().fromJson(response, collectionType) as? UserStatus<MUser>

            if (orderCek!!.isStatus){
                try {
                    pglistuser = view?.findViewById(R.id.progressBaruserceo) as ProgressBar
                    pglistuser.setVisibility(View.GONE)

                    adapterCek = HistoryUserAdapter(orderCek.dataUser)

                    adapterCek?.setListener(object: ItemClickListener<MUser> {
                        override fun onClicked(User: MUser?, position: Int, view: View?) {

                            val intent = Intent(context, DetailHistoryUserCeo::class.java)
                            intent.putExtra("data",Gson().toJson(User))
                            startActivity(intent)

                        }
                    })
                    lstHistoriuser.adapter = adapterCek
                }catch (ignored : Exception){

                }
            }else{
                pglistuser.setVisibility(View.GONE)
                Toast.makeText(context,"Tidak Ada Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }
