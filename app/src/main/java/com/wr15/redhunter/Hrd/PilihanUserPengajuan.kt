package com.wr15.redhunter.Hrd

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wr15.redhunter.Adapter.HistoryBrgPengajuanHrdAdapter
import com.wr15.redhunter.Adapter.ItemClickListener
import com.wr15.redhunter.AppController
import com.wr15.redhunter.Hrd.rekappdf.*
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.BrgStatus
import com.wr15.redhunter.model.MPbPdf
import com.wr15.redhunter.model.MPengajuan
import kotlinx.android.synthetic.main.pilih_user_pengajuan.*
import java.lang.Exception
import java.lang.reflect.Type
import java.util.HashMap

class PilihanUserPengajuan : AppCompatActivity() {

    lateinit var img_trainer : ImageView
    var loading: ProgressDialog? = null

    val URL = ServerHost.url + "list_pb_trainer.php"


    internal lateinit var conMgr: ConnectivityManager
    private var requesQueue: RequestQueue? = null

    private val TAG = RekapTrainerActivity::class.java.getSimpleName()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pilih_user_pengajuan)

        loading = ProgressDialog(this)
        loading?.setCancelable(false)
        loading?.setMessage("Mohon Tunggu...")



        img_trainer = findViewById(R.id.imgtrainer)

        img_trainer.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapTrainerActivity::class.java)
             startActivity(intent)


        }

        imgob.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapObActivity::class.java)
            startActivity(intent)


        }
        imgmits.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapItsActivity::class.java)
            startActivity(intent)


        }
        imgga.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapGaActivity::class.java)
            startActivity(intent)


        }
        imgcs.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapCsActivity::class.java)
            startActivity(intent)


        }
        imgcoo.setOnClickListener {

            val intent = Intent(this@PilihanUserPengajuan, RekapCooActivity::class.java)
            startActivity(intent)


        }


    }

    }

