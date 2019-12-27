package com.nandaadisaputra.projectakhir.ui.fragment.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.product.DeleteActivity
import com.nandaadisaputra.projectakhir.ui.activity.product.ShowActivity
import com.nandaadisaputra.projectakhir.ui.activity.product.ShowUserActivity
import com.nandaadisaputra.projectakhir.ui.fragment.AddProductFragment
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_menu_user.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity


class MenuUserFragment : Fragment() {

    lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        contentView = inflater.inflate(R.layout.fragment_menu_user, container, false)
        contentView.imageView10.onClick {
            startActivity<ShowUserActivity>()
        }
        return contentView
    }
    companion object {
        fun newInstance(): MenuUserFragment {
            val fragment = MenuUserFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment

        }
    }

}
