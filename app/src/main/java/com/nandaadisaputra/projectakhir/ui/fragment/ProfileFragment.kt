package com.nandaadisaputra.projectakhir.ui.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.DetailProfileActivity
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(){
    lateinit var contentView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        contentView = inflater.inflate(R.layout.fragment_profile, container, false)
        contentView.detail.onClick {
            startActivity<DetailProfileActivity>()
        }
        contentView.share.onClick {
            val sharingIntent = Intent(Intent.ACTION_VIEW)
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            sharingIntent.data = Uri.parse("https://web.whatsapp.com/")
            context?.startActivity(sharingIntent)
        }
        return contentView
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}