package com.nandaadisaputra.projectakhir.model

import com.google.gson.annotations.SerializedName

data class ProductModel(

	@field:SerializedName("id_barang")
	val idProduct: String? = null,

	@field:SerializedName("nama_barang")
	val nameProduct: String? = null,

	@field:SerializedName("harga_barang")
	val priceProduct: String? = null,

	@field:SerializedName("deskripsi_barang")
	val descriptionProduct: String? = null,

	@field:SerializedName("image_barang")
	val imageProduct: String? = null,

	@field:SerializedName("stok_barang")
	val stockProduct: String? = null
)