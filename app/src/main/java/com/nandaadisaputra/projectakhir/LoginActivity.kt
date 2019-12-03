package com.nandaadisaputra.projectakhir

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.layout_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val rcSignIn: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    private var db: SQLiteDatabase? = null
    private var openHelper: SQLiteOpenHelper? = null
    private var cursor: Cursor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_register.setOnClickListener(this)
        forgot_password.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()

        configureGoogleSignIn()
        setupUI()
        openHelper = DatabaseHelper(this)
        db = openHelper?.readableDatabase
        btn_login.onClick {
            val email =  edt_LoginGmail.text.toString().trim()
            val password = edt_LoginPassword.text.toString().trim()
            if (validation()) {
                return@onClick
            } else {
                cursor = db?.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", arrayOf(email, password))
                if (cursor != null) {
                    if (cursor!!.count > 0) {
                        startActivity<MainActivity>()
                        toast("Login Success")
                    } else {
                        toast("Login Error")
                    }

                }
            }
        }

    }


    override fun onClick(v: View?) {
        when (v) {
            tv_register -> startActivity<RegisterActivity>()
            forgot_password -> startActivity<ResetPasswordActivity>()
        }
    }

    private fun validation(): Boolean {
        when {
            //Cek gmail kosong atau tidak
            edt_LoginGmail.text.toString().isBlank() -> {
                edt_LoginGmail.requestFocus()
                edt_LoginGmail.error = "Gmail Tidak boleh kosong"
                return false
            }
            //Cek password kosong atau tidak
            edt_LoginPassword.text.toString().isBlank() -> {
                edt_LoginPassword.requestFocus()
                edt_LoginPassword.error = "Password Tidak boleh kosong"
                return false
            }
            else -> return true
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {
        google_button.onClick {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, rcSignIn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == rcSignIn) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                toast("Google Sign In failed :(")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(MainActivity.getLaunchIntent(this))
            } else {
                toast("Google sign in failed :(")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(MainActivity.getLaunchIntent(this))
        }
    }
}
