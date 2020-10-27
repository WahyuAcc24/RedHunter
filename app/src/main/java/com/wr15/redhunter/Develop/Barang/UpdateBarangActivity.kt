package com.wr15.redhunter.Develop.Barang

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MFile
import kotlinx.android.synthetic.main.update_kelola_barang.*
import java.util.HashMap

class UpdateBarangActivity : AppCompatActivity(){

    private lateinit var edt_nama_brg : EditText
    private lateinit var edt_satuan_brg : EditText
    private lateinit var edt_harga_brg : EditText
    private lateinit var edt_jmlh_brg : EditText
    private lateinit var btn_barang : Button


    private val URL_brg = ServerHost.url + "ubah_barang.php"

    internal lateinit var conMgr: ConnectivityManager

    private val TAG = UpdateBarangActivity::class.java.getSimpleName()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_kelola_barang)


        edt_nama_brg = findViewById(R.id.edtupdatenamabrg)
        edt_harga_brg = findViewById(R.id.edtupdatehargabrg)
        edt_satuan_brg = findViewById(R.id.edtupdatenamasatuanbrg)
        edt_jmlh_brg = findViewById(R.id.edtupdatejmlhbrg)
        btn_barang = findViewById(R.id.btnupdatekelolabrg)

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

        val databrg = Gson().fromJson(getIntent().getStringExtra("dataupdatebarang"), MFile::class.java)



        edt_nama_brg.setText(databrg.nama_brg)
        edt_satuan_brg.setText(databrg.satuan_brg)
        edt_harga_brg.setText(databrg.harga_brg)
        edt_jmlh_brg.setText(databrg.jmlh_brg)


       btn_barang.setOnClickListener {

           val id_brg = databrg.id.toString()
           val nama_brg = edt_nama_brg.text.toString()
           val satuan_brg = edt_satuan_brg.text.toString()
           val harga_brg =  edt_harga_brg.text.toString()
           val jmlh_brg = edt_jmlh_brg.text.toString()


           if (conMgr.activeNetworkInfo != null
               && conMgr.activeNetworkInfo!!.isAvailable
               && conMgr.activeNetworkInfo!!.isConnected
           ) {
               updateBrg(id_brg,nama_brg,satuan_brg,harga_brg,jmlh_brg)
           } else {
               Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                   .show()
           }


       }

        edtupdatenamabrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_barang_updt.setHint("Nama Barang")
                    edtupdatenamabrg.setHint("Laptop/Meja/Monitor dll")
                }
                else
                {
                    nama_barang_updt.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })

        edtupdatenamasatuanbrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_satuan_barang_updt.setHint("Nama Satuan Barang")
                    edtupdatenamasatuanbrg.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
                else
                {
                    nama_satuan_barang_updt.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })


    }


    fun updateBrg(id_brg: String,nama_brg:String, satuan_brg:String, harga_brg:String, jmlh_brg:String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Mengubah data...")
        loading.show()



        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_brg,
            Response.Listener<String> { response ->

                Log.e(TAG, "Mengubah data Response: $response")

                loading.dismiss()
                edt_nama_brg.setText("")
                edt_satuan_brg.setText("")
                edt_harga_brg.setText("")
                edt_jmlh_brg.setText("")
                Toast.makeText(getApplicationContext(), "Data berhasil dibah", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UpdateBarangActivity,
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
                params.put("nama_brg", nama_brg)
                params.put("satuan_brg",satuan_brg)
                params.put("harga_brg", harga_brg)
                params.put("jmlh_brg", jmlh_brg)
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)


    }

    override fun onBackPressed() {
        finish()
    }





}