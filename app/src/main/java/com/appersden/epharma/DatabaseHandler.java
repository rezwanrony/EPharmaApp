package com.appersden.epharma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "EPharmaApps";

    // Login table name
    private static final String TABLE_USER = "users";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_USERNAME + " TEXT, "+  KEY_EMAIL + " TEXT, "+KEY_PHONE + " TEXT, "+ KEY_PASSWORD + " TEXT,"+ KEY_GENDER + " TEXT " + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */


    public void addUser(String username, String email, String phone, String password, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //permission
        values.put(KEY_USERNAME, username);//permission
        values.put(KEY_PASSWORD, password);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_GENDER, gender);
        // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     * */
    public Map getUserDetails() {
        Map user = new HashMap();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("phone", cursor.getString(3));
            user.put("password", cursor.getString(2));
            user.put("gender", cursor.getString(5));
            user.put("email", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }




    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void UpdateUsers(String username, String phone, String gender, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update user data
        ContentValues cv = new ContentValues();
        cv.put("username",username); //These Fields should be your String values of actual column names
        cv.put("phone",phone);
        cv.put("gender",gender);
        cv.put("password",password);

        db.update(TABLE_USER, cv, String.format("%s = ?", "username"), new String[]{username});

        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


}
