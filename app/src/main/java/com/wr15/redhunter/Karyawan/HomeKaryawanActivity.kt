package com.wr15.redhunter.Karyawan

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wr15.redhunter.PilihanUserActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.util.Preferences
import io.isfaaghyth.rak.Rak
import kotlinx.android.synthetic.main.activity_home_karyawan.*
import kotlinx.android.synthetic.main.bottom_sheet_history_karyawan.view.*

class HomeKaryawanActivity : AppCompatActivity() {


    private var bottomSheetDialog: BottomSheetDialog? = null



    private var content: FrameLayout? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_karyawan)


        bottomSheetDialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_history_karyawan, null)

        bottomSheetDialog?.setContentView(view)

        view.tvHistoryInventoryKaryawan.setOnClickListener {

            val fragment = HistoryInventoryBarangFragmentUser()
            addFragment(fragment)
            bottomSheetDialog?.hide()


        }

        view.tvHistoryPengajuanBarang.setOnClickListener {

            val fragment = HistoryPengajuanBarangFragmentUser()
            addFragment(fragment)
            bottomSheetDialog?.hide()


        }


        bn_main2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragmenthome = HomeFragKaryawanActivity.newInstance()
        addFragment(fragmenthome)





    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.home_menu -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
                val fragment = HomeFragKaryawanActivity()
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
                replaceFragment()
                Preferences.clearLoggedInNama(baseContext)
                Preferences.clearLoggedInEmail(baseContext)
                Preferences.clearLoggedInId(baseContext)
                val i = Intent(this, PilihanUserActivity::class.java)
                Preferences.clearLoggedInNama(baseContext)
                Preferences.clearLoggedInEmail(baseContext)
                Preferences.clearLoggedInId(baseContext)
                Preferences.setLoggedInStatus(baseContext,false)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Preferences.setLoggedInStatus(baseContext,false)
                startActivity(i)
                Rak.entry("loginkaryawan", false)
                Rak.removeAll(baseContext)
                finishAffinity()
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
            .replace(R.id.f1_container2, fragment,fragment.javaClass.simpleName)
            .commit()
    }

    private fun replaceFragment(){

        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)


    }


}