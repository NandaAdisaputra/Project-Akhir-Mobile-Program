package com.nandaadisaputra.projectakhir.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.adapter.ShowProductAdapter
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.network.ApiConfig
import kotlinx.android.synthetic.main.fragment_show.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */

class ShowFragment : Fragment() {
    var produkModels: ArrayList<ProductModel>? = null
    var tampilProdukAdapter: ShowProductAdapter? = null

    var recyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_show, container, false)
        recyclerView = view.findViewById(R.id.rvProduk)
        produkModels = ArrayList()
        getData()
        return view
    }

    private fun getData() {
        val apiService = ApiConfig.getApiService()
        apiService.ambilData().enqueue(object : Callback<ArrayList<ProductModel>> {
            override fun onResponse(
                    call: Call<ArrayList<ProductModel>>,
                    response: Response<ArrayList<ProductModel>>
            ) {
                if (response.isSuccessful) {
                    produkModels = response.body()
                    tampilProdukAdapter = ShowProductAdapter(activity, produkModels)
                    rvProduk.setAdapter(tampilProdukAdapter)
                    rvProduk.setLayoutManager(GridLayoutManager(activity, 2))
                    tampilProdukAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductModel>>, t: Throwable) {
                Toast.makeText(
                        activity, "" + t.message,
                        Toast.LENGTH_SHORT
                ).show()

            }
        })

    }


}
