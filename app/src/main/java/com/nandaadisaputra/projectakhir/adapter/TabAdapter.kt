package com.nandaadisaputra.projectakhir.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nandaadisaputra.projectakhir.ui.fragment.AddProductFragment
import com.nandaadisaputra.projectakhir.ui.fragment.DeleteFragment
import com.nandaadisaputra.projectakhir.ui.fragment.ShowFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
            ShowFragment(),
            AddProductFragment(),
            DeleteFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "SHOW PRODUCT"
            1 -> "ADD PRODUCT"
            else -> "DELETE PRODUCT"
        }
    }

}