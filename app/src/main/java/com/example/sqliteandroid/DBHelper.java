package com.example.sqliteandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, phone TEXT NOT NULL, dob TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS users");
    }

    public boolean insertUserData(String username, String phone, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("dob", dob);

        long res = DB.insert("users", null, contentValues);
        return res != -1;
    }

    public boolean updateUserData(String username, String phone, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("phone", phone);
        contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("SELECT * FROM users WHERE username=?", new String[] { username });
        if (cursor.getCount() > 0) {
            long res = DB.update("users", contentValues, "username=?", new String[] { username });
            return res != -1;
        } else {
            return false;
        }
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM users WHERE username=?", new String[] { username });
        if (cursor.getCount() > 0) {
            long res = DB.delete("users", "username=?", new String[] { username });
            return res != -1;
        } else {
            return false;
        }
    }

    public Cursor getUsers() {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM users", null);

        return cursor;
    }
}
