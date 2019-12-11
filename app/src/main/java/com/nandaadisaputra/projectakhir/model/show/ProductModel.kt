package com.nandaadisaputra.projectakhir.model.show

import com.google.gson.annotations.SerializedName

data class ProductModel(

	@field:SerializedName("id_barang")
	val idBarang: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("harga_barang")
	val hargaBarang: String? = null,

	@field:SerializedName("deskripsi_barang")
	val deskripsiBarang: String? = null,

	@field:SerializedName("image_barang")
	val imageBarang: String? = null,

	@field:SerializedName("stok_barang")
	val stokBarang: String? = null
)