package com.wr15.redhunter.Hrd

import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.parser.IntegerParser
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.wr15.redhunter.AppController
import com.wr15.redhunter.R
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MPdf
import com.wr15.redhunter.model.MPengajuan
import java.io.*
import java.util.*
import kotlin.collections.HashMap

class DetailHrdPengajuanBarang : AppCompatActivity() {

    private lateinit var txt_nama_brg: TextView
    private lateinit var txt_satuan_brg: TextView
    private lateinit var txt_jmlh_brg: TextView
    private lateinit var txt_harga_brg: TextView
    private lateinit var txt_alasan: TextView
    private lateinit var txt_nama_user: TextView
    private lateinit var txt_divisi: TextView
    private lateinit var txt_tanggal: TextView

    private var totalsemua : Int = 0
    private var totalestimasi : Int = 0

    var harga_satu : Int = 0
    var harga_dua : Int = 0


    private lateinit var btn_konf: Button
    private lateinit var btn_tolak : Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    var loading :ProgressDialog? = null



    val URL_wait = ServerHost.url + "updatestatushrd.php"
    val URL_tolak = ServerHost.url + "updatetolakhrd.php"




    internal lateinit var conMgr: ConnectivityManager

    private val TAG = DetailHrdPengajuanBarang::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pengajuanbrg_hrd)

        txt_nama_brg = findViewById(R.id.txtnamapbrgdetailhrd)
        txt_nama_user = findViewById(R.id.txtnamapengajuandetailhrd)
        txt_harga_brg = findViewById(R.id.txthargapbrgdetailhrd)
        txt_jmlh_brg = findViewById(R.id.txtjmlhpbrgdetailhrd)
        txt_satuan_brg = findViewById(R.id.txtsatuanpbrgdetailhrd)
        txt_alasan = findViewById(R.id.txtalasanpengajuandetailhrd)
        txt_divisi = findViewById(R.id.txtdivisihrd)
        txt_tanggal = findViewById(R.id.txttglhrd)

        btn_konf = findViewById(R.id.btnpengajuanbrghrd)
        btn_tolak = findViewById(R.id.btnpengajuanditolak)
        swipeRefreshLayout = findViewById(R.id.swipedetailhistorypbrghrd)

        loading = ProgressDialog(this)
        loading?.setCancelable(false)
        loading?.setMessage("Mohon Tunggu...")


        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        run {
            if (conMgr.activeNetworkInfo != null
                && conMgr.activeNetworkInfo!!.isAvailable
                && conMgr.activeNetworkInfo!!.isConnected
            ) {
            } else {
                Toast.makeText(
                    applicationContext, " Silahkan Cek Lagi Koneksi Anda ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.birulain, R.color.merah, R.color.Biru)

        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false)
                        val databrg = Gson().fromJson(
                            getIntent().getStringExtra("datapengajuan"),
                            MPengajuan::class.java
                        )

                        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
                        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
                        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jumlah_brg)
                        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
                        txt_alasan.setText("Alasan : " + databrg.alasan)
                        txt_nama_user.setText("Nama User : " + databrg.nama_user)
                        txt_divisi.setText("Divisi : " + databrg.divisi)
                        txt_tanggal.setText("Tanggal : " + databrg.tanggal)


                        // fungsi-fungsi lain yang dijalankan saat refresh berhenti

                    }
                }, 3000)
            }
        })


        val databrg =
            Gson().fromJson(getIntent().getStringExtra("datapengajuan"), MPengajuan::class.java)

        txt_nama_brg.setText("Nama Barang : " + databrg.nama_brg)
        txt_satuan_brg.setText("Satuan Barang : " + databrg.satuan_brg)
        txt_jmlh_brg.setText("Jumlah Barang : " + databrg.jumlah_brg)
        txt_harga_brg.setText("Harga Barang : " + "Rp. " + databrg.harga_brg)
        txt_alasan.setText("Alasan : " + databrg.alasan)
        txt_nama_user.setText("Nama User : " + databrg.nama_user)
        txt_divisi.setText("Divisi : " + databrg.divisi)
        txt_tanggal.setText("Tanggal : " + databrg.tanggal)



        if (databrg.getStatus().equals("wait")) {
            btn_konf.setText("Konfirmasi Pengajuan")
            btn_konf.setBackgroundColor(Color.GREEN)
            btn_konf.setTextColor(Color.WHITE)
            btn_konf.isEnabled = true
            btn_tolak.isEnabled = true

            btn_tolak.setOnClickListener {

                val loading = ProgressDialog(this)
                loading.setCancelable(false)
                loading.setMessage("proses penolakan ...")
                loading.show()

//                val id_mitra_ac : String = Rak.grab("idmitraac")
                val id: String = databrg.id
                val stringRequest = object : StringRequest(
                    Request.Method.POST, URL_tolak,
                    Response.Listener<String> { response ->

                        Log.e(TAG, "Konfirmasi tolak Response: $response")

                        val intent = Intent(this, HomeHrdActivity::class.java)
                        startActivity(intent)

                        loading.dismiss()
                        Toast.makeText(
                            getApplicationContext(),
                            "penolakan berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()

                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(volleyError: VolleyError) {
                            loading.dismiss()
                            Toast.makeText(
                                applicationContext,
                                volleyError.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params.put("id", id)
                        return params
                    }
                }

                //adding request to queue
                AppController.getInstance().addToRequestQueue(stringRequest)



            }

            btn_konf.setOnClickListener {

                val loading = ProgressDialog(this)
                loading.setCancelable(false)
                loading.setMessage("Mohon Menunggu...")
                loading.show()

//                val id_mitra_ac : String = Rak.grab("idmitraac")
                val id: String = databrg.id
                val stringRequest = object : StringRequest(
                    Request.Method.POST, URL_wait,
                    Response.Listener<String> { response ->

                        Log.e(TAG, "Konfirmasi order Response: $response")

                        val intent = Intent(this, HomeHrdActivity::class.java)
                        startActivity(intent)

                        loading.dismiss()
                        Toast.makeText(
                            getApplicationContext(),
                            "konfirmasi berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()

                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(volleyError: VolleyError) {
                            loading.dismiss()
                            Toast.makeText(
                                applicationContext,
                                volleyError.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params.put("id", id)
                        return params
                    }
                }

                //adding request to queue
                AppController.getInstance().addToRequestQueue(stringRequest)

            }

        } else if (databrg.getStatus().equals("done")) {
            btn_konf.setText("Simpan PDF")
            btn_konf.setBackgroundColor(Color.RED)
            btn_konf.setTextColor(Color.WHITE)
            btn_konf.isEnabled = true
            btn_tolak.setVisibility(View.GONE)
            btn_konf.setOnClickListener {


                requestPdf()


            }

        }

        else if (databrg.getStatus().equals("ditolak")) {
            btn_konf.setText("Pengajuan ini anda tolak")
            btn_konf.setBackgroundColor(Color.RED)
            btn_konf.setTextColor(Color.WHITE)
            btn_konf.isEnabled = false
            btn_tolak.setVisibility(View.GONE)
        }
    }
    private fun reportPDF(){


        val databrg =
            Gson().fromJson(getIntent().getStringExtra("datapengajuan"), MPengajuan::class.java)

        var modelpengajuan =  MPdf(
            txt_nama_user.text.toString(),
            txt_nama_brg.text.toString(),
            txt_satuan_brg.text.toString(),
            txt_jmlh_brg.text.toString(),
            txt_harga_brg.text.toString(),
            txt_alasan.text.toString(),
            txt_tanggal.text.toString(),
            txt_divisi.text.toString(),
            "status"
        )

        savedPDF()

        loading?.dismiss()
        Toast.makeText(this,"FilePdf Berhasil disimpan", Toast.LENGTH_SHORT).show()

    }

    private fun requestPdf(){
        loading?.show()
        Dexter.withActivity(this).withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()){

                    reportPDF()
                }
                if(report.isAnyPermissionPermanentlyDenied){

                }
            }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>,
                    token: PermissionToken
                ) {

                    token.continuePermissionRequest()

                }


            }).withErrorListener {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()

    }

    fun savedPDF(){

        loading?.show()


        val databrg =
            Gson().fromJson(getIntent().getStringExtra("datapengajuan"), MPengajuan::class.java)

        var now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",now)
        val filename = "PengajuanBarang " + databrg.nama_user + " " + now + ".pdf"
        val sdcard : File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

//        val path2 = File(this.getExternalFilesDir(null)!!.absolutePath)

        val path = File(sdcard.getAbsolutePath()  + "/Pengajuan")



        if(!path.exists())
            path.mkdirs()

        var file = File(path,filename)

        if(file.exists()){

//            file.delete()
            file = File(path,filename)
        }


        val document = Document()

        PdfWriter.getInstance(document, FileOutputStream(file))

        document.open()
        document.pageSize = PageSize.A4

        document.addCreationDate()
        document.addAuthor("")
        document.addCreator("HRD")


        val mColorAccent = BaseColor(0,153,204,255)
        val mHeadingFontSize = 16.0f
        val mValueFontSize = 8.0f

//        var pointColumnWidths = floatArrayOf(150f, 150f, 150f)
        var table  = PdfPTable(7)
        table.setWidthPercentage(100F)
        table.setWidths(floatArrayOf(10f,30f,20f,30f,30f,30f,30f))



        var tablejumlah = PdfPTable(3)
        tablejumlah.setWidthPercentage(100F)
        tablejumlah.setWidths(floatArrayOf(30f,20f,20f))

        var tabletgl = PdfPTable(1)
        tabletgl.setWidthPercentage(100F)
        tabletgl.setWidths(floatArrayOf(100f))





        val fontMontserrat = BaseFont.createFont("res/font/montserratregular.ttf", "UTF-8", BaseFont.EMBEDDED)
        val lineSeparator =  LineSeparator()
        lineSeparator.lineColor = BaseColor(0,0,0,68)

        val spasi =  LineSeparator()
        spasi.lineColor = BaseColor(0,0,0,0)


        val fontmontserratHeader = Font(
            fontMontserrat,18.0f, Font.BOLD, BaseColor.BLACK)

        val fontttd = Font(
            fontMontserrat,mValueFontSize, Font.BOLD, BaseColor.BLACK)


        val fontnormalcolorblack = Font(
            fontMontserrat,mValueFontSize, Font.NORMAL, BaseColor.BLACK)

        val fontnormalcolorwhite = Font(
            fontMontserrat,mValueFontSize, Font.NORMAL, BaseColor.WHITE)

        val fontnormalcolorblue = Font(
            fontMontserrat,mValueFontSize, Font.NORMAL, BaseColor.BLUE)

        val fontheading = Font(
            fontMontserrat,mHeadingFontSize, Font.NORMAL, BaseColor.BLACK)



        var ims : InputStream = assets.open("rhcapsatu.png")
        var bmp : Bitmap = BitmapFactory.decodeStream(ims)
        var stream  = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100,stream)
        val paragrafgambar : Image = Image.getInstance(stream.toByteArray())
        paragrafgambar.scaleAbsoluteWidth(500.0f)
        paragrafgambar.scaleAbsoluteHeight(150.0f)
        document.add(paragrafgambar)

        val paragraf1 = Paragraph(Chunk("PENGAJUAN BARANG",fontmontserratHeader))
        paragraf1.alignment = Element.ALIGN_CENTER
        document.add(paragraf1)

        val paragraf2 = Paragraph(Chunk(txt_tanggal.text.toString(),fontnormalcolorblue))
        paragraf2.alignment = Element.ALIGN_LEFT
        document.add(paragraf2)


        //garis 1
        document.add(Paragraph(""))
        document.add(Chunk(lineSeparator))
        document.add(Paragraph(""))

        val paragraf3 = Paragraph("Status Pengajuan",fontnormalcolorblack)
        paragraf3.add(Chunk(VerticalPositionMark()))
        paragraf3.add(databrg.status)
        document.add(paragraf3)




        //garis 2
        document.add(Paragraph(""))
        document.add(Chunk(lineSeparator))
        document.add(Paragraph(""))

//        var nocell = PdfPCell(Paragraph("No"))


        var nocell = PdfPCell(Paragraph("No",fontnormalcolorwhite))
        nocell.setHorizontalAlignment(Element.ALIGN_CENTER)
        nocell.setBackgroundColor(BaseColor.RED)


        var jk = PdfPCell(Paragraph("JENIS KEBUTUHAN",fontnormalcolorwhite))
        jk.setHorizontalAlignment(Element.ALIGN_CENTER)
        jk.setBackgroundColor(BaseColor.RED)

        var jumlah = PdfPCell(Paragraph("JUMLAH",fontnormalcolorwhite))
        jumlah.setHorizontalAlignment(Element.ALIGN_CENTER)
        jumlah.setBackgroundColor(BaseColor.RED)

        var alasan = PdfPCell(Paragraph("TUJUAN PENGADAAN",fontnormalcolorwhite))
        alasan.setHorizontalAlignment(Element.ALIGN_CENTER)
        alasan.setBackgroundColor(BaseColor.RED)

        var ket = PdfPCell(Paragraph("KETERANGAN",fontnormalcolorwhite))
        ket.setHorizontalAlignment(Element.ALIGN_CENTER)
        ket.setBackgroundColor(BaseColor.RED)

        var harga = PdfPCell(Paragraph("HARGA (/item)",fontnormalcolorwhite))
        harga.setHorizontalAlignment(Element.ALIGN_CENTER)
        harga.setBackgroundColor(BaseColor.RED)

        var estimasiharga = PdfPCell(Paragraph("ESTIMASI HARGA",fontnormalcolorwhite))
        estimasiharga.setHorizontalAlignment(Element.ALIGN_CENTER)
        estimasiharga.setBackgroundColor(BaseColor.RED)

        table.addCell(nocell)
        table.addCell(jk)
        table.addCell(jumlah)
        table.addCell(alasan)
        table.addCell(ket)
        table.addCell(harga)
        table.addCell(estimasiharga)

        //judul

        nocell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)

        jk.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
        jumlah.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
        alasan.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
        ket.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
        harga.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)
        estimasiharga.setHorizontalAlignment(Element.ALIGN_JUSTIFIED)


