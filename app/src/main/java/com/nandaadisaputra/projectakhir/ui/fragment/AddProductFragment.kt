package com.nandaadisaputra.projectakhir.ui.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.product.ProductActivity
import kotlinx.android.synthetic.main.fragment_add_item.*
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductFragment : Fragment() {

    private var stockProduct = 0

    var btnSendProduct: Button? = null
    var btnCheckUrl: Button? = null
    var iconAdd: ImageView? = null
    var iconLess: ImageView? = null
    var edtStockProduct: EditText? = null
    var edtNameProduct: EditText? = null
    var edtUrlImage: EditText? = null
    var edtDescriptionImage: EditText? = null
    var edtPriceProduct: EditText? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_item, container, false)
        btnSendProduct = view.findViewById(R.id.btn_send_product)
        btnCheckUrl = view.findViewById(R.id.btn_check_url)
        iconAdd = view.findViewById(R.id.icon_plus)
        iconLess = view.findViewById(R.id.icon_minus)
        edtStockProduct = view.findViewById(R.id.edt_stock_product)
        edtNameProduct = view.findViewById(R.id.edt_name_product)
        edtUrlImage = view.findViewById(R.id.edt_url_image)
        edtDescriptionImage = view.findViewById(R.id.edt_description_product)
        edtPriceProduct = view.findViewById(R.id.edt_price_product)

        btnSendProduct?.setOnClickListener {

            val apiService = ApiConfig.getApiService()
            apiService.addData(
                    edtNameProduct?.text.toString().trim(),
                    edtUrlImage?.text.toString().trim(),
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
                                edtUrlImage?.setText("")
                                edtDescriptionImage?.setText("")
                                edtPriceProduct?.setText("")
                                edtStockProduct?.setText("")

                                activity?.finishAffinity()
                                val intent = Intent(context, ProductActivity::class.java)
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

        btnCheckUrl?.setOnClickListener {
            //TODO Check url image is empty or not
            if (edtUrlImage?.text.toString().isEmpty()) {
                edtUrlImage?.error = "Please enter the image url"
                edtUrlImage?.requestFocus()
            } else {
                Glide.with(activity!!)
                        .load(edtUrlImage?.text.toString())
                        .addListener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                    @Nullable e: GlideException?, model: Any,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                            ): Boolean {
                                toast("Invalid Url")
                                return false
                            }

                            override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }
                        })
                        .into(iv_photo)
            }
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


}
