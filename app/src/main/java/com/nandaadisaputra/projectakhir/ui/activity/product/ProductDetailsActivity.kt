package com.nandaadisaputra.projectakhir.ui.activity.product

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nandaadisaputra.projectakhir.R
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.content_detail_product.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProductDetailsActivity : AppCompatActivity() {

    private var idProduct: String? = null
    private var nameProduct: String? = null
    private var imageProduct: String? = null
    private var descriptionProduct: String? = null
    private var priceProduct: String? = null
    private var stockProduct: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        setSupportActionBar(toolbar)

        fab?.onClick{
            val sharingIntent = Intent(Intent.ACTION_VIEW)
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sharingIntent.data = Uri.parse("https://web.whatsapp.com/")
            startActivity(sharingIntent)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

        btn_edit.onClick{
            val intent = Intent(this@ProductDetailsActivity, UpdateProductActivity::class.java)
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
