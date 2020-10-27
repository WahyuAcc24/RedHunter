package com.wr15.redhunter.Karyawan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wr15.redhunter.R
import kotlinx.android.synthetic.main.homekaryawan_main.*

class HomeFragKaryawanActivity : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homekaryawan_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)



        imgpengajuanbarang.setOnClickListener {

            startActivity(Intent(context, FormPengajuanBarang::class.java))

        }




    }

    companion object {
        fun newInstance(): HomeFragKaryawanActivity {
            val fragment = HomeFragKaryawanActivity()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}