package com.wr15.redhunter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.wr15.redhunter.Develop.HomeActivity
import com.wr15.redhunter.Karyawan.HomeKaryawanActivity
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MKaryawan
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.logindev_main.*
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    lateinit var edt_nama : EditText
    lateinit var edt_pass : EditText

    val URL_user = ServerHost.url + "login.php"


    internal var tag_json_obj = "json_obj_req"



    internal lateinit var conMgr: ConnectivityManager

    private val TAG = LoginActivity::class.java.getSimpleName()

    var TAG_ID : String? = null
    var id : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logindev_main)

        edt_nama = findViewById(R.id.edtnamadev)
        edt_pass = findViewById(R.id.edtPassworddev)

        Rak.initialize(this)

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


        if (Rak.isExist("loginkaryawan")) {
            if (Rak.grab("loginkaryawan")) {
                startActivity(Intent(this, HomeKaryawanActivity::class.java))
                finish()
            }
        }



//        if (Preferences.getLoggedInStatus(baseContext)) {
//
//            startActivity(Intent(baseContext,HomeKaryawanActivity::class.java))
//            finish()
//        }



        btnMasukdev.setOnClickListener {

            val nama = edt_nama.text.toString()
            val password = edt_pass.text.toString()


            if (edt_nama.getText().toString().equals("admin") && edt_pass.getText().toString().equals("admin")){

                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)

            }else{
                if (nama.length > 0 && password.length > 0) {
                    if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(nama, password)
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }else{
                        Toast.makeText(applicationContext,"Nama Dan Password salah", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    fun checkLogin(nama:String, password:String){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Mohon Tunggu...")
        loading.show()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URL_user,
            Response.Listener<String> { response ->

                Log.e(TAG, "Login Response: $response")

                var res = Gson().fromJson(response.toString(), MKaryawan::class.java)

                var job : JSONObject = JSONObject(response)


                if (res.isStatus) {

                    if(res.data_user.id != null) Rak.entry("iduser", res.data_user.id)
                    if (res.data_user.nama_user != null) Rak.entry("namauser",res.data_user.nama_user)
                    if (res.data_user.divisi != null) Rak.entry("divisiuser", res.data_user.divisi)


                    Rak.entry("loginkaryawan", true)
                    loading.dismiss()
                    edt_nama.setText("")
                    edt_pass.setText("")
                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity,
                        HomeKaryawanActivity::class.java)
                    startActivity(intent)

                }else{
                    loading.dismiss()
                    Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show()
                }

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
                params.put("password", password)
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)
    }


}

