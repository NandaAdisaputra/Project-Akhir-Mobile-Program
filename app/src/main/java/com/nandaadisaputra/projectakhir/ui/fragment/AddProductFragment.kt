package com.nandaadisaputra.projectakhir.ui.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import com.nandaadisaputra.projectakhir.ui.activity.Utility
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class AddProductFragment : Fragment() {

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var btnSelect: ImageButton? = null
    private var ivImage: ImageView? = null
    private var userChoosenTask: String? = null
    private var stockProduct = 0
    var btnSendProduct: Button? = null
    var iconAdd: ImageView? = null
    var iconLess: ImageView? = null
    var edtStockProduct: EditText? = null
    var edtNameProduct: EditText? = null
    var edtDescriptionImage: EditText? = null
    var edtPriceProduct: EditText? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_item, container, false)
        //take photo
        btnSelect = view.findViewById(R.id.ivImage) as ImageButton
        btnSelect?.setOnClickListener { selectImage() }
        ivImage = view.findViewById(R.id.ivImage) as ImageView

        btnSendProduct = view.findViewById(R.id.btn_send_product)
        iconAdd = view.findViewById(R.id.icon_plus)
        iconLess = view.findViewById(R.id.icon_minus)
        edtStockProduct = view.findViewById(R.id.edt_stock_product)
        edtNameProduct = view.findViewById(R.id.edt_name_product)
        edtDescriptionImage = view.findViewById(R.id.edt_description_product)
        edtPriceProduct = view.findViewById(R.id.edt_price_product)

        btnSendProduct?.setOnClickListener {

            val apiService = ApiConfig.getApiService()
            apiService.addData(
                    edtNameProduct?.text.toString().trim(),
                    edtDescriptionImage?.text.toString().trim(),
                    edtPriceProduct?.text.toString().trim(),
                    edtStockProduct?.text.toString().trim()
            )
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                        activity, "Sukses Tambah",
                                        Toast.LENGTH_SHORT
                                ).show()
                                edtNameProduct?.setText("")
                                edtDescriptionImage?.setText("")
                                edtPriceProduct?.setText("")
                                edtStockProduct?.setText("")

                                activity?.finishAffinity()
                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)

                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(
                                    activity, "" + t.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

        }

        edtStockProduct?.setText("" + stockProduct)
        iconAdd?.setOnClickListener {
            if (edtStockProduct?.text.toString().isEmpty()) {
                resetStokBarang()
                tambahStokBarang()
            } else {
                tambahStokBarang()
            }
        }

        iconLess?.setOnClickListener {
            if (edtStockProduct?.text.toString().isEmpty()) {
                resetStokBarang()
                kurangStokBarang()
            } else {
                kurangStokBarang()
            }
        }

        return view
    }


    private fun kurangStokBarang() {
        try {
            stockProduct = Integer.parseInt(edtStockProduct?.text.toString().trim())
            if (stockProduct == 0) {
                toast("Item cannot be less than 0")
            } else {
                stockProduct -= 1
                edtStockProduct?.setText("" + stockProduct)
            }
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            kurangStokBarang()
        }

    }

    private fun resetStokBarang() {
        stockProduct = 0
        edtStockProduct?.setText("" + stockProduct)
    }

    private fun tambahStokBarang() {
        try {
            stockProduct = Integer.parseInt(edtStockProduct?.text.toString().trim())
            stockProduct += 1
            edtStockProduct?.setText("" + stockProduct)
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            tambahStokBarang()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask == "Take Photo") cameraIntent() else if (userChoosenTask == "Choose from Library") galleryIntent()
                } else { //code for deny
                }
            }
        }
    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library",
                "Cancel")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            val result: Boolean = Utility.checkPermission(activity)
            if (items[item] == "Take Photo") {
                userChoosenTask = "Take Photo"
                if (result) cameraIntent()
            } else if (items[item] == "Choose from Library") {
                userChoosenTask = "Choose from Library"
                if (result) galleryIntent()
            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
    }

    private fun cameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) onSelectFromGalleryResult(data) else if (requestCode == REQUEST_CAMERA) data?.let { onCaptureImageResult(it) }
        }
    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!["data"] as Bitmap?
        val bytes = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val destination = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        ivImage!!.setImageBitmap(thumbnail)
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(activity
                        ?.applicationContext?.contentResolver, data.data)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        ivImage?.setImageBitmap(bm)
    }
}