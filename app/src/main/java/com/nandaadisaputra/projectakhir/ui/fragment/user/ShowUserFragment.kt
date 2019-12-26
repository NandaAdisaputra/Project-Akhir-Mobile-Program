package com.nandaadisaputra.projectakhir.ui.fragment.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nandaadisaputra.projectakhir.R

/**
 * A simple [Fragment] subclass.
 */
class ShowUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_user, container, false)
    }
    companion object {
        fun newInstance(): ShowUserFragment {
            val fragment = ShowUserFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
