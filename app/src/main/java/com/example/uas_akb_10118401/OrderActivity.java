package com.example.uas_akb_10118401;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderActivity extends AppCompatActivity {

    CardView btn_cancel,btn_cancel2,btn_confirm,cv2,cv3;
    CheckBox cb1, cb2;
    LinearLayout lay_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.order);

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

        initView();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel.setVisibility(View.GONE);
                lay_choose.setVisibility(View.VISIBLE);
                cb1.setVisibility(View.VISIBLE);
                cb2.setVisibility(View.VISIBLE);
            }
        });

        btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel.setVisibility(View.VISIBLE);
                lay_choose.setVisibility(View.GONE);
                cb1.setVisibility(View.GONE);
                cb2.setVisibility(View.GONE);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb1.isChecked()){
                    cv2.setVisibility(View.GONE);
                } else if (cb2.isChecked())
                    cv3.setVisibility(View.GONE);
                else if (cb1.isChecked() && (cb2.isChecked())){
                    cv2.setVisibility(View.GONE);
                    cv3.setVisibility(View.GONE);
                } else {
                    btn_cancel.setVisibility(View.VISIBLE);
                    lay_choose.setVisibility(View.GONE);
                    cb1.setVisibility(View.GONE);
                    cb2.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel2 = findViewById(R.id.btn_cancel2);
        btn_confirm = findViewById(R.id.btn_confirm);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);

        lay_choose = findViewById(R.id.lay_choose);

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
    }
}