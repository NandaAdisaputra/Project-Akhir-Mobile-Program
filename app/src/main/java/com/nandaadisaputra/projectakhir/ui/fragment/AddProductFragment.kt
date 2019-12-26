package com.nandaadisaputra.projectakhir.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import okhttp3.ResponseBody
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductFragment : Fragment() {
    private var stockProduct = 0
    var btnSendProduct: Button? = null
    var iconAdd: ImageView? = null
    var iconLess: ImageView? = null
    var edtStockProduct: EditText? = null
    var edtNameProduct: EditText? = null
    var edtDescriptionImage: EditText? = null
    var edtPriceProduct: EditText? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_item, container, false)
        btnSendProduct = view.findViewById(R.id.btn_send_product)
        iconAdd = view.findViewById(R.id.icon_plus)
        iconLess = view.findViewById(R.id.icon_minus)
        edtStockProduct = view.findViewById(R.id.edt_stock_product)
        edtNameProduct = view.findViewById(R.id.edt_name_product)
        edtDescriptionImage = view.findViewById(R.id.edt_description_product)
        edtPriceProduct = view.findViewById(R.id.edt_price_product)

        btnSendProduct?.setOnClickListener {

            val apiService = ApiConfig.getApiService()
            apiService.addData(
                    edtNameProduct?.text.toString().trim(),
                    edtDescriptionImage?.text.toString().trim(),
                    edtPriceProduct?.text.toString().trim(),
                    edtStockProduct?.text.toString().trim()
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
                                edtNameProduct?.setText("")
                                edtDescriptionImage?.setText("")
                                edtPriceProduct?.setText("")
                                edtStockProduct?.setText("")

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

        edtStockProduct?.setText("" + stockProduct)
        iconAdd?.setOnClickListener {
            if (edtStockProduct?.text.toString().isEmpty()) {
                resetStokBarang()
                tambahStokBarang()
            } else {
                tambahStokBarang()
            }
        }

        iconLess?.setOnClickListener {
            if (edtStockProduct?.text.toString().isEmpty()) {
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
            stockProduct = Integer.parseInt(edtStockProduct?.text.toString().trim())
            if (stockProduct == 0) {
                toast("Item cannot be less than 0")
            } else {
                stockProduct -= 1
                edtStockProduct?.setText("" + stockProduct)
            }
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            kurangStokBarang()
        }

    }

    private fun resetStokBarang() {
        stockProduct = 0
        edtStockProduct?.setText("" + stockProduct)
    }

    private fun tambahStokBarang() {
        try {
            stockProduct = Integer.parseInt(edtStockProduct?.text.toString().trim())
            stockProduct += 1
            edtStockProduct?.setText("" + stockProduct)
        } catch (nfe: NumberFormatException) {
            resetStokBarang()
            tambahStokBarang()
        }

    }
}