package com.nandaadisaputra.projectakhir.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.sharepref.SharedPrefManager
import com.nandaadisaputra.projectakhir.ui.activity.login.LoginActivity
import com.nandaadisaputra.projectakhir.ui.fragment.MenuFragment
import com.nandaadisaputra.projectakhir.ui.fragment.ProfileFragment
import com.nandaadisaputra.projectakhir.ui.fragment.user.ShowUserFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var content: FrameLayout? = null
    private var sharedPrefManager: SharedPrefManager? = null

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_admin -> {
                        val fragment = MenuFragment.newInstance()
                        addFragment(fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_user-> {
                        val fragment = ShowUserFragment.newInstance()
                        addFragment(fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_profile -> {
                        val fragment = ProfileFragment.newInstance()
                        addFragment(fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentProduct, fragment, fragment.javaClass.simpleName)
                .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        sharedPrefManager = SharedPrefManager(this)

        initView()


        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initView() {

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = MenuFragment.newInstance()
        addFragment(fragment)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation_drawer view Item clicks here.
        when (item.itemId) {
            R.id.menu_exit -> {
                alert("Apakah anda ingin logout ?") {
                    noButton {
                        toast("Anda tidak jadi Keluar")
                        startActivity(intentFor<MainActivity>())
                        finish()
                    }
                    yesButton {
                        if (sharedPrefManager?.sPSudahLogin!!) {
                            sharedPrefManager?.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false)
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                            super.onBackPressed()
                        } else {
                            toast("Maaf Aplikasi Tugas Akhir Mobile Program belum sempurna")
                        }
                    }
                }.show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

}