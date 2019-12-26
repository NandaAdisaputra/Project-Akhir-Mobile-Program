package com.nandaadisaputra.projectakhir.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.product.DeleteActivity
import com.nandaadisaputra.projectakhir.ui.activity.product.ShowActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class MenuFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        val btn: ImageView = view.find(R.id.imageView2)
        val btn2: ImageView = view.find(R.id.imageView3)
        val btn4: ImageView = view.find(R.id.imageView5)
        btn.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn4.setOnClickListener(this)
        return view
    }
    companion object {
        fun newInstance(): MenuFragment {
            val fragment = MenuFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageView2 -> {
                val ft = fragmentManager?.beginTransaction()?.replace(R.id.menu, AddProductFragment())
                ft?.commit()
            }
            R.id.imageView3 -> {
                toast("Masuk Ke DeleteFragment")
                startActivity<DeleteActivity>()
            }
            R.id.imageView5 -> {
                toast("Masuk Ke ShowFragment")
                startActivity<ShowActivity>()
            }
        }
    }

}
