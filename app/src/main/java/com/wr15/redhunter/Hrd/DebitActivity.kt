package com.wr15.redhunter.Hrd

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.wr15.redhunter.R
import com.wr15.redhunter.model.NumberTextWatcher
import java.text.SimpleDateFormat
import java.util.*

class DebitActivity : AppCompatActivity() {

    lateinit var edt_tanggal : EditText
    lateinit var edt_debit : EditText
    lateinit var edt_keterangan : EditText

    private var dateFormatter: SimpleDateFormat? = null

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_form_debit)


        edt_tanggal = findViewById(R.id.edttgldebit)
        edt_debit = findViewById(R.id.edtdebit)
        edt_keterangan = findViewById(R.id.edtket)


        edt_tanggal.isFocusable = false


        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        edt_tanggal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@DebitActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)


        edt_debit.addTextChangedListener(NumberTextWatcher(edt_debit))












    }



    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_tanggal.setText(dateFormatter?.format(cal.getTime()))
    }


}