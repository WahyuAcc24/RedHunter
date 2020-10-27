package com.wr15.redhunter.GA

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.android.volley.*
import com.bumptech.glide.Glide
import com.wr15.redhunter.Adapter.HistoryInventoryAdapter
import com.wr15.redhunter.R
import com.wr15.redhunter.Retrofit.ApiServices
import com.wr15.redhunter.Retrofit.RemoteData
import com.wr15.redhunter.Retrofit.RetrofitBuilder
import com.wr15.redhunter.Retrofit.ServerResponse
import com.wr15.redhunter.Server.ServerHost
import com.wr15.redhunter.model.MFile
import kotlinx.android.synthetic.main.form_kelola_barang.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

class FormKelolaBarangGa : AppCompatActivity(){

    private lateinit var edt_nama_brg : AutoCompleteTextView
    private lateinit var edt_satuan_brg : EditText
//    private lateinit var edt_harga_brg : EditText
    private lateinit var edt_jmlh_brg : EditText
//    private lateinit var edt_nama_user : EditText
    private lateinit var edt_divisi : EditText
    private lateinit var btn_barang : Button
    private lateinit var btn_pilih_foto : Button
    private lateinit var imguser : ImageView

    private var mediaPath: String? = null
    private var postPath: String? = null
    private var mImageFileLocation = ""

    private var requesQueue: RequestQueue? = null

    private lateinit var pglistbrg: ProgressBar

    var mfile: List<MFile>? = null
    private var adapterfile: HistoryInventoryAdapter? = null
    private lateinit var lstHistoribrg: RecyclerView


//    var urlbrglist = ServerHost.url + "list_brgdev.php"

    val URL_brg = ServerHost.url + "insert_brg.php"

    internal var tag_json_obj = "json_obj_req"

    lateinit var pDialog: ProgressDialog



    internal lateinit var conMgr: ConnectivityManager

    private val TAG = FormKelolaBarangGa::class.java.getSimpleName()



    var bitmap_size: Int = 60

    var max_resolution_image : Int = 800

    private val READ_REQUEST_CODE: Int = 42
    var bitmapfoto: Bitmap? = null
    var decoded: Bitmap? = null
    var filefoto: Uri? = null

