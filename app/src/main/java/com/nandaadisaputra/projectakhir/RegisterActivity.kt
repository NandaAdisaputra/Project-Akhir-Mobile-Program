package com.nandaadisaputra.projectakhir

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    var regName: EditText? = null
    var regPhone: EditText? = null
    var regGmail: EditText? = null
    var regPassword: EditText? = null
    var btnregister: Button? = null

    private var openHelper: SQLiteOpenHelper? = null
    private var db: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        study()
        gender()
        btnregister?.onClick {
            db = openHelper?.writableDatabase
            val regName = regName?.text.toString().trim()
            val regPhone = regPhone?.text.toString().trim()
            val regEmail = regGmail?.text.toString().trim()
            val regPassword = regPassword?.text.toString().trim()
            if (regName.isEmpty() || regPassword.isEmpty() || regEmail.isEmpty() || regPhone.isEmpty()) {
                toast("Please fill all the details")
            } else {
                insertData(regName, regPhone, regEmail, regPassword)
                toast("Registration Successful")
            }
            if (!validation()) {
                return@onClick


            }
        }

        openHelper = DatabaseHelper(this)

    }

    private fun validation(): Boolean {
        when {
            edt_namereg.text.toString().isBlank() -> {
                edt_namereg.requestFocus()
                edt_namereg.error = "Tidak boleh kosong"
                return false
            }
            edt_phonereg.text.toString().isBlank() -> {
                edt_phonereg.requestFocus()
                edt_phonereg.error = "Tidak boleh kosong"
                return false
            }
            edt_gmailreg.text.toString().isBlank() -> {
                edt_gmailreg.requestFocus()
                edt_gmailreg.error = "Tidak boleh kosong"
                return false
            }
            edt_passwordreg.text.toString().isBlank() -> {
                edt_passwordreg.requestFocus()
                edt_passwordreg.error = "Tidak boleh kosong"
                return false
            }
            else -> return true
        }
    }

    fun insertData(fname: String?, fPhone: String?, fGmail: String?, fPassword: String?) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.COL_2, fname)
        contentValues.put(DatabaseHelper.COL_3, fPhone)
        contentValues.put(DatabaseHelper.COL_4, fGmail)
        contentValues.put(DatabaseHelper.COL_5, fPassword)
        val id = db!!.insert(DatabaseHelper.TABLE_NAME, null, contentValues)
    }

    private fun study() {
        tv_choosepend.onClick {
            val pendidikan = listOf("SD", "SMP Sederajat", "SMA Sederajat", "S1", "S2", "S3")
            selector("Pilih jenjang pendidikan", pendidikan) { dialog, i ->
                edt_pendreg.setText(pendidikan[i])
            }
        }

    }

    private fun gender() {
        tv_choosegenderreg.onClick {
            val jeniskelamin = listOf("Laki-Laki", "Perempuan")
            selector("Pilih jenis Kelamin", jeniskelamin) { dialog, i ->
                edt_genderreg.setText(jeniskelamin[i])
            }
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            tv_sign_upreg -> {
                startActivity<LoginActivity>()
            }
            tv_guestreg -> {
               startActivity<MainActivity>()
            }
        }
    }

}