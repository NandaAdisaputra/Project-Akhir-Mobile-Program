package com.nandaadisaputra.projectakhir.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.product.ProductDetailsActivity
import com.nandaadisaputra.projectakhir.model.show.ProductModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class ShowProductAdapter(private val context: FragmentActivity?,
                         private val productModel: ArrayList<ProductModel>?
) : RecyclerView.Adapter<ShowProductAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.iv_product)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvProductPrices: TextView = view.findViewById(R.id.tv_prices)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.list_item, parent,
                        false))
    }

    override fun getItemCount(): Int {
        return productModel!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = productModel!![position].namaBarang
        holder.tvProductPrices.text = productModel!![position].hargaBarang
        context?.let {
            Glide.with(it)
                    .load(productModel[position].imageBarang)
                    .override(512, 512)
                    .into(holder.ivProduct)
        }

        holder.itemView.onClick {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("ID_BARANG", productModel[position].idBarang)
            intent.putExtra("NAMA_BARANG", productModel[position].namaBarang)
            intent.putExtra("IMAGE_BARANG", productModel[position].imageBarang)
            intent.putExtra("DESKRIPSI_BARANG", productModel[position].deskripsiBarang)
            intent.putExtra("HARGA_BARANG", productModel[position].hargaBarang)
            intent.putExtra("STOK_BARANG", productModel[position].stokBarang)
            context?.startActivity(intent)
        }
    }


}