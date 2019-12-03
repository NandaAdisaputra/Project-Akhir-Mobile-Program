package com.nandaadisaputra.projectakhir

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import kotlin.system.exitProcess

class ResetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private var edtEmail: EditText? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mAuth = FirebaseAuth.getInstance()
        btn_reset_password.setOnClickListener(this)

        btn_reset_password.onClick {
            val email = edtEmail?.text.toString().trim()
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
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_backreset -> {
                startActivity<LoginActivity>()
            }
            tv_exitapps -> {
                moveTaskToBack(true)
                exitProcess(-1)
            }
        }
    }
}