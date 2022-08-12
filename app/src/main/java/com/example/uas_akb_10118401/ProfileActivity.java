package com.example.uas_akb_10118401;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    Database database;
    Session session;
    Cursor cursor;

    TextView nama, hp, alamat;

    CardView edit, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.order:
                        Intent intent3 = new Intent(getApplicationContext(), OrderActivity.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        initView();

        database = new Database(this);
        session = new Session(getApplicationContext());

        SQLiteDatabase db = database.getReadableDatabase();

        Toast.makeText(getApplicationContext(), "Username : "+ session.getUsername(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "ID : "+ session.getUserID(), Toast.LENGTH_LONG).show();

        cursor = db.rawQuery("SELECT * FROM user where id_user = "+session.getUserID()+"", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(1));
            hp.setText(cursor.getString(5));
            alamat.setText(cursor.getString(2));
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent3);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent3);
                session.logout();
                session.logoutUser();
            }
        });
    }

    private void initView() {
        nama = findViewById(R.id.nama);
        hp = findViewById(R.id.no_hp);
        alamat = findViewById(R.id.alamat);

        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
    }
}