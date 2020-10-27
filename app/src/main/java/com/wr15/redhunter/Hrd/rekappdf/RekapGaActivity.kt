package com.wr15.redhunter.Hrd.rekappdf

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.itextpdf.text.*
import com.itextpdf.text.List
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class RekapGaActivity : AppCompatActivity() {



    var JSON_array : String = "data_barang"

    var value_nama_user : String = "nama_user"
    var value_tgl : String = "tanggal"
    var value_status : String = "status"

    var value_nama_brg : String = "nama_brg"
    var value_id : String = "id"
    var value_jumlah : String = "jumlah_brg"

    var value_alasan : String = "alasan"
    var value_harga : String = "harga_brg"

    var nama_user : String = ""
    var tgl : String = ""
    var status : String = ""

    var nama_brg : String =""
    var id : String =""
    var jumlah_brg : String =""
    var alasan_brg : String =""
    var harga_brg : Int = 0

    var total : Int = 0



    var loading: ProgressDialog? = null

    val URL = ServerHost.url + "list_pb_ga.php"


    internal lateinit var conMgr: ConnectivityManager
    private var requesQueue: RequestQueue? = null

    private val TAG = RekapGaActivity::class.java.getSimpleName()


    lateinit var btn_rekap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_rekap)

        btn_rekap = findViewById(R.id.btnRekaptrainer)

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



       btn_rekap.setOnClickListener {

            requestPdf()


        }


    }

    private fun reportPDF() {

       savedPDF()

        loading?.dismiss()
        Toast.makeText(this, "FilePdf Berhasil disimpan", Toast.LENGTH_SHORT).show()

    }

    private fun requestPdf() {
        loading?.show()
        Dexter.withActivity(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {

                        reportPDF()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>,
                    token: PermissionToken
                ) {

                    token.continuePermissionRequest()

                }


            }).withErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
            .onSameThread()
            .check()

    }

    fun savedPDF() {

        loading?.show()

        val stringRequest = object : StringRequest(Method.GET,URL,
            Response.Listener<String> { response ->

                try {

                    Log.e(TAG, "Login Response: $response")

                        val document = Document()

                        var now = Date()
                        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
                        val filename = "PengajuanBarang " + " " + now + ".pdf"

                        val sdcard: File =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)


                        val path = File(sdcard.getAbsolutePath() + "/Pengajuan")



                        if (!path.exists())
                            path.mkdirs()

                        var file = File(path, filename)


                        PdfWriter.getInstance(document, FileOutputStream(file))

                        document.open()
                        document.pageSize = PageSize.A4

                        document.addCreationDate()
                        document.addAuthor("")
                        document.addCreator("HRD")


                        val mColorAccent = BaseColor(0, 153, 204, 255)
                        val mHeadingFontSize = 16.0f
                        val mValueFontSize = 8.0f

                        var table = PdfPTable(7)
                        table.setWidthPercentage(100F)
                        table.setWidths(floatArrayOf(15f, 30f, 20f, 30f, 30f, 30f, 30f))



                        var tablejumlah = PdfPTable(3)
                        tablejumlah.setWidthPercentage(100F)
                        tablejumlah.setWidths(floatArrayOf(30f, 20f, 20f))

                        var tabletgl = PdfPTable(1)
                        tabletgl.setWidthPercentage(100F)
                        tabletgl.setWidths(floatArrayOf(100f))

                        var listisitabel = List()


                        val fontMontserrat =
                            BaseFont.createFont(
                                "res/font/montserratregular.ttf",
                                "UTF-8",
                                BaseFont.EMBEDDED
                            )
                        val lineSeparator = LineSeparator()
                        lineSeparator.lineColor = BaseColor(0, 0, 0, 68)

                        val spasi = LineSeparator()
                        spasi.lineColor = BaseColor(0, 0, 0, 0)


                        val fontmontserratHeader = Font(
                            fontMontserrat, 18.0f, Font.BOLD, BaseColor.BLACK
                        )

                        val fontttd = Font(
                            fontMontserrat, mValueFontSize, Font.BOLD, BaseColor.BLACK
                        )


                        val fontnormalcolorblack = Font(
                            fontMontserrat, mValueFontSize, Font.NORMAL, BaseColor.BLACK
                        )

                        val fontnormalcolorwhite = Font(
                            fontMontserrat, mValueFontSize, Font.NORMAL, BaseColor.WHITE
                        )

                        val fontnormalcolorblue = Font(
                            fontMontserrat, mValueFontSize, Font.NORMAL, BaseColor.BLUE
                        )

                        val fontheading = Font(
                            fontMontserrat, mHeadingFontSize, Font.NORMAL, BaseColor.BLACK
                        )


                        var ims: InputStream = assets.open("rhcapsatu.png")
                        var bmp: Bitmap = BitmapFactory.decodeStream(ims)
                        var stream = ByteArrayOutputStream()
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val paragrafgambar: Image = Image.getInstance(stream.toByteArray())
                        paragrafgambar.scaleAbsoluteWidth(500.0f)
                        paragrafgambar.scaleAbsoluteHeight(150.0f)
                        document.add(paragrafgambar)

                        val paragraf1 = Paragraph(Chunk("PENGAJUAN BARANG", fontmontserratHeader))
                        paragraf1.alignment = Element.ALIGN_CENTER
                        document.add(paragraf1)

                        val paragraf2 = Paragraph(Chunk(tgl, fontnormalcolorblue))
                        paragraf2.alignment = Element.ALIGN_LEFT
                        document.add(paragraf2)


                        //garis 1
                        document.add(Paragraph(""))
                        document.add(Chunk(lineSeparator))
                        document.add(Paragraph(""))


                        //garis 2
                        document.add(Paragraph(""))
                        document.add(Chunk(lineSeparator))
                        document.add(Paragraph(""))



                        var nocell = PdfPCell(Paragraph("No", fontnormalcolorwhite))
                        nocell.setHorizontalAlignment(Element.ALIGN_CENTER)
                        nocell.setBackgroundColor(BaseColor.RED)


                        var jk = PdfPCell(Paragraph("JENIS KEBUTUHAN", fontnormalcolorwhite))
                        jk.setHorizontalAlignment(Element.ALIGN_CENTER)
                        jk.setBackgroundColor(BaseColor.RED)

                        var jumlah = PdfPCell(Paragraph("JUMLAH", fontnormalcolorwhite))
                        jumlah.setHorizontalAlignment(Element.ALIGN_CENTER)
                        jumlah.setBackgroundColor(BaseColor.RED)

                        var alasan = PdfPCell(Paragraph("TUJUAN PENGADAAN", fontnormalcolorwhite))
                        alasan.setHorizontalAlignment(Element.ALIGN_CENTER)
                        alasan.setBackgroundColor(BaseColor.RED)

                        var ket = PdfPCell(Paragraph("KETERANGAN", fontnormalcolorwhite))
                        ket.setHorizontalAlignment(Element.ALIGN_CENTER)
                        ket.setBackgroundColor(BaseColor.RED)

                        var harga = PdfPCell(Paragraph("HARGA (/item)", fontnormalcolorwhite))
                        harga.setHorizontalAlignment(Element.ALIGN_CENTER)
                        harga.setBackgroundColor(BaseColor.RED)

                        var estimasiharga =
                            PdfPCell(Paragraph("ESTIMASI HARGA", fontnormalcolorwhite))
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



                    var job: JSONObject = JSONObject(response)
                    var data_brg: JSONArray = job.getJSONArray(JSON_array)

                    for (v in 0 until data_brg.length()) {

                        var dapatData = data_brg.getJSONObject(v)

                        nama_user = dapatData.getString(value_nama_user)
                        tgl = dapatData.getString(value_tgl)
                        status = dapatData.getString(value_status)
                        nama_brg = dapatData.getString(value_nama_brg)
                        id = dapatData.getString(value_id)
                        jumlah_brg = dapatData.getString(value_jumlah)
                        alasan_brg = dapatData.getString(value_alasan)
                        harga_brg = dapatData.getInt(value_harga)


                        nocell = PdfPCell(Phrase(id, fontnormalcolorwhite))
                        nocell.setHorizontalAlignment(Element.ALIGN_CENTER)
                        nocell.setBackgroundColor(BaseColor.GRAY)

                        jk = PdfPCell(Phrase(nama_brg, fontnormalcolorblack))
                        jumlah = PdfPCell(Phrase(jumlah_brg, fontnormalcolorblack))
                        alasan = PdfPCell(Phrase(alasan_brg, fontnormalcolorblack))
                        ket = PdfPCell(Phrase(alasan_brg, fontnormalcolorblack))
                        harga = PdfPCell(Phrase(harga_brg.toString(), fontnormalcolorblack))
                        estimasiharga = PdfPCell(Phrase(harga_brg.toString(), fontnormalcolorblack))


                        table.addCell(nocell)
                        table.addCell(jk)
                        table.addCell(jumlah)
                        table.addCell(alasan)
                        table.addCell(ket)
                        table.addCell(harga)
                        table.addCell(estimasiharga)


                    }
                    document.add(table)


                        var jumlahtxt = PdfPCell(Paragraph("JUMLAH", fontnormalcolorwhite))
                        jumlahtxt.setHorizontalAlignment(Element.ALIGN_CENTER)
                        jumlahtxt.setBackgroundColor(BaseColor.BLUE)
                        jumlahtxt.setColspan(5)



                    var jmlh1 = PdfPCell(Paragraph("", fontnormalcolorwhite))
                        jmlh1.setHorizontalAlignment(Element.ALIGN_CENTER)
                        jmlh1.setBackgroundColor(BaseColor.BLUE)

                        var jmlh2 = PdfPCell(Paragraph("", fontnormalcolorwhite))
                        jmlh2.setHorizontalAlignment(Element.ALIGN_CENTER)
                        jmlh2.setBackgroundColor(BaseColor.BLUE)


                        tablejumlah.addCell(jumlahtxt)
                        tablejumlah.addCell(jmlh1)
                        tablejumlah.addCell(jmlh2)

                        document.add(tablejumlah)

                        document.add(Paragraph(""))
                        document.add(Chunk(lineSeparator))
                        document.add(Paragraph(""))


                        var tgltxtbawah = PdfPCell(Paragraph(tgl, fontnormalcolorwhite))
                        tgltxtbawah.setHorizontalAlignment(Element.ALIGN_CENTER)
                        tgltxtbawah.setBackgroundColor(BaseColor.BLACK)

                        tabletgl.addCell(tgltxtbawah)

                        document.add(tabletgl)

                        document.add(Paragraph(""))
                        document.add(Chunk(lineSeparator))
                        document.add(Paragraph(""))


                        val paragraf11 = Paragraph("Pemohon", fontnormalcolorblack)
                        paragraf11.add(Chunk(VerticalPositionMark()))
                        paragraf11.add("Disetujui")
                        document.add(paragraf11)

                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))

                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))


                        val ttduser = Paragraph(nama_user, fontttd)
                        ttduser.add(Chunk(VerticalPositionMark()))
                        ttduser.add("Himatul Mila")
                        document.add(ttduser)


                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))

                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))


                        val paragraf12 = Paragraph("Diketahui", fontnormalcolorblack)
                        paragraf12.add(Chunk(VerticalPositionMark()))
                        paragraf12.add("Penerima")
                        document.add(paragraf12)

                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))

                        document.add(Paragraph(""))
                        document.add(Chunk(spasi))
                        document.add(Paragraph(""))


                        val ttd = Paragraph("Juli Herniansyah", fontttd)
                        ttd.add(Chunk(VerticalPositionMark()))
                        ttd.add("Sico Ferry Ekel")
                        document.add(ttd)


                        document.close()


                        loading?.dismiss()

                        finish()




                }catch (e : JSONException){
                    e.printStackTrace()
                }

            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    loading?.dismiss()
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {

        }

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest)







    }

}
