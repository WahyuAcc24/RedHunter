package com.wr15.redhunter.Karyawan

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MPengajuan

class DetailPengajuanBarang : AppCompatActivity(){

    private lateinit var txt_nama_brg : TextView
    private lateinit var txt_satuan_brg : TextView
    private lateinit var txt_jmlh_brg : TextView
    private lateinit var txt_harga_brg : TextView
    private lateinit var txt_alasan : TextView
    private lateinit var btn_ubah : Button
//    private lateinit var btn_hapus : Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    val URL_req_hapus =  ServerHost.url + "req_hapus_barang.php"

    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailPengajuanBarang::class.java.getSimpleName()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pengajuanbrg_main)

        txt_nama_brg = findViewById(R.id.txtnamapbrgdetail)
        txt_harga_brg = findViewById(R.id.txthargapbrgdetail)
        txt_jmlh_brg = findViewById(R.id.txtjmlhpbrgdetail)
        txt_satuan_brg = findViewById(R.id.txtsatuanpbrgdetail)
        txt_alasan = findViewById(R.id.txtalasanpengajuandetail)
        btn_ubah = findViewById(R.id.btnpengajuanbrg)
//        btn_hapus = findViewById(R.id.btnhapusreq)
        swipeRefreshLayout = findViewById(R.id.swipedetailhistorypbrg)



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

        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        val databrg = Gson().fromJson(
                            getIntent().getStringExtra("datapengajuan"),
                            MPengajuan::class.java
                        )

                        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
                        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
                        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jumlah_brg)
                        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
                        txt_alasan.setText("Alasan : " + databrg.alasan)


                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 3000)
            }
        })


        val databrg =
            Gson().fromJson(getIntent().getStringExtra("datapengajuan"), MPengajuan::class.java)

        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jumlah_brg)
        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
        txt_alasan.setText("Alasan : " + databrg.alasan)



        if (databrg.getStatus().equals("wait")) {
            btn_ubah.setText("Menunggu Konfirmasi Hrd")
            btn_ubah.setBackgroundColor(Color.GRAY)
            btn_ubah.setTextColor(Color.WHITE)
            btn_ubah.isEnabled = false
//            btn_hapus.setVisibility(View.GONE)

        }else if (databrg.getStatus().equals("ditolak")) {
            btn_ubah.setText("Pengajuan Ditolak")
            btn_ubah.setBackgroundColor(Color.BLACK)
            btn_ubah.setTextColor(Color.WHITE)
            btn_ubah.isEnabled = false
//            btn_hapus.setVisibility(View.GONE)


        }else if (databrg.getStatus().equals("done")) {
            btn_ubah.setText("Pengajuan Telah Disetujui")
            btn_ubah.setBackgroundColor(Color.BLUE)
            btn_ubah.setTextColor(Color.WHITE)
            btn_ubah.isEnabled = false

//            btn_hapus.setOnClickListener {
//
//                val loading = ProgressDialog(this)
//                loading.setCancelable(false)
//                loading.setMessage("proses pengajuan...")
//                loading.show()
//
////                val id_mitra_ac : String = Rak.grab("idmitraac")
//                val id_user: String = Rak.grab("iduser")
//                val stringRequest = object : StringRequest(
//                    Request.Method.POST, URL_req_hapus,
//                    Response.Listener<String> { response ->
//
//                        Log.e(TAG, "Pengajuan hapus order Response: $response")
//
//                        val intent = Intent(this,HomeKaryawanActivity::class.java)
//                        startActivity(intent)
//
//                        loading.dismiss()
//                        Toast.makeText(
//                            getApplicationContext(),
//                            "pengajuan berhasil",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        onBackPressed()
//
//                    },
//                    object : Response.ErrorListener {
//                        override fun onErrorResponse(volleyError: VolleyError) {
//                            loading.dismiss()
//                            Toast.makeText(
//                                applicationContext,
//                                volleyError.message,
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }) {
//                    @Throws(AuthFailureError::class)
//                    override fun getParams(): Map<String, String> {
//                        val params = HashMap<String, String>()
//                        params.put("id_user", id_user)
//                        params.put("status","hapus")
//                        return params
//                    }
//                }
//
//                //adding request to queue
//                AppController.getInstance().addToRequestQueue(stringRequest)
//
//
//            }


//        }else if (databrg.getStatus().equals("hapus")) {
//            btn_ubah.setText("Menunggu Persetujuan Hapus")
//            btn_ubah.setBackgroundColor(Color.BLACK)
//            btn_ubah.setTextColor(Color.WHITE)
//            btn_ubah.isEnabled = false
////            btn_hapus.setVisibility(View.GONE)
            }
    }
}