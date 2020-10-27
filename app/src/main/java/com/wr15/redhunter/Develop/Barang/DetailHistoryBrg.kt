package com.wr15.redhunter.Develop.Barang

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.Develop.User.DetailHistoryUser
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MFile
import java.util.HashMap

class DetailHistoryBrg : AppCompatActivity() {

    lateinit var txt_nama_brg : TextView
    lateinit var txt_satuan_brg : TextView
    lateinit var txt_jmlh_brg : TextView
    lateinit var txt_harga_brg : TextView
    private lateinit var btn_ubah : Button
    private lateinit var btn_hapus : Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val URL_brg = ServerHost.url + "hapus_brg.php"


    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailHistoryUser::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_brgdev_main)


        txt_nama_brg =  findViewById(R.id.txtnamabrgdetail)
        txt_satuan_brg = findViewById(R.id.txtsatuanbrgdetail)
        txt_jmlh_brg =  findViewById(R.id.txtjmlhbrgdetail)
        txt_harga_brg =  findViewById(R.id.txthargabrgdetail)
        btn_hapus = findViewById(R.id.btnhapusdatabrg)
        btn_ubah = findViewById(R.id.btnubahdatabrg)
        swipeRefreshLayout = findViewById(R.id.swipedetailhistorybrg)


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
                        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })


        val databrg = Gson().fromJson(getIntent().getStringExtra("data"), MFile::class.java)

        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jmlh_brg)
        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)


        btn_ubah.setOnClickListener {

        val intent = Intent(this,UpdateBarangActivity::class.java)
        intent.putExtra("dataupdatebarang",Gson().toJson(databrg))
        startActivity(intent)

        }

        btn_hapus.setOnClickListener {

            val id_brg = databrg.id.toString()

            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected
            ) {
                hapusBrg(id_brg)
            } else {
                Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                    .show()
            }

        }


    }

    fun hapusBrg(id_brg:String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Menghapus data...")
        loading.show()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_brg,
            Response.Listener<String> { response ->

                Log.e(TAG, "Mengubah data Response: $response")

                loading.dismiss()

                Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@DetailHistoryBrg,
                    HomeActivity::class.java)
                startActivity(intent)
                finish()
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id",id_brg)

                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)



    }



}

