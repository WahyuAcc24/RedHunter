package com.wr15.redhunter.Hrd

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wr15.redhunter.R
import kotlinx.android.synthetic.main.hrd_main.*

class FragHomeHrd : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.hrd_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgkelolauserhrd.setOnClickListener {
            startActivity(Intent(context, FormKelolaUserHrd::class.java))
        }

        imgrekapdata.setOnClickListener {
            startActivity(Intent(context, PilihanUserPengajuan::class.java))
        }


    }

    companion object {
        fun newInstance(): FragHomeHrd {
            val fragment = FragHomeHrd()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}