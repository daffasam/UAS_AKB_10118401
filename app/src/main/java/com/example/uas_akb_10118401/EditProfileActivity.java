package com.example.uas_akb_10118401;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {

    Database database;
    Session session;
    Cursor cursor;

    TextInputLayout tfUsername, tfNama, tfAlamat, tfNo;
    EditText etUsername, etNama, etAlamat, etNo;

    CardView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initView();

        database = new Database(this);
        session = new Session(getApplicationContext());

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM user where username = '"+session.getUsername()+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            etUsername.setText(cursor.getString(3));
            etNama.setText(cursor.getString(1));
            etNo.setText(cursor.getString(5));
            etAlamat.setText(cursor.getString(2));
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = database.getWritableDatabase();
                db.execSQL("update user set username='"+
                        etUsername.getText().toString() +"', nama='" +
                        etNama.getText().toString() +"', alamat='"+
                        etAlamat.getText().toString() +"', no_hp='" +
                        etNo.getText().toString() +"' where id= "+session.getUserID());
                Toast.makeText(getApplicationContext(), "Berhasil diubah", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void initView() {
        tfUsername = findViewById(R.id.tfUsername);
        tfNama = findViewById(R.id.tfNama);
        tfAlamat = findViewById(R.id.tfAlamat);
        tfNo = findViewById(R.id.tfNo);

        etUsername = findViewById(R.id.username);
        etNama = findViewById(R.id.nama);
        etAlamat = findViewById(R.id.alamat);
        etNo = findViewById(R.id.no);

        edit = findViewById(R.id.btn_edit);
    }
}