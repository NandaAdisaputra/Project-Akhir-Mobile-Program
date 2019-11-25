package com.nandaadisaputra.projectakhir;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.regName)
    EditText regName;
    @BindView(R.id.regPhone)
    EditText regPhone;
    @BindView(R.id.regGmail)
    EditText regGmail;
    @BindView(R.id.regPassword)
    EditText regPassword;
    @BindView(R.id.btnregister)
    Button btnregister;
    @BindView(R.id.sign_up)
    TextView signUp;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        openHelper = new DatabaseHelper(this);

        btnregister.setOnClickListener(v -> {
            db = openHelper.getWritableDatabase();
            String fname = regName.getText().toString().trim();
            String fPhone = regPhone.getText().toString().trim();
            String fGmail = regGmail.getText().toString().trim();
            String fPassword = regPassword.getText().toString().trim();
            if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                insertData(fname, fPhone, fGmail, fPassword);
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    public void insertData(String fname, String fPhone, String fGmail, String fPassword) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, fname);
        contentValues.put(DatabaseHelper.COL_3, fPhone);
        contentValues.put(DatabaseHelper.COL_4, fGmail);
        contentValues.put(DatabaseHelper.COL_5, fPassword);

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }
}
