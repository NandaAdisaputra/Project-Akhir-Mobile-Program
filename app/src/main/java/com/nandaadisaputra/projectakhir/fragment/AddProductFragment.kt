package com.nandaadisaputra.projectakhir.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.activity.MainActivity
import com.nandaadisaputra.projectakhir.network.ApiConfig
import kotlinx.android.synthetic.main.fragment_add_item.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductFragment : Fragment() {

    private var stokBarang = 0

    var btnKirimProduk: Button? = null
    var btnCekUrl: Button? = null
    var iconTambah: ImageView? = null
    var iconKurang: ImageView? = null
    var edtStokProduk: EditText? = null
    var edtNamaBarang: EditText? = null
    var edtUrlGambar: EditText? = null
    var edtDeskripsiBarang: EditText? = null
    var edtHargaBarang: EditText? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_item ,container, false)
        btnKirimProduk= view.findViewById(R.id.btn_kirim_produk)
        btnCekUrl= view.findViewById(R.id.btn_cek_url)
        iconTambah= view.findViewById(R.id.icon_plus)
        iconKurang= view.findViewById(R.id.icon_minus)
        edtStokProduk= view.findViewById(R.id.edt_stok_barang)

        edtStokProduk= view.findViewById(R.id.edt_stok_barang)
        edtNamaBarang= view.findViewById(R.id.edt_nama_barang)
        edtUrlGambar= view.findViewById(R.id.edt_url_gambar)
        edtDeskripsiBarang= view.findViewById(R.id.edt_deskripsi_barang)
        edtHargaBarang= view.findViewById(R.id.edt_harga_barang)

        btnKirimProduk?.setOnClickListener {

            val apiService = ApiConfig.getApiService()
            apiService.tambahData(
                    edtNamaBarang?.text.toString().trim(),
                    edtUrlGambar?.text.toString().trim(),
                    edtDeskripsiBarang?.text.toString().trim(),
                    edtHargaBarang?.text.toString().trim(),
                    edtStokProduk?.text.toString().trim()
            )
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                        activity, "Sukses Tambah",
                                        Toast.LENGTH_SHORT
                                ).show()
                                edtNamaBarang?.setText("")
                                edtUrlGambar?.setText("")
                                edtDeskripsiBarang?.setText("")
                                edtHargaBarang?.setText("")
                                edtStokProduk?.setText("")

                                activity?.finishAffinity()
                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)

                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(
                                    activity, "" + t.message,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }

        btnCekUrl?.setOnClickListener {
            //TODO Check url image is empty or not
            if (edtUrlGambar?.getText().toString().isEmpty()) {
                edtUrlGambar?.setError("Silahkan masukkan url gambar")
                edtUrlGambar?.requestFocus()
            } else {
                Glide.with(activity!!)
                        .load(edtUrlGambar?.getText().toString())
                        .addListener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                    @Nullable e: GlideException?, model: Any,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                            ): Boolean {
                                Toast.makeText(activity, "Url Tidak Valid", Toast.LENGTH_SHORT).show()
                                return false
                            }

                            override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }
                        })
                        .into(iv_photo)
            }
        }

        edtStokProduk?.setText("" + stokBarang)
        iconTambah?.setOnClickListener {
            if (edtStokProduk?.text.toString().isEmpty()) {
                resetStokBarang()
                tambahStokBarang()
            } else {
                tambahStokBarang()
            }
        }

        iconKurang?.setOnClickListener {
            if (edtStokProduk?.text.toString().isEmpty()) {
                resetStokBarang()
                kurangStokBarang()
            } else {
                kurangStokBarang()
            }
        }

        return view
    }


    private fun kurangStokBarang() {
        try {
            stokBarang = Integer.parseInt(edtStokProduk?.text.toString().trim())
            if (stokBarang == 0) {
                Toast.makeText(activity, "Barang tidak bisa kurang dari 0", Toast.LENGTH_SHORT)
                        .show()
            } else {
                stokBarang = stokBarang - 1
                edtStokProduk?.setText("" + stokBarang)
            }
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            kurangStokBarang()
        }

    }

    private fun resetStokBarang() {
        stokBarang = 0
        edtStokProduk?.setText("" + stokBarang)
    }

    private fun tambahStokBarang() {
        try {
            stokBarang = Integer.parseInt(edtStokProduk?.text.toString().trim())
            stokBarang = stokBarang + 1
            edtStokProduk?.setText("" + stokBarang)
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            tambahStokBarang()
        }

    }


}
