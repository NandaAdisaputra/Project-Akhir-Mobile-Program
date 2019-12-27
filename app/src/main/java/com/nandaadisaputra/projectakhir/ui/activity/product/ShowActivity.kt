package com.nandaadisaputra.projectakhir.ui.activity.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.adapter.ShowProductAdapter
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.network.ApiConfig
import kotlinx.android.synthetic.main.activity_show.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ShowActivity : AppCompatActivity() {
    var productModels: ArrayList<ProductModel>? = null
    var showProductAdapter: ShowProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        productModels = ArrayList()
        getData()
    }

    private fun getData() {
        val apiService = ApiConfig.getApiService()
        apiService.getData().enqueue(object : Callback<ArrayList<ProductModel>> {
            override fun onResponse(
                    call: Call<ArrayList<ProductModel>>,
                    response: Response<ArrayList<ProductModel>>
            ) {
                if (response.isSuccessful) {
                    productModels = response.body()
                    showProductAdapter = ShowProductAdapter(this@ShowActivity, productModels)
                    rvProduct.adapter = showProductAdapter
                    rvProduct.layoutManager = GridLayoutManager(this@ShowActivity, 2)
                    showProductAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModel>>, t: Throwable) {
                toast("There is no internet network")

            }
        })

    }
}
