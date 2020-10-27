package com.wr15.redhunter.Develop

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wr15.redhunter.Develop.User.FragmentHistoryUserActivity
import com.wr15.redhunter.Develop.Barang.FragmentHistoryBrgActivity
import com.wr15.redhunter.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_sheet_history.view.*

class HomeActivity : AppCompatActivity() {


    var bottomSheetDialog: BottomSheetDialog? = null



    private var content: FrameLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        bottomSheetDialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_history, null)

        bottomSheetDialog?.setContentView(view)

        view.tvHistoryUser.setOnClickListener {

            val fragment = FragmentHistoryUserActivity()
            addFragment(fragment)
            bottomSheetDialog?.hide()


        }

        view.tvHistoryBarang.setOnClickListener {

            val fragment = FragmentHistoryBrgActivity()
            addFragment(fragment)
            bottomSheetDialog?.hide()


        }


        bn_main.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragmenthome =
            FragmentHomeActivity.newInstance()
        addFragment(fragmenthome)





    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.home_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
                val fragment = FragmentHomeActivity()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }
            R.id.history_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.pesanan_bot)
//                showBottomSheet()
                bottomSheetDialog?.show()

                return@OnNavigationItemSelectedListener true

            }
            R.id.exit_menu -> {
                finish()
            }


        }
        false
    }

    private fun addFragment(fragment: Fragment) {


//        val json = ""
//        val res = Gson().fromJson<Konsumen>(json,Konsumen::class.java)

//        var sharedPreferences  = getSharedPreferences("pref", Context.MODE_PRIVATE)

//        id = getIntent().getStringExtra(TAG_ID)


        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.f1_container, fragment,fragment.javaClass.simpleName)
            .commit()
    }

    private fun replaceFragment(){

        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)


    }


}