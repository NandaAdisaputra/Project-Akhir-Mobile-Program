package com.nandaadisaputra.projectakhir.activity.item

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.activity.MainActivity
import com.nandaadisaputra.projectakhir.network.ApiConfig
import kotlinx.android.synthetic.main.activity_update_product.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProductActivity : AppCompatActivity() {

    private var idBarang: String? = null
    private var namaBarang: String? = null
    private var imageBarang: String? = null
    private var deskripsiBarang: String? = null
    private var hargaBarang: String? = null
    private var stokBarang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)
        idBarang = intent.getStringExtra("ID_BARANG")
        namaBarang = intent.getStringExtra("NAMA_BARANG")
        imageBarang = intent.getStringExtra("IMAGE_BARANG")
        deskripsiBarang = intent.getStringExtra("DESKRIPSI_BARANG")
        hargaBarang = intent.getStringExtra("HARGA_BARANG")
        stokBarang = intent.getStringExtra("STOK_BARANG")

        edt_nama_barang_update.setText(namaBarang)
        edt_url_gambar_update.setText(imageBarang)
        edt_deskripsi_barang_update.setText(deskripsiBarang)
        edt_harga_barang_update.setText(hargaBarang)
        edt_stok_barang_update.setText(stokBarang)

        btn_update_produk.setOnClickListener {
            updateBarang()
        }
    }

    private fun updateBarang() {
        val apiService = ApiConfig.getApiService()
        apiService.updateData(idBarang!!,
                edt_nama_barang_update.text.toString().trim(),
                edt_url_gambar_update.text.toString().trim(),
                edt_deskripsi_barang_update.text.toString().trim(),
                edt_harga_barang_update.text.toString().trim(),
                edt_stok_barang_update.text.toString().trim())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                    this@UpdateProductActivity, "Sukses Diperbarui",
                                    Toast.LENGTH_SHORT
                            ).show()
                            finishAffinity()
                            startActivity(Intent(applicationContext, MainActivity::class.java))

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                                this@UpdateProductActivity, "" + t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

}
