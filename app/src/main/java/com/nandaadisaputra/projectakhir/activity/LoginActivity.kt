package com.nandaadisaputra.projectakhir.activity

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.database.DatabaseHelper
import com.nandaadisaputra.projectakhir.network.SharedPrefManager
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
    var sharedPrefManager: SharedPrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPrefManager = SharedPrefManager(this)
        if (sharedPrefManager?.sPSudahLogin!!) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
            super.onBackPressed()
        }
        val img: LinearLayout = findViewById<View>(R.id.anim) as LinearLayout
        img.setBackgroundResource(R.drawable.bg_gradient0)
        val frameAnimation = img.background as AnimationDrawable
        frameAnimation.setEnterFadeDuration(2000)
        frameAnimation.setExitFadeDuration(4000)
        frameAnimation.start()

        tv_register.setOnClickListener(this)
        forgot_password.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()

        configureGoogleSignIn()
        setupUI()
        openHelper = DatabaseHelper(this)
        db = openHelper?.readableDatabase
        btn_login.onClick {
            val email = edt_login.text.toString().trim()
            val password = edt_loginPassword.text.toString().trim()
            sharedPrefManager?.saveSPString(SharedPrefManager.COL_2, email)
            sharedPrefManager?.saveSPString(SharedPrefManager.COL_7, password)
            if (email.isEmpty() || password.isEmpty()) {
                if (validation()) {
                    return@onClick
                }
            } else {
                cursor = db?.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_7 + "=?", arrayOf(email, password))
                if (cursor != null) {
                    if (cursor!!.count > 0) {
                        startActivity<MainActivity>()
                        sharedPrefManager?.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true)
                        toast("Login Success")
                    } else {
                        toast("Username/Password Salah")
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
            //Check email is empty or not
            edt_login.text.toString().isBlank() -> {
                edt_login.requestFocus()
                edt_login.error = "Email cannot be empty"
                return false
            }
            //Check the password is empty or not
            edt_loginPassword.text.toString().isBlank() -> {
                edt_loginPassword.requestFocus()
                edt_loginPassword.error = "The password must not be blank"
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
