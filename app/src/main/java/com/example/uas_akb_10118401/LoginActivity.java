package com.example.uas_akb_10118401;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Database database;
    Session session;

    TextInputLayout tfUsername, tfPassword;
    EditText username_login, password_login;
    CardView btn_login;
    TextView daftar;

    String username, password, id;
    boolean data = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(getApplicationContext());
        database = new Database(this);

        initView();
        setView();

        username_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && s.toString().contains(" ")){
                    //show error
                    tfUsername.setError("No spaces");
                }else if (tfUsername.getError() != null && !s.toString().isEmpty()){
                    tfUsername.setError(null);
                    tfUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        password_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfPassword.getError() != null && !s.toString().isEmpty()){
                    tfPassword.setError(null);
                    tfPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        tfUsername.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_login.getText().clear();
            }
        });

    }

    public boolean checkUsernameandPass() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursorr = db.rawQuery("select * from user where username ='" + username + "' and password = '"+ password +"'" , null);
        if (cursorr.moveToFirst())
        {
            do {
                data = true;
            }
            while (cursorr.moveToNext());
        }
        db.close();
        cursorr.close();
        return data;
    }

    public void initView() {
        username_login = findViewById(R.id.username);
        password_login = findViewById(R.id.password);

        username = username_login.getText().toString();
        password = password_login.getText().toString();

        btn_login = findViewById(R.id.btn_login);
        daftar = findViewById(R.id.txtDaftar);

        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
    }

    public void setView() {
        btn_login.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();

                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                    tfUsername.setError("This field must be filled");
                    tfPassword.setError("This field must be filled");
                }
                else if (TextUtils.isEmpty(username))
                    tfUsername.setError("This field must be filled");
                else if (TextUtils.isEmpty(password)){
                    tfPassword.setError("This field must be filled");
                }
                else if (checkUsernameandPass()==false) {
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(LoginActivity.this);
                    dialog1.setMessage("Username and Password not match");
                    dialog1.setPositiveButton("OK", null);
                    dialog1.setCancelable(false);
                    dialog1.create().show();
                } else {
                    if (database.checID(username)) {
                        id = String.valueOf(database.getID(username));
                        Toast.makeText(getApplicationContext(), "Username : "+ username, Toast.LENGTH_LONG).show();
                        session.createUserLoginSession(id, username);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(LoginActivity.this);
                        dialog1.setMessage("Account not found!");
                        dialog1.setPositiveButton("OK", null);
                        dialog1.setCancelable(false);
                        dialog1.create().show();
                    }
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}