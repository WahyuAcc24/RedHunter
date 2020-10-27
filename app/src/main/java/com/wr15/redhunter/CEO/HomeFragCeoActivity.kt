package com.wr15.redhunter.CEO

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wr15.redhunter.R

class HomeFragCeoActivity : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homeceo_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)



//        imgpengajuanbarang.setOnClickListener {
//
//            startActivity(Intent(context, FormPengajuanBarang::class.java))
//
//        }




    }

    companion object {
        fun newInstance(): HomeFragCeoActivity {
            val fragment = HomeFragCeoActivity()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}