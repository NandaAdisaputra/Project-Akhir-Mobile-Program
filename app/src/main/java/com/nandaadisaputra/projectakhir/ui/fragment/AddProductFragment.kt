package com.nandaadisaputra.projectakhir.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.network.ApiConfig
import com.nandaadisaputra.projectakhir.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_add_item.view.*
import okhttp3.ResponseBody
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductFragment : Fragment() {

    lateinit var contentView: View
    private var stockProduct = 0
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_add_item, container, false)

        contentView.btn_send_product.onClick{

            val apiService = ApiConfig.getApiService()
            apiService.addData(
                    contentView.edt_name_product.text.toString().trim(),
                    contentView.edt_description_product.text.toString().trim(),
                    contentView.edt_price_product.text.toString().trim(),
                    contentView.edt_stock_product.text.toString().trim()
            )
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                              toast("Add Success")
                                contentView.edt_name_product.setText("")
                                contentView.edt_description_product.setText("")
                                contentView.edt_price_product.setText("")
                                contentView.edt_stock_product.setText("")

                                activity?.finishAffinity()
                                startActivity<MainActivity>()

                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            toast("There is no internet network")
                        }
                    })

        }

        contentView.edt_stock_product.setText("" + stockProduct)
        contentView.icon_plus.onClick {
            if (contentView.edt_stock_product.text.toString().isEmpty()) {
                resetStockProducts()
                addStockProducts()
            } else {
               addStockProducts()
            }
        }

        contentView.icon_minus.onClick {
            if (contentView.edt_stock_product.text.toString().isEmpty()) {
                resetStockProducts()
                lessStockProducts()
            } else {
                lessStockProducts()
            }
        }

        return contentView
    }


    private fun lessStockProducts() {
        try {
            stockProduct = Integer.parseInt(contentView.edt_stock_product.text.toString().trim())
            if (stockProduct == 0) {
                toast("Item cannot be less than 0")
            } else {
                stockProduct -= 1
                contentView.edt_stock_product.setText("" + stockProduct)
            }
        } catch (nfe: NumberFormatException) {
            resetStockProducts()
            lessStockProducts()
        }

    }

    private fun resetStockProducts() {
        stockProduct = 0
        contentView.edt_stock_product.setText("" + stockProduct)
    }

    private fun addStockProducts() {
        try {
            stockProduct = Integer.parseInt(contentView.edt_stock_product.text.toString().trim())
            stockProduct += 1
            contentView.edt_stock_product.setText("" + stockProduct)
        } catch (nfe: NumberFormatException) {
            resetStockProducts()
            addStockProducts()
        }

    }
}