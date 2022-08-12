package com.example.uas_akb_10118401;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.onSelectData {

    RecyclerView rvLayanan;
    MainModel mdlMainMenu;
    List<MainModel> lsMainMenu = new ArrayList<>();

    TextInputLayout tfTgl;
    EditText tgl;
    CardView btn_confirm;
    ImageView icon_tgl;
    TextView nama;

    Database database;
    Session session;

    String terpilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case  R.id.order:
                        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        Intent intent3 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        database = new Database(this);
        session = new Session(getApplicationContext());

        tfTgl = findViewById(R.id.textFieldTanggal);
        tgl = findViewById(R.id.tgl);
        btn_confirm = findViewById(R.id.btn_confirm);
        icon_tgl = findViewById(R.id.icon_tgl);
        nama = findViewById(R.id.nama);

        rvLayanan = findViewById(R.id.rvLayanan);
        rvLayanan.setHasFixedSize(true);
        rvLayanan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setMenu();

        nama.setText("Welcome, \\n"+session.getUsername());
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean insert;
                String nomor = "+6285731250605";
                String message;

                if (tgl.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "Pastikan memilih tanggal", Toast.LENGTH_LONG).show();
                } else {
                    if (terpilih=="1"){
                        insert = database.insertTransaksi("Dikeringkan saja","1",Integer.parseInt(session.getUserID()));
                        message = "Nama : " + session.getUsername() + "\n" +
                                "Layanan : Laundry\n" +
                                "Tanggal selesai : "+ tgl.getText();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+nomor+"&text="+message));
                        startActivity(intent);
                    } else if (terpilih=="2"){
                        insert = database.insertTransaksi("Laundry","2",Integer.parseInt(session.getUserID()));
                        message = "Nama : " + session.getUsername() + "\n" +
                                "Layanan : Laundry\n" +
                                "Tanggal selesai : "+ tgl.getText();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+nomor+"&text="+message));
                        startActivity(intent);
                    } else {
                        insert = database.insertTransaksi("Disetrika saja","3",Integer.parseInt(session.getUserID()));
                        message = "Nama : " + session.getUsername() + "\n" +
                                "Layanan : Laundry\n" +
                                "Tanggal selesai : "+ tgl.getText();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+nomor+"&text="+message));
                        startActivity(intent);
                    }

                }
            }
        });

        icon_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pilih layanan terlebih dahulu", Toast.LENGTH_LONG).show();
            }
        });

        tfTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pilih layanan terlebih dahulu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setMenu() {
        mdlMainMenu = new MainModel("1","Dikeringkan saja", R.drawable.baju); //merah
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new MainModel("2","Laundry", R.drawable.basket); //biru
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new MainModel("3","Disetrika saja", R.drawable.setrika); //kuning
        lsMainMenu.add(mdlMainMenu);

        MainAdapter myAdapter = new MainAdapter(lsMainMenu, this);
        rvLayanan.setAdapter(myAdapter);
    }

    @Override
    public void onSelected(MainModel mdlMain) {
        if(mdlMain.getTxtName().equals("Dikeringkan saja")){
            terpilih="1";
            showDateandTime();
        }
        else if (mdlMain.getTxtName().equals("Laundry")) {
            terpilih="2";
            showDateandTime();
        }
        else if (mdlMain.getTxtName().equals("Disetrika saja")){
            terpilih="3";
            showDateandTime();
        }

    }

    public void showDateandTime() {
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(
                MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker,
                                  int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                String date = simpleDateFormat.format(calendar.getTime());

                int ageInteger =0;

                Calendar today = Calendar.getInstance();

                ageInteger = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

                if (today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                    if (today.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)) {
                        ageInteger = ageInteger - 1;
                    }
                } else if (today.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
                    ageInteger = ageInteger - 1;
                }

                tgl.setText(date);
            }
        }, mYear, mMonth, mDay);

        mDatePicker.show();
        int buttonColor = ContextCompat.getColor(getApplicationContext(), R.color.black);
        mDatePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(buttonColor);
        mDatePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(buttonColor);
    }


}