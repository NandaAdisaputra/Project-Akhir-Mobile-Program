package com.nandaadisaputra.projectakhir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etLogGmail)
    EditText etLogGmail;
    @BindView(R.id.etLoginPassword)
    EditText etLoginPassword;
    @BindView(R.id.btnlogin)
    Button btnlogin;
    @BindView(R.id.sign_in)
    TextView signIn;
    @BindView(R.id.forget_password)
    TextView forgetPassword;

    private SQLiteDatabase db;
    public SQLiteOpenHelper openHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        btnlogin.setOnClickListener(v -> {
            String email = etLogGmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
            } else {
                cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, password});
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        forgetPassword.setOnClickListener(
                v -> startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class)));

        signIn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

    }
}
