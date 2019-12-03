package com.nandaadisaputra.projectakhir

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

private var openHelper: SQLiteOpenHelper? = null
private var db: SQLiteDatabase? = null

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        openHelper = DatabaseHelper(this)

        btn_register.onClick {
            if (validation()) {
                return@onClick
            }
            db = openHelper?.writableDatabase
            val name = edt_namereg.text.toString().trim()
            val phone = edt_phonereg.text.toString().trim()
            val email = edt_gmailreg.text.toString().trim()
            val password = edt_passwordreg.text.toString().trim()
            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                toast("Silakan isi semua detail")
            } else {
                dataInsert(name, phone, email, password)
                toast("Registrasi berhasil")
            }

        }
    }

    private fun dataInsert(name: String?, phone: String?, email: String?, password: String?) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.COL_2, name)
        contentValues.put(DatabaseHelper.COL_3, phone)
        contentValues.put(DatabaseHelper.COL_4, email)
        contentValues.put(DatabaseHelper.COL_5, password)
        db?.insert(DatabaseHelper.TABLE_NAME, null, contentValues)

    }

    private fun validation(): Boolean {
        when {
            //Cek nama kosong atau tidakS
            edt_namereg.text.toString().isBlank() -> {
                edt_namereg.requestFocus()
                edt_namereg.error = "Nama Anda Tidak boleh kosong"
                return false
            }
            //Cek phone kosong atau tidak
            edt_phonereg.text.toString().isBlank() -> {
                edt_phonereg.requestFocus()
                edt_phonereg.error = "Nomor Hp Anda Tidak boleh kosong"
                return false
            }
            //Cek gmail kosong atau tidak
            edt_gmailreg.text.toString().isBlank() -> {
                edt_gmailreg.requestFocus()
                edt_gmailreg.error = "Gmail Anda Tidak boleh kosong"
                return false
            }
            //Cek gmail kosong atau tidak
            edt_gmailreg.text.toString().isBlank() -> {
                edt_gmailreg.requestFocus()
                edt_gmailreg.error = "Gmail Anda Tidak boleh kosong"
                return false
            }
            else -> return true
        }
    }

    override fun onClick(v: View?) {
            when (v) {
                tv_loginreg -> startActivity<LoginActivity>()
                tv_guestreg -> startActivity<MainActivity>()
            }
    }
}
