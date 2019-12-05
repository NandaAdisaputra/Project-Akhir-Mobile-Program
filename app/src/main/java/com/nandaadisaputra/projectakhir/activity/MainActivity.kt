package com.nandaadisaputra.projectakhir.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.fragment.AboutFragment
import com.nandaadisaputra.projectakhir.network.SharedPrefManager
import org.jetbrains.anko.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var pageContent: Fragment? = AboutFragment()
    private var title: String? = "Aplikasi Mobile GIS"
    private val KEY_FRAGMENT: String? = null
    private val KEY_TITLE: String? = null
    var sharedPrefManager: SharedPrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPrefManager = SharedPrefManager(this)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_drawer)
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.main_navigation)
        navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_tentang -> {
                    pageContent = AboutFragment()
                    title = "Marketplace"
                }
                R.id.menu_keluar -> {
                    alert ("Apakah anda ingin logout ?"){
                        noButton {
                            toast("Anda tidak jadi Keluar")
                            startActivity(intentFor<MainActivity>())
                            finish()
                        }
                        yesButton {
                            if(sharedPrefManager?.sPSudahLogin!!) {
                                sharedPrefManager?.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false)
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                                super.onBackPressed()
                            }else {
                                toast("Maaf Aplikasi Tugas Akhir Mobile Program belum sempurna")
                            }
                        }
                    }.show()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pageContent!!).commit()
            toolbar.title = title
            drawerLayout.closeDrawers()
            true
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pageContent!!).commit()
            toolbar.title = title
        } else {
            pageContent = supportFragmentManager.getFragment(savedInstanceState, KEY_FRAGMENT!!)
            title = savedInstanceState.getString(KEY_TITLE)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, pageContent!!).commit()
            toolbar.title = title
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_TITLE, title)
        supportFragmentManager.putFragment(outState, KEY_FRAGMENT!!, pageContent!!)
        super.onSaveInstanceState(outState)
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

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

}