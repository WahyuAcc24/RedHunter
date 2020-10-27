package com.wr15.redhunter.Hrd

import android.content.Context
import android.os.Environment
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import com.wr15.redhunter.model.MPdf
import com.wr15.redhunter.model.MPengajuan
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.util.*

class Pdf (private val dataPengajuan : MPdf, private val mContext : Context) {
//
//    private val localeID : Locale
//    private val formatRupiah : NumberFormat
//
//    init {
//        localeID = Locale("in","ID")
//        formatRupiah = NumberFormat.getCurrencyInstance(localeID)
//    }
//
//
//    fun savedPDF(){
//
//        var now = Date()
//        val filename = "PengajuanBarang.pdf"
//        val sdcard : File = Environment.getExternalStorageDirectory()
//        val path = File(sdcard.absolutePath + "/Pengajuan")
//
//        if(!path.exists())
//            path.mkdirs()
//
//        var file = File(path,filename)
//
//        if(file.exists()){
//            file.delete()
//            file = File(path,filename)
//        }
//
//        val document = Document()
//
//        PdfWriter.getInstance(document, FileOutputStream(file) as OutputStream?)
//
//        document.open()
//        document.pageSize = PageSize.A4
//
//        document.addCreationDate()
//        document.addAuthor("")
//        document.addCreator("HRD")
//
//
//        val mColorAccent = BaseColor(0,153,204,255)
//        val mHeadingFontSize = 20.0f
//        val mValueFontSize = 26.0f
//
//        val fontMontserrat = BaseFont.createFont("res/font/montserratregular.ttf", "UTF-8", BaseFont.EMBEDDED)
//        val lineSeparator =  LineSeparator()
//        lineSeparator.lineColor = BaseColor(0,0,0,0)
//
//        val fontmontserratHeader = Font(
//            fontMontserrat,36.0f, Font.NORMAL,BaseColor.BLACK)
//
//        val fontnormalcolorblack = Font(
//            fontMontserrat,mValueFontSize, Font.NORMAL,BaseColor.BLACK)
//
//        val fontnormalcolorblue = Font(
//            fontMontserrat,mValueFontSize, Font.NORMAL,BaseColor.BLUE)
//
//        val fontheading = Font(
//            fontMontserrat,mHeadingFontSize, Font.NORMAL,BaseColor.BLACK)
//
//
//
//        val paragraf1 = Paragraph(Chunk("Pengajuan Barang",fontmontserratHeader))
//        paragraf1.alignment = Element.ALIGN_CENTER
//        document.add(paragraf1)
//
//        val paragraf2 = Paragraph(Chunk("Tanggal : " + dataPengajuan.tanggal,fontnormalcolorblue))
//        paragraf2.alignment = Element.ALIGN_LEFT
//        document.add(paragraf2)
//
//
//        //garis 1
//        document.add(Paragraph(""))
//        document.add(Chunk(lineSeparator))
//        document.add(Paragraph(""))
//
//        val paragraf3 = Paragraph("Status Pengajuan",fontnormalcolorblack)
//        paragraf3.add(Chunk(VerticalPositionMark()))
//        paragraf3.add(dataPengajuan.status)
//
//
//        //garis 2
//        document.add(Paragraph(""))
//        document.add(Chunk(lineSeparator))
//        document.add(Paragraph(""))
//
//
//        val paragraf4 = Paragraph("Nama Karyawan",fontnormalcolorblack)
//        paragraf4.add(Chunk(VerticalPositionMark()))
//        paragraf4.add(dataPengajuan.nama_user)
//
//
//        val paragraf5 = Paragraph("Divisi",fontnormalcolorblack)
//        paragraf5.add(Chunk(VerticalPositionMark()))
//        paragraf5.add(dataPengajuan.divisi)
//
//
//        val paragraf6 = Paragraph("Barang yang diajukan",fontnormalcolorblack)
//        paragraf6.add(Chunk(VerticalPositionMark()))
//        paragraf6.add(dataPengajuan.nama_barang)
//
//        val paragraf7 = Paragraph("Jenis Satuan",fontnormalcolorblack)
//        paragraf7.add(Chunk(VerticalPositionMark()))
//        paragraf7.add(dataPengajuan.satuan_barang)
//
//
//        val paragraf8 = Paragraph("Jumlah Barang",fontnormalcolorblack)
//        paragraf8.add(Chunk(VerticalPositionMark()))
//        paragraf8.add(dataPengajuan.jumlah_barang)
//
//        val paragraf9 = Paragraph("Harga",fontnormalcolorblack)
//        paragraf9.add(Chunk(VerticalPositionMark()))
//        paragraf9.add(dataPengajuan.harga_barang)
//
//        val paragraf10 = Paragraph("Alasan",fontnormalcolorblack)
//        paragraf10.add(Chunk(VerticalPositionMark()))
//        paragraf10.add(dataPengajuan.alasan)
//
//
//        //garis 3
//        document.add(Paragraph(""))
//        document.add(Chunk(lineSeparator))
//        document.add(Paragraph(""))
//
//
//        val paragraf11 = Paragraph("",fontnormalcolorblack)
//        paragraf11.add(Chunk(VerticalPositionMark()))
//        paragraf11.add("Tanda Tangan")
//
//
//        document.close()
//
//
//
//    }
//
//

}