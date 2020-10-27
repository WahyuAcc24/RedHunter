package com.wr15.redhunter.Develop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wr15.redhunter.Develop.User.FormKelolaUser
import com.wr15.redhunter.Develop.Barang.FormKelolaBarang
import com.wr15.redhunter.R
import kotlinx.android.synthetic.main.homedev_main.*

class FragmentHomeActivity : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homedev_main, container, false)
//        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)




        imgkelolabarang.setOnClickListener {

            startActivity(Intent(context, FormKelolaBarang::class.java))

        }

        imgkelolauser.setOnClickListener {

            startActivity(Intent(context, FormKelolaUser::class.java))

        }


    }
    companion object {
        fun newInstance(): FragmentHomeActivity {
            val fragment = FragmentHomeActivity()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

}