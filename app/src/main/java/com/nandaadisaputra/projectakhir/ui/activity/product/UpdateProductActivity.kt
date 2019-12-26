package com.nandaadisaputra.projectakhir.ui.activity.product

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_update_product.*
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProductActivity : AppCompatActivity() {

    private var idProduct: String? = null
    private var nameProduct: String? = null
    private var imageProduct: String? = null
    private var descriptionProduct: String? = null
    private var priceProduct: String? = null
    private var stockProduct: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)
        idProduct = intent.getStringExtra("ID_PRODUCT")
        nameProduct = intent.getStringExtra("NAME_PRODUCT")
        imageProduct = intent.getStringExtra("IMAGE_PRODUCT")
        descriptionProduct = intent.getStringExtra("DESCRIPTION_PRODUCT")
        priceProduct = intent.getStringExtra("PRICE_PRODUCT")
        stockProduct = intent.getStringExtra("STOCK_PRODUCT")

        edt_name_product_update.setText(nameProduct)
        edt_url_image_update.setText(imageProduct)
        edt_description_product_update.setText(descriptionProduct)
        edt_price_product_update.setText(priceProduct)
        edt_stock_product_update.setText(stockProduct)

        btn_update_product.onClick{
            updateBarang()
        }
    }

    private fun updateBarang() {
        val apiService = ApiConfig.getApiService()
        apiService.updateData(idProduct!!,
                edt_name_product_update.text.toString().trim(),
                edt_url_image_update.text.toString().trim(),
                edt_description_product_update.text.toString().trim(),
                edt_price_product_update.text.toString().trim(),
                edt_stock_product_update.text.toString().trim())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            toast("Success Updated")
                            finishAffinity()
                            startActivity< MainActivity>()

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                                this@UpdateProductActivity, "" + t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

}
