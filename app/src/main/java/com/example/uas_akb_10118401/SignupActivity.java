package com.example.uas_akb_10118401;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Database database;
    TextInputLayout tfUsername, tfNama, tfAlamat, tfNo, tfPassword;
    EditText username, nama, alamat, no, password;
    CardView btn_signup;

    String username_user, nama_user,alamat_user,password_user, hp_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = new Database(this);

        initView();
        setView();
    }

    private void initView() {
        tfUsername = findViewById(R.id.tfUsername);
        tfNama = findViewById(R.id.tfNama);
        tfAlamat = findViewById(R.id.tfAlamat);
        tfNo = findViewById(R.id.tfNo);
        tfPassword = findViewById(R.id.tfPassword);

        username = findViewById(R.id.username);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        no = findViewById(R.id.no);
        password = findViewById(R.id.password);

        btn_signup = findViewById(R.id.btn_signup);

        username_user = username.getText().toString();
        nama_user = nama.getText().toString();
        alamat_user = alamat.getText().toString();
        password_user = password.getText().toString();
        hp_user = no.getText().toString();
    }

    public static boolean validateEmail(String email) {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setView() {
        tfAlamat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alamat.getText().clear();
            }
        });

        tfNama.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama.getText().clear();
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfUsername.getError() != null && !s.toString().isEmpty()) {
                    tfUsername.setError(null);
                    tfUsername.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfNama.getError() != null && !s.toString().isEmpty()) {
                    tfNama.setError(null);
                    tfNama.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfAlamat.getError() != null && !s.toString().isEmpty()) {
                    tfAlamat.setError(null);
                    tfAlamat.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfNo.getError() != null && !s.toString().isEmpty()) {
                    tfNo.setError(null);
                    tfNo.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tfPassword.getError() != null && !s.toString().isEmpty()) {
                    tfPassword.setError(null);
                    tfPassword.setErrorEnabled(false);
                }
                if(password.getText().toString().length()<8 &&!isValidPassword(password_user)){
                    tfPassword.setError("Password must contain capital letters, numbers and symbols");
                }else{
                    //System.out.println("Valid");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                initView();

                if (TextUtils.isEmpty(username_user))
                    tfUsername.setError("Username cannot be empty");
                else if (TextUtils.isEmpty(nama_user)) {
                    tfNama.setError("Nama cannot be empty");
                }
                else if (TextUtils.isEmpty(alamat_user))
                    tfAlamat.setError("Phone number cannot be empty");
                else if (TextUtils.isEmpty(password_user))
                    tfPassword.setError("Phone number cannot be empty");
                else if (TextUtils.isEmpty(hp_user))
                    tfNo.setError("Password cannot be empty");
                else if(TextUtils.isEmpty(username_user) && TextUtils.isEmpty(nama_user) && TextUtils.isEmpty(alamat_user) &&
                        TextUtils.isEmpty(hp_user) && TextUtils.isEmpty(password_user)){
                    tfUsername.setError("This field must be filled");
                    tfNama.setError("This field must be filled");
                    tfAlamat.setError("This field must be filled");
                    tfNo.setError("This field must be filled");
                    tfPassword.setError("This field must be filled");
                } else{
                    Boolean checkusername = database.checkusername(username_user);
                    if (checkusername==false) {
                        if (no.getText().toString().length()>7 && no.getText().toString().length()<14){
                            Boolean insert = database.insertUser(nama_user, alamat_user, username_user, password_user, hp_user);
                            if (insert){
                                Toast.makeText(getApplicationContext(), "User Created. Please Login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                            AlertDialog.Builder dialog1 = new AlertDialog.Builder(SignupActivity.this);
                            dialog1.setMessage("Phone number must be between 10 and 15 digits");
                            dialog1.setPositiveButton("OK", null);
                            dialog1.setCancelable(false);
                            dialog1.create().show();
                        }
                    } else {
                        tfUsername.setError("Username registered");
                    }
                }
            }
        });
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}