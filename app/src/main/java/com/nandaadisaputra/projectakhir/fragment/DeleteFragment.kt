package com.nandaadisaputra.projectakhir.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.adapter.DeleteProductAdapter
import com.nandaadisaputra.projectakhir.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DeleteFragment : Fragment() {


    private var rVItem: RecyclerView? = null
    private var deleteProductAdapter: DeleteProductAdapter? = null
    private var productModel: ArrayList<ProductModel>? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_delete, container, false)
        rVItem = view.findViewById(R.id.rvItem)
        getData()
        return view
    }

    private fun getData() {
        productModel = ArrayList()

        val apiService = ApiConfig.getApiService()
        apiService.ambilData().enqueue(object : Callback<ArrayList<ProductModel>> {
            override fun onResponse(
                    call: Call<ArrayList<ProductModel>>,
                    response: Response<ArrayList<ProductModel>>
            ) {
                if (response.isSuccessful) {
                    productModel?.clear()
                    productModel = response.body()
                    deleteProductAdapter = DeleteProductAdapter(activity, productModel)
                    rVItem?.layoutManager = GridLayoutManager(activity, 2)
                    rVItem?.adapter = deleteProductAdapter
                    deleteProductAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModel>>, t: Throwable) {
                Toast.makeText(activity, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}
