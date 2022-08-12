package com.example.uas_akb_10118401;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREFER_NAME = "session";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "nama";
    public static final String KEY_ID = "id";

    // Constructor
    public Session(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String id, String username){
        // Storing in pref
        editor.putString(KEY_USERNAME, username).commit();
        //editor.putString(KEY_NAME, nama).commit();
        editor.putString(KEY_ID, id).commit();
    }

    public String getUserID(){
        String id = pref.getString(KEY_ID, null);
        return id;
    }

    public String getUsername(){
        String username = pref.getString(KEY_USERNAME, null);
        return username;
    }

    public String getNama(){
        String nama = pref.getString(KEY_NAME, null);
        return nama;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public void logout(){
        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }
}

