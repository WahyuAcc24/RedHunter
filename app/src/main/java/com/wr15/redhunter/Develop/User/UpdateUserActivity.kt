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
import com.google.gson.Gson
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MUser
import kotlinx.android.synthetic.main.update_kelola_user.*
import java.util.HashMap

class UpdateUserActivity : AppCompatActivity(){

    private lateinit var edt_nama_user : EditText
    private lateinit var edt_pass : EditText
    private lateinit var edt_jk : EditText
    private lateinit var edt_divisi : EditText
    private lateinit var btn_user : Button


    private val URL_user = ServerHost.url + "ubah_user.php"

    internal lateinit var conMgr: ConnectivityManager

    private val TAG = UpdateUserActivity::class.java.getSimpleName()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_kelola_user)


        edt_nama_user =  findViewById(R.id.edtupdatenamauser)
        edt_jk = findViewById(R.id.edtupdatejkuser)
        edt_pass = findViewById(R.id.edtupdatepassuser)
        edt_divisi = findViewById(R.id.edtupdatenamadivisi)
        btn_user = findViewById(R.id.btnupdateRegUser)

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

        val datauser = Gson().fromJson(getIntent().getStringExtra("dataupdateuser"), MUser::class.java)



        edt_nama_user.setText(datauser.nama_user)
        edt_jk.setText(datauser.jenis_kelamin)
//        edt_pass.setText(datauser.password)
        edt_divisi.setText(datauser.divisi)

        edtupdatenamadivisi.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_divisi_update.setHint("Nama Divisi")
                    edtupdatenamadivisi.setHint("CEO/CTO/COO/HRD dll")
                }
                else
                {
                    nama_divisi_update.setHint("CEO/CTO/COO/HRD dll")
                }
            }
        })

       btn_user.setOnClickListener {

           val id_user = datauser.id.toString()
           val nama_user = edt_nama_user.text.toString()
           val jk_user = edt_jk.text.toString()
           val divisi =  edt_divisi.text.toString()
           val pass = edt_pass.text.toString()


           if (conMgr.activeNetworkInfo != null
               && conMgr.activeNetworkInfo!!.isAvailable
               && conMgr.activeNetworkInfo!!.isConnected
           ) {
               updateBrg(id_user,nama_user,jk_user,divisi,pass)
           } else {
               Toast.makeText(applicationContext, "tidak ada koneksi", Toast.LENGTH_LONG)
                   .show()
           }


       }


    }


    fun updateBrg(id_user: String,nama_user:String, jk_user:String, divisi:String, pass:String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Mengubah data...")
        loading.show()



        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_user,
            Response.Listener<String> { response ->

                Log.e(TAG, "Mengubah data Response: $response")

                loading.dismiss()
                edt_nama_user.setText("")
                edt_jk.setText("")
                edt_pass.setText("")
                edt_divisi.setText("")
                Toast.makeText(getApplicationContext(), "Data berhasil dibah", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UpdateUserActivity,
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
                params.put("id",id_user)
                params.put("nama_user", nama_user)
                params.put("jenis_kelamin",jk_user)
                params.put("password", pass)
                params.put("ulangiPwd", pass)
                params.put("divisi", divisi)
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