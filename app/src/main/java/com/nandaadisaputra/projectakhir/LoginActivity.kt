package com.nandaadisaputra.projectakhir

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
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
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var etLogGmail: EditText? = null
    var etLoginPassword: EditText? = null
    private var db: SQLiteDatabase? = null
    var openHelper: SQLiteOpenHelper? = null
    private var cursor: Cursor? = null

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        openHelper = DatabaseHelper(this)
        db = openHelper?.readableDatabase
        firebaseAuth = FirebaseAuth.getInstance()
        configureGoogleSignIn()
        setupUI()

        btnlogin.onClick {
            if (!validation()) {
                return@onClick
            }
        }
        val email = etLogGmail?.text.toString().trim()
        val password = etLoginPassword?.text.toString().trim()
        if (password.length < 6) {
            toast("Password too short, enter minimum 6 characters!")
        } else {
            cursor = db?.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", arrayOf(email, password))
            if (cursor != null) {
                if (cursor!!.count > 0) {
                    startActivity(intentFor<MainActivity>())
                    toast("Login sucess")
                } else {
                    toast("Login error")
                }
            }
        }
    }

    private fun validation(): Boolean {
        when {
            edt_LoginGmail.text.toString().isBlank() -> {
                edt_LoginGmail.requestFocus()
                edt_LoginGmail.error = "Tidak boleh kosong"
                return false
            }
            edt_LoginPassword.text.toString().isBlank() -> {
                edt_LoginPassword.requestFocus()
                edt_LoginPassword.error = "Tidak boleh kosong"
                return false
            }
            else -> return true
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            forgot_password -> {
                startActivity<ResetPasswordActivity>()
            }
            sign_in -> {
                startActivity<RegisterActivity>()
            }
        }
    }
    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI(){
        google_button.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign In failed :(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(MainActivity.getLaunchIntent(this))
            } else {
                Toast.makeText(this, "Google sign in failed :(", Toast.LENGTH_LONG).show()
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


