package com.nandaadisaputra.projectakhir

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class ResetPasswordActivity : AppCompatActivity() {
    private var edtEmail: EditText? = null
    private var btnResetPassword: Button? = null
    private var btnBack: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        mAuth = FirebaseAuth.getInstance()
        btnResetPassword?.onClick{
            val email = edtEmail?.text.toString().trim ()
            if (TextUtils.isEmpty(email)) {
                toast("Enter your email!")
                return@onClick
            }
            mAuth?.sendPasswordResetEmail(email)
                    ?.addOnCompleteListener { task: Task<Void?> ->
                        if (task.isSuccessful) {
                            toast("Check email to reset your password!")
                        } else {
                            toast("Fail to send reset password email!")
                        }
                    }
        }
        btnBack?.onClick {
            finish() }
    }
}