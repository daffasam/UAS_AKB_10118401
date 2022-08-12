package com.example.uas_akb_10118401;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "laundry.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTableUser = "create table user(id_user INTEGER PRIMARY KEY AUTOINCREMENT, nama text null, " +
                "alamat text null, username text null, password text null, no_hp text null);";

        String CreateTableTransaksi = "create table transaksi(id_tr INTEGER PRIMARY KEY AUTOINCREMENT, jenis text null, " +
                "status text null, id_user INTEGER, " +
                "FOREIGN KEY (id_user)" +
                "    REFERENCES user (id_user)" +
                "       ON UPDATE CASCADE" +
                "       ON DELETE CASCADE);";

        Log.d("Data", "onCreate " + CreateTableUser);
        db.execSQL(CreateTableUser);
        Log.d("Data", "onCreate " + CreateTableTransaksi);
        db.execSQL(CreateTableTransaksi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean insertUser(String nama, String alamat, String username, String password, String hp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama", nama);
        values.put("alamat", alamat);
        values.put("password", password);
        values.put("username", username);
        values.put("no_hp", hp);

        long result = db.insert("user", null, values);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Boolean insertTransaksi(String jenis, String status, int id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("select id_user from user where id_user="+id_user, null);

        if(cursor.getCount()>0) {
            values.put("jenis", jenis);
            values.put("status", status);
            values.put("id_user", id_user);
        }

        long result = db.insert("transaksi", null, values);
        if (result==-1)
            return false;
        else
            return true;

    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checID(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id_user from user where username=?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Integer getID(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id_user from user where username=?", new String[] {username});
        if(cursor.moveToFirst() && cursor.getCount()>0)
            return cursor.getInt(0);
        else
            return null;
    }

    public List<Integer> getData_User(int id_user) {
        ArrayList<Integer> results = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from transaksi where id_user="+id_user, null);
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    results.add(cursor.getInt(4));
                } while (cursor.moveToNext());
            }
        }

        if (results != null)
            return results;
        else
            return null;
    }
}
