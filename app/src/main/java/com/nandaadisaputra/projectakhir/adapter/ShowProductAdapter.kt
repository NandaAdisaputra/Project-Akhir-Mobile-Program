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
import com.nandaadisaputra.projectakhir.activity.item.ProductDetailsActivity
import com.nandaadisaputra.projectakhir.model.ProductModel
import java.util.*

class ShowProductAdapter(private val context: FragmentActivity?,
                         private val produkModel: ArrayList<ProductModel>?
) : RecyclerView.Adapter<ShowProductAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iVProduk: ImageView = view.findViewById(R.id.IVProduk)
        val tvNama: TextView = view.findViewById(R.id.TVNama)
        val tvHargaProduk: TextView = view.findViewById(R.id.TVHarga)
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
        return produkModel!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = produkModel!!.get(position).namaBarang
        holder.tvHargaProduk.text = produkModel!!.get(position).hargaBarang
        context?.let {
            Glide.with(it)
                    .load(produkModel.get(position).imageBarang)
                    .override(512, 512)
                    .into(holder.iVProduk)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("ID_BARANG", produkModel.get(position).idBarang)
            intent.putExtra("NAMA_BARANG", produkModel.get(position).namaBarang)
            intent.putExtra("IMAGE_BARANG", produkModel.get(position).imageBarang)
            intent.putExtra("DESKRIPSI_BARANG", produkModel.get(position).deskripsiBarang)
            intent.putExtra("HARGA_BARANG", produkModel.get(position).hargaBarang)
            intent.putExtra("STOK_BARANG", produkModel.get(position).stokBarang)
            context?.startActivity(intent)
        }
    }


}