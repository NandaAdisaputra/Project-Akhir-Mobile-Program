package com.nandaadisaputra.projectakhir.ui.activity.product

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.content_detail_product.*
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsActivity : AppCompatActivity() {

    private var idProduct: String? = null
    private var nameProduct: String? = null
    private var imageProduct: String? = null
    private var descriptionProduct: String? = null
    private var priceProduct: String? = null
    private var stockProduct: String? = null

    private var myMediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        setSupportActionBar(toolbar)
        fab.onClick{ view ->
            if (view != null) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myMediaPlayer = MediaPlayer.create(this, R.raw.battle)
        idProduct = intent.getStringExtra("ID_PRODUCT")
        nameProduct = intent.getStringExtra("NAME_PRODUCT")
        imageProduct = intent.getStringExtra("IMAGE_PRODUCT")
        descriptionProduct = intent.getStringExtra("DESCRIPTION_PRODUCT")
        priceProduct= intent.getStringExtra("PRICE_PRODUCT")
        stockProduct = intent.getStringExtra("STOCK_PRODUCT")

        supportActionBar?.title = nameProduct
        Glide.with(this).load(imageProduct).override(512, 512)
                .into(ivBackdropProduct)

        tv_price_product.text = priceProduct
        tv_stock_product.text = stockProduct
        tv_description_product.text = descriptionProduct

        fab.setOnClickListener {
            myMediaPlayer?.start()
            val apiService = ApiConfig.getApiService()
            apiService.buyData(idProduct!!).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        toast("Success")
                        startActivity<MainActivity>()
                        finishAffinity()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    toast("" + t.message)
                }
            })
        }
        btn_edit.setOnClickListener {
            val intent = Intent(this, UpdateProductActivity::class.java)
            intent.putExtra("ID_PRODUCT", idProduct)
            intent.putExtra("NAME_PRODUCT", nameProduct)
            intent.putExtra("IMAGE_PRODUCT", imageProduct)
            intent.putExtra("DESCRIPTION_PRODUCT", descriptionProduct)
            intent.putExtra("PRICE_PRODUCT", priceProduct)
            intent.putExtra("STOCK_PRODUCT", stockProduct)
            startActivity(intent)
        }

    }
}
