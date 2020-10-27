package com.wr15.redhunter.Hrd

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.dialog_komen.view.*
import java.util.HashMap

class DetailHistoryInventory : AppCompatActivity() {

    lateinit var txt_nama_brg : TextView
    lateinit var txt_satuan_brg : TextView
    lateinit var txt_jmlh_brg : TextView
    lateinit var txt_harga_brg : TextView
    lateinit var txt_nama_user : TextView
    lateinit var txt_divisi : TextView
    lateinit var btn_hps : Button

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    val URL_req_hapus =  ServerHost.url + "req_hapus_barang.php"


    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailHistoryUser::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_inventory_main)


        txt_nama_brg = findViewById(R.id.txtnamadetailinventory)
        txt_satuan_brg = findViewById(R.id.txtsatuandetailinventory)
        txt_jmlh_brg = findViewById(R.id.txtjmlhdetailinventory)
        txt_harga_brg = findViewById(R.id.txthargadetailinventory)
        txt_divisi = findViewById(R.id.txtdivisiinventory)
        txt_nama_user = findViewById(R.id.txtuserinventory)
        btn_hps = findViewById(R.id.btnhapusinventoryhrd)

        swipeRefreshLayout = findViewById(R.id.swipedetailhistoryinventory)



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
                        val databrg =
                            Gson().fromJson(getIntent().getStringExtra("data"), MFile::class.java)

                        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
                        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
                        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jmlh_brg)
//                        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
                        txt_nama_user.setText("Nama User : " + databrg.nama_user)
                        txt_divisi.setText("Divisi : " + databrg.divisi)


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
        txt_nama_user.setText("Nama User : " + databrg.nama_user)
        txt_divisi.setText("Divisi : " + databrg.divisi)


        if (databrg.getStatus().equals("done")) {
            btn_hps.setText("Ajukan Hapus Barang")
            btn_hps.setBackgroundColor(Color.RED)
            btn_hps.setTextColor(Color.WHITE)
            btn_hps.isEnabled = true


            btn_hps.setOnClickListener {

                val dialog : AlertDialog = AlertDialog.Builder(this).create()
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_komen, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)

                val mAlertDialog = mBuilder.show()


                mDialogView.btnRating.setOnClickListener {

                    val loading = ProgressDialog(this)
                    loading.setCancelable(false)
                    loading.setMessage("proses pengajuan...")
                    loading.show()

//                val id_mitra_ac : String = Rak.grab("idmitraac")
                    val id: String = databrg.id.toString()
                    var alasan_dihapus: String = mDialogView.edtKomen.text.toString()
                    val stringRequest = object : StringRequest(
                        Request.Method.POST, URL_req_hapus,
                        Response.Listener<String> { response ->

                            Log.e(TAG, "Pengajuan hapus order Response: $response")

                            val intent = Intent(this, HomeHrdActivity::class.java)
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
                            params.put("alasan_dihapus",alasan_dihapus)
                            params.put("status", "hapus")
                            return params
                        }
                    }

                    //adding request to queue
                    AppController.getInstance().addToRequestQueue(stringRequest)

                }
                mDialogView.btnCancel.setOnClickListener {
                    mAlertDialog.dismiss()
                }

                mAlertDialog.show()


            }


        }else if (databrg.getStatus().equals("hapus")) {
            btn_hps.setText("Pengajuan Sedang diproses")
            btn_hps.setBackgroundColor(Color.GRAY)
            btn_hps.setTextColor(Color.BLACK)
            btn_hps.isEnabled = false

        }

    }

}

