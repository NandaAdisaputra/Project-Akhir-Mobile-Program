package com.nandaadisaputra.projectakhir.ui.activity.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.adapter.ShowProductUserAdapter
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.network.ApiConfig
import kotlinx.android.synthetic.main.activity_show_user.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ShowUserActivity : AppCompatActivity() {
    var productModels: ArrayList<ProductModel>? = null
    var showProductUserAdapter: ShowProductUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user)
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
                    showProductUserAdapter = ShowProductUserAdapter(this@ShowUserActivity, productModels)
                    rvProductUser.adapter = showProductUserAdapter
                    rvProductUser.layoutManager = GridLayoutManager(this@ShowUserActivity, 2)
                    showProductUserAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModel>>, t: Throwable) {
                toast("There is no internet network")

            }
        })

    }
}
