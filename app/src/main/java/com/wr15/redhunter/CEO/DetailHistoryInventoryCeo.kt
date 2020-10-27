package com.wr15.redhunter.CEO

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Develop.User.DetailHistoryUser
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MFile
import java.util.HashMap

class DetailHistoryInventoryCeo : AppCompatActivity() {

    lateinit var txt_nama_brg : TextView
    lateinit var txt_satuan_brg : TextView
    lateinit var txt_jmlh_brg : TextView
    lateinit var txt_harga_brg : TextView
    lateinit var txt_alasan : TextView
    lateinit var txt_divisi : TextView
    lateinit var btn_hapus_ceo : Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val URL_hapus =  ServerHost.url + "hapus_brg.php"




    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailHistoryUser::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_inventory_ceo)


        txt_nama_brg =  findViewById(R.id.txtnamadetailinventoryceo)
        txt_satuan_brg = findViewById(R.id.txtsatuandetailinventoryceo)
        txt_jmlh_brg =  findViewById(R.id.txtjmlhdetailinventoryceo)
        txt_harga_brg =  findViewById(R.id.txthargadetailinventoryceo)
//        txt_divisi = findViewById(R.id.txtdivisiinventoryceo)
        txt_alasan = findViewById(R.id.txtuseralasanceo)
        btn_hapus_ceo = findViewById(R.id.btnhapusinventoryceo)

        swipeRefreshLayout = findViewById(R.id.swipedetailhistoryinventoryceo)



        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        run {
            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected
            ) {
            } else {
                Toast.makeText(
                    applicationContext, " Silahkan Cek Lagi Koneksi Anda ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        val databrg = Gson().fromJson(getIntent().getStringExtra("data"), MFile::class.java)

                        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
                        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
                        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jmlh_brg)
//                        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
                        txt_alasan.setText("Alasan dihapus : " + databrg.alasan_dihapus)
//                        txt_divisi.setText("Divisi : " + databrg.divisi)


                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })


        val databrg = Gson().fromJson(getIntent().getStringExtra("data"), MFile::class.java)

        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jmlh_brg)
//        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
        txt_alasan.setText("Alasan Dihapus : " + databrg.alasan_dihapus)
//        txt_divisi.setText("Divisi : " + databrg.divisi)


        if (databrg.getStatus().equals("done")) {
            btn_hapus_ceo.setVisibility(View.GONE)
            btn_hapus_ceo.isEnabled = false
            txt_alasan.setVisibility(View.GONE)

        }else if (databrg.getStatus().equals("hapus")){
            btn_hapus_ceo.setText("Konfirmasi Hapus Barang")
            btn_hapus_ceo.setBackgroundColor(Color.RED)
            btn_hapus_ceo.setTextColor(Color.WHITE)
            btn_hapus_ceo.isEnabled = true

            btn_hapus_ceo.setOnClickListener {

                val loading = ProgressDialog(this)
                loading.setCancelable(false)
                loading.setMessage("sedang menghapus...")
                loading.show()

//                val id_mitra_ac : String = Rak.grab("idmitraac")
                val id : String = databrg.id.toString()
                val stringRequest = object : StringRequest(
                    Request.Method.POST, URL_hapus,
                    Response.Listener<String> { response ->

                        Log.e(TAG, "hapus order Response: $response")

                        val intent = Intent(this, HomeCeoActivity::class.java)
                        startActivity(intent)

                        loading.dismiss()
                        Toast.makeText(
                            getApplicationContext(),
                            "pengajuan berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()

                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(volleyError: VolleyError) {
                            loading.dismiss()
                            Toast.makeText(
                                applicationContext,
                                volleyError.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params.put("id", id)
                        return params
                    }
                }

                //adding request to queue
                AppController.getInstance().addToRequestQueue(stringRequest)


            }




        }

    }



}

