package com.wr15.redhunter.Develop.User

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
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import kotlinx.android.synthetic.main.form_kelola_user.*
import java.util.HashMap

class FormKelolaUser : AppCompatActivity() {

    lateinit var edt_nama_user : EditText
    lateinit var edt_pass : EditText
    lateinit var edt_jk : EditText
    lateinit var edt_divisi : EditText
    lateinit var btn_ok : Button

    val URL_user = ServerHost.url + "insert_user.php"

    internal var tag_json_obj = "json_obj_req"

    lateinit var pDialog: ProgressDialog


    internal lateinit var conMgr: ConnectivityManager

    private val TAG = FormKelolaUser::class.java.getSimpleName()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_kelola_user)


        edt_nama_user =  findViewById(R.id.edtnamauser)
        edt_jk = findViewById(R.id.edtjkuser)
        edt_pass = findViewById(R.id.edtpassuser)
        edt_divisi = findViewById(R.id.edtnamadivisi)
        btn_ok = findViewById(R.id.btnRegUser)


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




        edtnamadivisi.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_divisi.setHint("Nama Divisi")
                    edtnamadivisi.setHint("CEO/CTO/COO/HRD dll")
                }
                else
                {
                    nama_divisi.setHint("CEO/CTO/COO/HRD dll")
                }
            }
        })

        btn_ok.setOnClickListener {


            val nama = edt_nama_user.text.toString()
            val jk = edt_jk.text.toString()
            val divisi = edt_divisi.text.toString()
            val password = edt_pass.text.toString()


            if (nama.length > 0 && password.length > 0) {
                if (conMgr.activeNetworkInfo != null
                    && conMgr.activeNetworkInfo!!.isAvailable
                    && conMgr.activeNetworkInfo!!.isConnected
                ) {
                    cek(nama,password,jk,divisi)
                } else {
                    Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Data Kurang Lengkap", Toast.LENGTH_LONG)
                    .show()

            }

        }


    }


    private fun cek(nama:String, password:String, jk:String, divisi:String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Menambahkan data...")
        loading.show()



        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_user,
            Response.Listener<String> { response ->

                Log.e(TAG, "Menambahkan data Response: $response")

                loading.dismiss()
                edt_nama_user.setText("")
                edt_pass.setText("")
                edt_divisi.setText("")
                edt_jk.setText("")
                Toast.makeText(getApplicationContext(), "Data berhasil masuk", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@FormKelolaUser,
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
                params.put("nama_user", nama)
                params.put("jenis_kelamin",jk)
                params.put("divisi", divisi)
                params.put("password", password)
                params.put("ulangiPwd", password)
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
