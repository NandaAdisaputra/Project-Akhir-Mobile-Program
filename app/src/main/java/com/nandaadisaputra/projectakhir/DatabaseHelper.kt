package com.nandaadisaputra.projectakhir

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone TEXT,Email TEXT,Password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    companion object {
        const val DATABASE_NAME = "thesis-login-register"
        const val TABLE_NAME = "user"
        const val COL_1 = "ID"
        const val COL_2 = "Name"
        const val COL_3 = "Phone"
        const val COL_4 = "Email"
        const val COL_5 = "Password"
    }
}