    private val request by lazy {
        RetrofitBuilder.builder().create(ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_kelola_barang)

        edt_nama_brg =  findViewById(R.id.edtnamabrg)
        edt_jmlh_brg =  findViewById(R.id.edtjmlhbrg)
//        edt_harga_brg = findViewById(R.id.edthargabrg)
        edt_satuan_brg =  findViewById(R.id.edtnamasatuanbrg)
//        edt_nama_user = findViewById(R.id.edtnamauserkelola)
//        edt_divisi = findViewById(R.id.edtdivisi)
        btn_barang =  findViewById(R.id.btnkelolabrg)
        btn_pilih_foto = findViewById(R.id.btnPilihgambar)
        imguser = findViewById(R.id.imgGambar)

//        edt_harga_brg.addTextChangedListener(NumberTextWatcher(edt_harga_brg))

        var remoteData = RemoteData(this)
        remoteData.getDataBarang()

        edt_nama_brg.setOnItemClickListener(onItemClickListener)



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            btn_pilih_foto.setEnabled(false)

            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)

        }else{
            btn_pilih_foto.setEnabled(true)
        }


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


        btn_pilih_foto.setOnClickListener {
            pilihGambar()
        }



        btn_barang.setOnClickListener {

            uploadFoto()


        }



        edtnamasatuanbrg.setOnFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasfocus:Boolean) {
                if (hasfocus)
                {
                    nama_satuan_barang.setHint("Nama Satuan Barang")
                    edtnamasatuanbrg.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
                else
                {
                    nama_satuan_barang.setHint("Lusin/gross/kodi/rim/lembar/ dll")
                }
            }
        })



    }


    private val onItemClickListener = object: AdapterView.OnItemClickListener {
        override fun onItemClick(adapterView:AdapterView<*>, view:View, i:Int, l:Long) {
            Toast.makeText(this@FormKelolaBarangGa,
                ("Clicked item " + adapterView.getItemAtPosition(i)), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<String>, grantResults:IntArray) {
        if (requestCode == 0)
        {
            if ((grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED))
            {
                btn_pilih_foto.setEnabled(true)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    imguser.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()


                    postPath = mediaPath
                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(imguser)
                    postPath = mImageFileLocation

                } else {
                    Glide.with(this).load(filefoto).into(imguser)
                    postPath = filefoto!!.path

                }

            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }

    }
    private fun pilihGambar(){

        MaterialDialog.Builder(this)
            .title("Pilih Foto")
            .items(R.array.uploadImages)
            .itemsIds(R.array.itemIds)
            .itemsCallback { dialog, view, which, text ->

                when (which) {
                    0 -> {
                        val galleryIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
                    }
                    1 -> ambilFoto()
                    2 -> imguser.setImageResource(R.drawable.redhunter)
                }
            }
            .show()


    }


    private fun uploadFoto(){

        val loading = ProgressDialog(this)
        loading.setCancelable(false)
        loading.setMessage("Menambahkan data...")

        val nama_brg = edt_nama_brg.text.toString()
        val satuan_brg = edt_satuan_brg.text.toString()
//            val harga_brg =  edt_harga_brg.text.toString()
        val jmlh_brg = edt_jmlh_brg.text.toString()
//            val nama_user = edt_nama_user.text.toString()
//            val divisi = Rak.grab("divisiuser") as String


        postPath?.let {
            loading.show()

            val file = File(it)
            val requestBody = RequestBody.create(MediaType.parse("*/*"), file)

            request.uploadGambar(
                namaBarang = nama_brg.toMultiPartBody("nama_brg"),
                satuanBarang = satuan_brg.toMultiPartBody("satuan_brg"),
                hargaBarang = "".toMultiPartBody("harga_brg"),
                jumlahBarang = jmlh_brg.toMultiPartBody("jmlh_brg"),
                status = "done".toMultiPartBody("status"),
                divisi = "done".toMultiPartBody("divisi"),
                namaUser = "done".toMultiPartBody("nama_user"),
                file = createFormData("images", file.name, requestBody)
            ).enqueue(object : Callback<ServerResponse> {
                override fun onResponse(
                    call: Call<ServerResponse>,
                    response: retrofit2.Response<ServerResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            loading.dismiss()
                            val serverResponse = response.body()
                            Toast.makeText(applicationContext, serverResponse?.message, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        loading.dismiss()
                        Toast.makeText(applicationContext, "problem uploading image", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                    loading.dismiss()
                    Log.v("Response gotten is", t.message)
                }
            })
        }?: Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
    }

    fun String.toMultiPartBody(key: String) = createFormData(key, this)

    private fun ambilFoto(){

        if (Build.VERSION.SDK_INT > 23) { //use this if Lollipop_Mr1 (API 22) or above
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE

            // We give some instruction to the intent to save the image
            var photoFile: File? = null

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile()
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (e: IOException) {
                Logger.getAnonymousLogger().info("Exception error in generating the file")
                e.printStackTrace()
            }

            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            val outputUri : Uri = FileProvider.getUriForFile(
                this@FormKelolaBarangGa,
                "com.wr15.redhunter.fileprovider",
                photoFile!!
            )
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

            Logger.getAnonymousLogger().info("Calling the camera App by intent")


            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            filefoto = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, filefoto)

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }


    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        Logger.getAnonymousLogger().info("Generating the image - method started")

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        // Here we specify the environment location and the exact path where we want to save the so-created file
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app")
        Logger.getAnonymousLogger().info("Storage directory set")

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir()

        // Here we create the file using a prefix, a suffix and a directory
        val image = File(storageDirectory, imageFileName + ".jpg")
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set")

        mImageFileLocation = image.absolutePath
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", filefoto)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        filefoto = savedInstanceState.getParcelable("file_uri")
    }
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }


    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        private val TAG = FormKelolaBarangGa::class.java.getSimpleName()

        private val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        private fun getOutputMediaFile(type: Int): File? {

            // External sdcard location
            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory")
                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }

            return mediaFile
        }


}
}