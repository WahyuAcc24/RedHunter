package com.wr15.redhunter.Hrd

import android.app.ProgressDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Hrd.rekappdf.RekapTrainerActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MPbPdf
import kotlinx.android.synthetic.main.pilih_user_pengajuan.*
import kotlinx.android.synthetic.main.pilihan_login.*
import kotlinx.android.synthetic.main.pilihan_login.imgceo
import org.json.JSONObject
import java.util.HashMap

class PilihanDivisiPb : Fragment() {



    var loading: ProgressDialog? = null

    val URL = ServerHost.url + "list_pb_trainer.php"


    internal lateinit var conMgr: ConnectivityManager
    private var requesQueue: RequestQueue? = null

    private val TAG = RekapTrainerActivity::class.java.getSimpleName()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pilih_user_pengajuan, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = ProgressDialog(context)
        loading?.setCancelable(false)
        loading?.setMessage("Mohon Tunggu...")

        ambilData()


        imgceo.setOnClickListener {

        }

        imgcoo.setOnClickListener {

        }

        imgga.setOnClickListener {

        }

        imgtrainer.setOnClickListener {

            ambilData()

        }

        imgcs.setOnClickListener {

        }

        imgmits.setOnClickListener {


        }




    }

    fun ambilData(){

        loading?.show()

        val stringRequest = object : StringRequest(
            Request.Method.GET, URL,
            Response.Listener<String> { response ->

                Log.e(TAG, "Login Response: $response")


                var res = Gson().fromJson(response.toString(), MPbPdf::class.java)

                val intent = Intent(context,RekapTrainerActivity::class.java)
                intent.putExtra("datarekap",Gson().toJson(res))
                startActivity(intent)



            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading?.dismiss()
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG)
                        .show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
//                params.put("nama_user", nama)
//                params.put("password", password)
                return params
            }
        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)

    }
}