package com.wr15.redhunter.Develop.Barang

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.wr15.redhunter.Adapter.HistoryInventoryAdapter
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Retrofit.RemoteData
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MFile
import kotlinx.android.synthetic.main.form_kelola_barang.*
import java.util.HashMap

class FormKelolaBarang : AppCompatActivity(){

    private lateinit var edt_nama_brg : AutoCompleteTextView
    private lateinit var edt_satuan_brg : EditText
//    private lateinit var edt_harga_brg : EditText
    private lateinit var edt_jmlh_brg : EditText
    private lateinit var edt_nama_user : EditText
    private lateinit var edt_divisi : EditText
    private lateinit var btn_barang : Button
    private var requesQueue: RequestQueue? = null

    private lateinit var pglistbrg: ProgressBar

    var mfile: List<MFile>? = null
    private var adapterfile: HistoryInventoryAdapter? = null
    private lateinit var lstHistoribrg: RecyclerView


    var urlbrglist = ServerHost.url + "list_brgdev.php"

    val URL_brg = ServerHost.url + "insert_brg.php"

    internal var tag_json_obj = "json_obj_req"

    lateinit var pDialog: ProgressDialog



    internal lateinit var conMgr: ConnectivityManager

    private val TAG = FormKelolaBarang::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_kelola_barang)

        edt_nama_brg =  findViewById(R.id.edtnamabrg)
        edt_jmlh_brg =  findViewById(R.id.edtjmlhbrg)
//        edt_harga_brg = findViewById(R.id.edthargabrg)
        edt_satuan_brg =  findViewById(R.id.edtnamasatuanbrg)
//        edt_nama_user = findViewById(R.id.edtnamauserkelola)
//        edt_divisi = findViewById(R.id.edtdivisi)
        btn_barang =  findViewById(R.id.btnkelolabrg)

//        edt_harga_brg.addTextChangedListener(NumberTextWatcher(edt_harga_brg))

        var remoteData = RemoteData(this)
        remoteData.getDataBarang()

        edt_nama_brg.setOnItemClickListener(onItemClickListener)







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

            val nama_brg = edt_nama_brg.text.toString()
            val satuan_brg = edt_satuan_brg.text.toString()
//            val harga_brg =  edt_harga_brg.text.toString()
            val jmlh_brg = edt_jmlh_brg.text.toString()
            val nama_user = edt_nama_user.text.toString()
//            val divisi = edtdivisi.text.toString()

            if (nama_brg.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {
                    cekBrg(nama_brg,jmlh_brg,nama_user,satuan_brg)
                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Data Kurang Lengkap", Toast.LENGTH_LONG)
                    .show()

            }



        }




//        edtnamabrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
//            override fun onFocusChange(view: View, hasfocus:Boolean) {
//                if (hasfocus)
//                {
//                    nama_barang.setHint("Nama Barang")
//                    edtnamabrg.setHint("Laptop/Meja/Monitor dll")
//                }
//                else
//                {
//                    nama_barang.setHint("Lusin/gross/kodi/rim/lembar/ dll")
//                }
//            }
//        })



        edtnamasatuanbrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_satuan_barang.setHint("Nama Satuan Barang")
                    edtnamasatuanbrg.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
                else
                {
                    nama_satuan_barang.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })



    }

    private fun cekBrg(nama_brg:String ,satuan_brg:String, jmlh_brg:String, nama_user: String){

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
//                edt_harga_brg.setText("")
                edt_jmlh_brg.setText("")
                edt_divisi.setText("")
                edt_nama_user.setText("")
                Toast.makeText(getApplicationContext(), "Data berhasil masuk", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@FormKelolaBarang,
                    HomeActivity::class.java)
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
                params.put("nama_brg", nama_brg)
                params.put("satuan_brg",satuan_brg)
//                params.put("harga_brg", harga_brg)
                params.put("jmlh_brg", jmlh_brg)
                params.put("nama_user",nama_user)
//                params.put("divisi",divisi)
                params.put("status","done")
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
    }
    private val onItemClickListener = object: AdapterView.OnItemClickListener {
        override fun onItemClick(adapterView:AdapterView<*>, view:View, i:Int, l:Long) {
            Toast.makeText(this@FormKelolaBarang,
                ("Clicked item " + adapterView.getItemAtPosition(i)), Toast.LENGTH_SHORT).show()
        }
    }
}