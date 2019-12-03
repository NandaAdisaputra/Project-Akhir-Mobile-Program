package com.nandaadisaputra.projectakhir

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

private var openHelper: SQLiteOpenHelper? = null
private var db: SQLiteDatabase? = null

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        openHelper = DatabaseHelper(this)

        tv_chooseGender.onClick {
            val jurusan = listOf("Male", "Female")
            selector("Select Gender", jurusan) { _, i ->
                edt_gender.setText(jurusan[i])
            }
        }
        btn_register.onClick {
            db = openHelper?.writableDatabase
            val name = edt_name.text.toString().trim()
            val phone = edt_phone.text.toString().trim()
            val email = edt_email.text.toString().trim()
            val password = edt_password.text.toString().trim()
            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            } else {
                dataInsert(name, phone, email, password)
                toast("Registration successful")
            }
            if (validation()) {
                return@onClick
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
            //Check the name is empty or not
            edt_name.text.toString().isBlank() -> {
                edt_name.requestFocus()
                edt_name.error = "Your name must not be blank"
                return false
            }
            //Check phone is empty or not
            edt_phone.text.toString().isBlank() -> {
                edt_phone.requestFocus()
                edt_phone.error = "Your Mobile Number must not be blank"
                return false
            }
            //Check email is empty or not
            edt_email.text.toString().isBlank() -> {
                edt_email.requestFocus()
                edt_email.error = "Your Email may not be empty"
                return false
            }
            //Check the password is empty or not
            edt_password.text.toString().isBlank() -> {
                edt_password.requestFocus()
                edt_password.error = "Your password must not be blank"
                return false
            }
            else -> return true
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_loginRegister -> startActivity<LoginActivity>()
            tv_guestRegister -> startActivity<MainActivity>()
        }
    }
}
