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
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val btn: Button = view.find(R.id.detail)
        val btn2: Button = view.find(R.id.share)
        btn.setOnClickListener(this)
        btn2.setOnClickListener(this)
        return view
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detail -> {
                toast("Masuk Ke Detail Activity")
                val intent = Intent(this@ProfileFragment.context, DetailProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.share -> {
                toast("Masuk Ke akun Whatshapp")
                val sharingIntent = Intent(Intent.ACTION_VIEW)
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                sharingIntent.data = Uri.parse("https://web.whatsapp.com/")
                context!!.startActivity(sharingIntent)
            }
        }
    }
}