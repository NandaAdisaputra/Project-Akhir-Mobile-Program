package com.nandaadisaputra.projectakhir.activity

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.projectakhir.database.DatabaseHelper
import com.nandaadisaputra.projectakhir.R
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
            val gender = listOf("Male", "Female")
            selector("Select Gender", gender) { _, i ->
                edt_gender.setText(gender[i])
            }
        }
        tv_selectEducation.onClick {
            val education = listOf("SD Sederajat", "SMP Sederajat","SMA Sederajat","S1","S2","S2","S3")
            selector("Select Education", education) { _, i ->
                edt_education.setText(education[i])
            }
        }
        btn_register.onClick {
            db = openHelper?.writableDatabase
            val name = edt_name.text.toString().trim()
            val phone = edt_phone.text.toString().trim()
            val email = edt_email.text.toString().trim()
            val gender= edt_gender.text.toString().trim()
            val education = edt_education.text.toString().trim()
            val password = edt_password.text.toString().trim()
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()|| gender.isEmpty() || education.isEmpty()|| password.isEmpty()) {
            } else {
                dataInsert(name, phone, email, gender,education, password)
                toast("Registration successful")
            }
            if (validation()) {
                return@onClick
            }
        }
    }

    private fun dataInsert(name: String?, phone: String?, email: String?,gender: String?, education: String?,password: String?) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.COL_2, name)
        contentValues.put(DatabaseHelper.COL_3, phone)
        contentValues.put(DatabaseHelper.COL_4, email)
        contentValues.put(DatabaseHelper.COL_5, gender)
        contentValues.put(DatabaseHelper.COL_6,education)
        contentValues.put(DatabaseHelper.COL_7, password)
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
            //Check gender is empty or not
            edt_gender.text.toString().isBlank() -> {
                edt_gender.requestFocus()
                edt_gender.error = "Your Gender may not be empty"
                return false
            }
            //Check education is empty or not
            edt_education.text.toString().isBlank() -> {
                edt_education.requestFocus()
                edt_education.error = "Your Education may not be empty"
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
