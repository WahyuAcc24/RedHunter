package com.wr15.redhunter.CEO

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MUser

class DetailHistoryUserCeo : AppCompatActivity() {

    lateinit var txt_nama : TextView
    lateinit var txt_divisi : TextView
    lateinit var txt_jk : TextView
//    lateinit var btn_ubah : Button
//    lateinit var btn_hapus : Button

    val URL_user = ServerHost.url + "hapus_user.php"


    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailHistoryUserCeo::class.java.getSimpleName()



    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_userceo_main)


        txt_nama =  findViewById(R.id.txtnamauserdetailceo)
        txt_divisi = findViewById(R.id.txtdivisiuserdetailceo)
        txt_jk =  findViewById(R.id.txtjkuserdetailceo)
//        btn_hapus = findViewById(R.id.btnhapusdatauser)
//        btn_ubah = findViewById(R.id.btnubahdatauser)

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



        swipeRefreshLayout = findViewById(R.id.swipedetailhistoryuserceo)


        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object:Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        val datauser = Gson().fromJson(getIntent().getStringExtra("data"), MUser::class.java)

                        txt_nama.setText("Nama : " + datauser.nama_user)
                        txt_divisi.setText("Divisi : " + datauser.divisi)
                        txt_jk.setText("Jenis Kelamin : " + datauser.jenis_kelamin)                    // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 5000)
            }
        })



        val datauser = Gson().fromJson(getIntent().getStringExtra("data"), MUser::class.java)

        txt_nama.setText("Nama : " + datauser.nama_user)
        txt_divisi.setText("Divisi : " + datauser.divisi)
        txt_jk.setText("Jenis Kelamin : " + datauser.jenis_kelamin)


//        btn_ubah.setOnClickListener {
//
//            val intent = Intent(this, UpdateUserActivity::class.java)
//            intent.putExtra("dataupdateuser",Gson().toJson(datauser))
//            startActivity(intent)
//
//        }
//
//        btn_hapus.setOnClickListener {
//
//            val id_user = datauser.id.toString()
//
//            if (conMgr.activeNetworkInfo != null
//                && conMgr.activeNetworkInfo!!.isAvailable
//                && conMgr.activeNetworkInfo!!.isConnected
//            ) {
//                hapusUser(id_user)
//            } else {
//                Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
//                    .show()
//            }
//
//        }
//
//
//    }
//
//    fun hapusUser(id_user:String){
//
//        val loading = ProgressDialog(this)
//        loading.setCancelable(false)
//        loading.setMessage("Menghapus data...")
//        loading.show()
//
//        val stringRequest = object : StringRequest(
//            Request.Method.POST, URL_user,
//            Response.Listener<String> { response ->
//
//                Log.e(TAG, "Mengubah data Response: $response")
//
//                loading.dismiss()
//
//                Toast.makeText(getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@DetailHistoryUserCeo,
//                    HomeActivity::class.java)
//                startActivity(intent)
//                finish()
//            },
//            object : Response.ErrorListener {
//                override fun onErrorResponse(volleyError: VolleyError) {
//                    loading.dismiss()
//                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
//                }
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params.put("id",id_user)
//
//                return params
//            }
//        }
//
//        //adding request to queue
//        AppController.getInstance().addToRequestQueue(stringRequest)





    }


}