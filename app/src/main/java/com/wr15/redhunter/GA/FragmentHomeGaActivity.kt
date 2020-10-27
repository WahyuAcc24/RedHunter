package com.wr15.redhunter.GA

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wr15.redhunter.Develop.Barang.FormKelolaBarang
import kotlinx.android.synthetic.main.homega_main.*
import kotlinx.android.synthetic.main.homekaryawan_main.*
import android.R
import androidx.core.app.ActivityCompat


class FragmentHomeGaActivity : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.wr15.redhunter.R.layout.homega_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)





        imgpengajuanbarangga.setOnClickListener {

            startActivity(Intent(context, FormPengajuanBarangGa::class.java))

        }

        imgkelolabarangga.setOnClickListener {

            startActivity(Intent(context, FormKelolaBarangGa::class.java))

        }

    }
    companion object {
        fun newInstance(): FragmentHomeGaActivity {
            val fragment = FragmentHomeGaActivity()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

}