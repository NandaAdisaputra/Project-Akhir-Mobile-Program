package com.nandaadisaputra.projectakhir.ui.activity.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.adapter.DeleteProductAdapter
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DeleteActivity : AppCompatActivity() {
    private var rvItem: RecyclerView? = null
    private var deleteProductAdapter: DeleteProductAdapter? = null
    private var productModel: ArrayList<ProductModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        rvItem = findViewById(R.id.rvItem)
        getData()
    }
    private fun getData() {
        productModel = ArrayList()

        val apiService = ApiConfig.getApiService()
        apiService.getData().enqueue(object : Callback<ArrayList<ProductModel>> {
            override fun onResponse(
                    call: Call<ArrayList<ProductModel>>,
                    response: Response<ArrayList<ProductModel>>
            ) {
                if (response.isSuccessful) {
                    productModel?.clear()
                    productModel = response.body()
                    deleteProductAdapter = DeleteProductAdapter(this@DeleteActivity, productModel)
                    rvItem?.layoutManager = GridLayoutManager(this@DeleteActivity, 2)
                    rvItem?.adapter = deleteProductAdapter
                    deleteProductAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModel>>, t: Throwable) {
                Toast.makeText(this@DeleteActivity, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
