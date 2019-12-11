package com.nandaadisaputra.projectakhir.sharepref

import android.content.Context
import android.content.SharedPreferences


class SharedPrefManager(context: Context) {

    var sp: SharedPreferences
    var spEditor: SharedPreferences.Editor

    // Required empty public constructor

    fun saveSPString(keySP: String?, value: String?) {
        spEditor.putString(keySP, value)
        spEditor.commit()
    }

    fun saveSPInt(keySP: String?, value: Int) {
        spEditor.putInt(keySP, value)
        spEditor.commit()
    }

    fun saveSPBoolean(keySP: String?, value: Boolean) {
        spEditor.putBoolean(keySP, value)
        spEditor.commit()
    }

    val spName: String?
        get() = sp.getString(COL_2, "")

    val spPhone: String?
        get() = sp.getString(COL_3, "")

    val spEmail: String?
        get() = sp.getString(COL_4, "")

    val spPassword: String?
        get() = sp.getString(COL_7, "")

    val sPSudahLogin: Boolean
        get() = sp.getBoolean(SP_SUDAH_LOGIN, false)

    companion object {
        const val DATABASE_NAME = "thesis-login-register"
        const val TABLE_NAME = "user"
        const val COL_1 = "ID"
        const val COL_2 = "Name"
        const val COL_3 = "Phone"
        const val COL_4 = "Email"
        const val COL_5 = "Gender"
        const val COL_6 = "Education"
        const val COL_7 = "Password"
        const val SP_SUDAH_LOGIN = "spSudahLogin"
    }

    init {
        sp = context.getSharedPreferences(TABLE_NAME, Context.MODE_PRIVATE)
        spEditor = sp.edit()
    }
}