//        nocell.setColspan(4)



        harga_satu = databrg.harga_brg.toInt()
        harga_dua = databrg.harga_brg.toInt()


        //kolom bawah
        nocell = PdfPCell(Paragraph (databrg.id.toString(),fontnormalcolorwhite))
        nocell.setBackgroundColor(BaseColor.GRAY)
        nocell.setHorizontalAlignment(Element.ALIGN_CENTER)


        jk = PdfPCell(Paragraph (databrg.nama_brg.toString(),fontnormalcolorblack))
        jumlah = PdfPCell(Paragraph (databrg.jumlah_brg.toString(),fontnormalcolorblack))
        alasan = PdfPCell(Paragraph (databrg.alasan.toString(),fontnormalcolorblack))
        ket = PdfPCell(Paragraph (databrg.alasan.toString(),fontnormalcolorblack))
        harga = PdfPCell(Paragraph (harga_satu.toString(),fontnormalcolorblack))
        estimasiharga = PdfPCell(Paragraph (harga_dua.toString(),fontnormalcolorblack))


        table.addCell(nocell)
        table.addCell(jk)
        table.addCell(jumlah)
        table.addCell(alasan)
        table.addCell(ket)
        table.addCell(harga)
        table.addCell(estimasiharga)

        document.add(table)






        totalsemua =  harga_satu
        totalestimasi = harga_dua


        var jumlahtxt = PdfPCell(Paragraph("JUMLAH",fontnormalcolorwhite))
        jumlahtxt.setHorizontalAlignment(Element.ALIGN_CENTER)
        jumlahtxt.setBackgroundColor(BaseColor.BLUE)


        var jmlh1 = PdfPCell(Paragraph(totalsemua.toString(),fontnormalcolorwhite))
        jmlh1.setHorizontalAlignment(Element.ALIGN_CENTER)
        jmlh1.setBackgroundColor(BaseColor.BLUE)

        var jmlh2 = PdfPCell(Paragraph(totalsemua.toString(),fontnormalcolorwhite))
        jmlh2.setHorizontalAlignment(Element.ALIGN_CENTER)
        jmlh2.setBackgroundColor(BaseColor.BLUE)


        tablejumlah.addCell(jumlahtxt)
        tablejumlah.addCell(jmlh1)
        tablejumlah.addCell(jmlh2)

        document.add(tablejumlah)

        document.add(Paragraph(""))
        document.add(Chunk(lineSeparator))
        document.add(Paragraph(""))


        var tgltxtbawah = PdfPCell(Paragraph(databrg.tanggal.toString(),fontnormalcolorwhite))
        tgltxtbawah.setHorizontalAlignment(Element.ALIGN_CENTER)
        tgltxtbawah.setBackgroundColor(BaseColor.BLACK)

        tabletgl.addCell(tgltxtbawah)

        document.add(tabletgl)




        //garis 3
        document.add(Paragraph(""))
        document.add(Chunk(lineSeparator))
        document.add(Paragraph(""))


        val paragraf11 = Paragraph("Pemohon",fontnormalcolorblack)
        paragraf11.add(Chunk(VerticalPositionMark()))
        paragraf11.add("Disetujui")
        document.add(paragraf11)

        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))

        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))


        val ttduser = Paragraph(databrg.nama_user.toString(),fontttd)
        ttduser.add(Chunk(VerticalPositionMark()))
        ttduser.add("Himatul Mila")
        document.add(ttduser)


        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))

        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))



        val paragraf12 = Paragraph("Diketahui",fontnormalcolorblack)
        paragraf12.add(Chunk(VerticalPositionMark()))
        paragraf12.add("Penerima")
        document.add(paragraf12)

        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))
        
        document.add(Paragraph(""))
        document.add(Chunk(spasi))
        document.add(Paragraph(""))


        val ttd = Paragraph("Juli Herniansyah",fontttd)
        ttd.add(Chunk(VerticalPositionMark()))
        ttd.add("Sico Ferry Ekel")
        document.add(ttd)




        document.close()



    }

    private fun downloadExcel(){

        var downloadID : Long = 0

        var request = DownloadManager.Request(
            Uri.parse("http://t-hisyam.net/mila/excel/contoh_pake_html_table.php"))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)

            .setTitle("Pengajuan Barang")
            .setDescription("contoh deskripsi")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)


        var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID= dm.enqueue(request)
        var br = object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                if(id==downloadID){
                    Toast.makeText(applicationContext,"download telah selesai", Toast.LENGTH_SHORT).show()
                }
            }

    }
    registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))



    }
}
//        var jk = PdfPCell(Paragraph("JENIS KEBUTUHAN"))
//        jk.setColspan(4)
//        jk.setHorizontalAlignment(Element.ALIGN_CENTER)
//        jk.setBackgroundColor(BaseColor.RED)
//        table.addCell(jk)
//
//
//        val paragraf4 = Paragraph(databrg.nama_brg.toString(),fontnormalcolorblack) //jenis kebutuhan
//        paragraf4.add(Chunk(VerticalPositionMark()))
////        paragraf4.add(databrg.nama_user)
////        table.addCell(jk)
//        table.addCell(paragraf4)
//        document.add(table)
//
//
//        var jumlah = PdfPCell(Paragraph("JUMLAH"))
//        jumlah.setColspan(4)
//        jumlah.setHorizontalAlignment(Element.ALIGN_CENTER)
//        jumlah.setBackgroundColor(BaseColor.RED)
//        table.addCell(jumlah)
//
//        val paragraf5 = Paragraph(databrg.jumlah_brg.toString(),fontnormalcolorblack)
//        paragraf5.add(Chunk(VerticalPositionMark()))
////        paragraf5.add(databrg.divisi)
////        table.addCell(jumlah)
//        table.addCell(paragraf5)
//        document.add(table)
//
//
//        var tp = PdfPCell(Paragraph("TUJUAN PENGADAAN"))
//        tp.setColspan(4)
//        tp.setHorizontalAlignment(Element.ALIGN_CENTER)
//        tp.setBackgroundColor(BaseColor.RED)
//        table.addCell(tp)
//
//
//        val paragrafket = Paragraph(databrg.alasan.toString(),fontnormalcolorblack)
////        paragraf6.add(Chunk(VerticalPositionMark()))
////        paragraf6.add(databrg.nama_brg)
////        table.addCell(ket)
//        table.addCell(paragrafket)
//        document.add(table)
//
//        var ket = PdfPCell(Paragraph("KETERANGAN"))
//        ket.setColspan(4)
//        ket.setHorizontalAlignment(Element.ALIGN_CENTER)
//        ket.setBackgroundColor(BaseColor.RED)
//        table.addCell(ket)
//
//
//        val paragraf6 = Paragraph(databrg.alasan.toString(),fontnormalcolorblack)
////        paragraf6.add(Chunk(VerticalPositionMark()))
////        paragraf6.add(databrg.nama_brg)
////        table.addCell(ket)
//        table.addCell(paragraf6)
//        document.add(table)
//
//
//
//
//        var harga = PdfPCell(Paragraph("HARGA/PCS"))
//        harga.setColspan(4)
//        harga.setHorizontalAlignment(Element.ALIGN_CENTER)
//        harga.setBackgroundColor(BaseColor.RED)
//        table.addCell(harga)
//
//
//        val paragraf7 = Paragraph(databrg.harga_brg.toString(),fontnormalcolorblack)
////        paragraf7.add(Chunk(VerticalPositionMark()))
////        paragraf7.add(databrg.satuan_brg)
////        table.addCell(harga) // cell / judul
//        table.addCell(paragraf7)
//        document.add(table)
//
//
//
//
//        var estimasi = PdfPCell(Paragraph("ESTIMASI HARGA"))
//        estimasi.setColspan(4)
//        estimasi.setHorizontalAlignment(Element.ALIGN_CENTER)
//        estimasi.setBackgroundColor(BaseColor.RED)
//        table.addCell(estimasi)
//
//        val paragraf8 = Paragraph(databrg.harga_brg.toString(),fontnormalcolorblack)
////        paragraf8.add(Chunk(VerticalPositionMark()))
////        paragraf8.add(databrg.jumlah_brg)
////        table.addCell(estimasi)
//        table.addCell(paragraf8)
//        document.add(table)
//
//
//        var stok = PdfPCell(Paragraph("STOK SAAT INI"))
//        stok.setColspan(4)
//        stok.setHorizontalAlignment(Element.ALIGN_CENTER)
//        stok.setBackgroundColor(BaseColor.RED)
//        table.addCell(stok)
//
//
//
//        val paragraf9 = Paragraph(databrg.jumlah_brg.toString(),fontnormalcolorblack)
//        paragraf9.add(Chunk(VerticalPositionMark()))
////        paragraf9.add(databrg.harga_brg)
////        table.addCell(stok)
//        table.addCell(paragraf9)
//        document.add(table)


//        val paragraf10 = Paragraph("Alasan",fontnormalcolorblack)
//        paragraf10.add(Chunk(VerticalPositionMark()))
//        paragraf10.add(databrg.alasan)
//        document.add(paragraf10)
