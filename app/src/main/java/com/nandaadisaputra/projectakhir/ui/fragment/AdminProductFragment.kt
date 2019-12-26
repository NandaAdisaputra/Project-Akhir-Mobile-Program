package com.nandaadisaputra.projectakhir.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.nandaadisaputra.projectakhir.R

class AdminProductFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_product, container, false)
    }
    companion object {
        fun newInstance(): AdminProductFragment {
            val fragment = AdminProductFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}

