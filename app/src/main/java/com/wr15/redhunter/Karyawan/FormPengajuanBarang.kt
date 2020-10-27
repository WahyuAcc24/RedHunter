package com.wr15.redhunter.Karyawan

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
import com.wr15.redhunter.AppController
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.NumberTextWatcher
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.form_pengajuan_barang.*
import java.util.HashMap

class FormPengajuanBarang : AppCompatActivity(){

    private lateinit var edt_nama_brg : EditText
    private lateinit var edt_satuan_brg : EditText
    private lateinit var edt_harga_brg : EditText
    private lateinit var edt_jmlh_brg : EditText
    private lateinit var edt_alasan : EditText
    private lateinit var btn_barang : Button


    val URL_brg : String = ServerHost.url + "pengajuan_brg.php"

    internal var tag_json_obj = "json_obj_req"

    lateinit var pDialog: ProgressDialog


    internal lateinit var conMgr: ConnectivityManager

    private val TAG = FormPengajuanBarang::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_pengajuan_barang)

        edt_nama_brg =  findViewById(R.id.edtpnamabrg)
        edt_jmlh_brg =  findViewById(R.id.edtpjmlhbrg)
        edt_harga_brg = findViewById(R.id.edtphargabrg)
        edt_satuan_brg =  findViewById(R.id.edtpnamasatuanbrg)
        edt_alasan = findViewById(R.id.edtalasan)
        btn_barang =  findViewById(R.id.btnpengajuanbrg)



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


        btn_barang.setOnClickListener {

            val id_user = Rak.grab("iduser") as String
            val nama_user = Rak.grab("namauser") as String
            val divisi = Rak.grab("divisiuser") as String
            val nama_brg = edt_nama_brg.text.toString()
            val satuan_brg = edt_satuan_brg.text.toString()
            val harga_brg =  edt_harga_brg.text.toString()
            val jumlah_brg = edt_jmlh_brg.text.toString()
            val alasan = edt_alasan.text.toString()

            if (nama_brg.length > 0 && satuan_brg.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {
                    cekBrg(id_user,nama_user,divisi,nama_brg,satuan_brg,harga_brg,jumlah_brg,alasan)
                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Data Kurang Lengkap", Toast.LENGTH_LONG)
                    .show()

            }



        }




        edtpnamabrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    p_nama_barang.setHint("Nama Barang")
                    edtpnamabrg.setHint("Laptop/Meja/Monitor dll")
                }
                else
                {
                    p_nama_barang.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })

        edtpnamasatuanbrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    p_nama_satuan_barang.setHint("Nama Satuan Barang")
                    edtpnamasatuanbrg.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
                else
                {
                    p_nama_satuan_barang.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })


//        edt_harga_brg.addTextChangedListener(NumberTextWatcher(edt_harga_brg))


    }

    private fun cekBrg(id_user : String,nama_user : String, divisi:String ,nama_brg:String, satuan_brg:String, harga_brg:String, jumlah_brg:String, alasan : String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Menambahkan data...")
        loading.show()



        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_brg,
            Response.Listener<String> { response ->

                Log.e(TAG, "Menambahkan data Response: $response")

                loading.dismiss()
                edt_nama_brg.setText("")
                edt_satuan_brg.setText("")
                edt_harga_brg.setText("")
                edt_jmlh_brg.setText("")
                Toast.makeText(getApplicationContext(), "Data berhasil masuk", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@FormPengajuanBarang,
                    HomeKaryawanActivity::class.java)
                startActivity(intent)
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
                params.put("id_user",id_user)
                params.put("nama_user", nama_user)
                params.put("divisi",divisi)
                params.put("nama_brg", nama_brg)
                params.put("satuan_brg",satuan_brg)
                params.put("harga_brg", harga_brg)
                params.put("jumlah_brg", jumlah_brg)
                params.put("alasan", alasan)
                params.put("status", "wait")
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
    }

    override fun onBackPressed() {
//        intent = Intent(this, LoginActivity::class.java)
        finish()
//        startActivity(intent)
    }


}