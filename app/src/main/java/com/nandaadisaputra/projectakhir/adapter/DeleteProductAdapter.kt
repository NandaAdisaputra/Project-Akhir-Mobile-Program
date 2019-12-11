package com.nandaadisaputra.projectakhir.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.model.ProductModel
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.product.ProductActivity
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class DeleteProductAdapter(private val context: FragmentActivity?,
                           private val productModel: ArrayList<ProductModel>?
)
    : RecyclerView.Adapter<DeleteProductAdapter.ViewHolder>()

{
    private var id: String? = null
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val ivProduct:ImageView = view.findViewById(R.id.iv_product)
        val tvName:TextView = view.findViewById(R.id.tv_name)
        val tvProductPrices:TextView = view.findViewById(R.id.tv_prices)
        val btnDelete:Button = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_delete, parent,
                false))
    }

    override fun getItemCount(): Int {
        return productModel!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = productModel!![position].nameProduct
        holder.tvProductPrices.text = productModel!![position].priceProduct
        context?.let {
            Glide.with(it)
                .load(productModel[position].imageProduct)
                .override(512, 512)
                .into(holder.ivProduct)
        }

        holder.btnDelete.onClick {
            id = productModel[position].idProduct
            val apiService = ApiConfig.getApiService()
            id?.let { it1 ->
                apiService.deleteData(it1).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            try {
                                val jsonObject = JSONObject(response.body()!!.string())
                                val error = jsonObject.optString("error_msg")
                                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show()
                                context?.startActivity(Intent(context, ProductActivity::class.java))
                                (context as Activity).finishAffinity()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

    }

}