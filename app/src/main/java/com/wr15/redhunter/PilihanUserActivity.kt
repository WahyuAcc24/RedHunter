package com.wr15.redhunter

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.wr15.redhunter.CEO.LoginCeoActivity
import com.wr15.redhunter.GA.LoginGaActivity
import com.wr15.redhunter.Hrd.LoginHrdActivity

class PilihanUserActivity : AppCompatActivity(){

    lateinit var img_karyawan : ImageView
    lateinit var img_hrd : ImageView
    lateinit var img_ceo : ImageView
    lateinit var img_ga : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pilihan_login)


        img_karyawan = findViewById(R.id.imgkaryawan)
        img_hrd = findViewById(R.id.imghrd)
        img_ceo = findViewById(R.id.imgceo)
        img_ga = findViewById(R.id.imggapilih)


        img_karyawan.setOnClickListener {

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }

        img_hrd.setOnClickListener {

            val intent = Intent(this,LoginHrdActivity::class.java)
            startActivity(intent)


        }

        img_ceo.setOnClickListener {

            val intent = Intent(this,LoginCeoActivity::class.java)
            startActivity(intent)

        }

        img_ga.setOnClickListener {

            val intent = Intent(this,LoginGaActivity::class.java)
            startActivity(intent)

        }



    }

}