package com.moa.theiustore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moa.theiustore.model.User;

import java.util.ArrayList;

public class UserDao {
    private Context context;
    private User user;
    private ArrayList<User> usersList;
    private SQLiteDatabase SQLdb;
    private final String dbName = "UsersDb";
    private static final String TABLE_NAME = "users";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_PASSWORD = "password";

    public UserDao(Context context) {
        this.context = context;
        SQLdb = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        String tableCreation = "create table if not exists users (id integer primary key autoincrement, name text, email text, phone text, password text)";
        SQLdb.execSQL(tableCreation);
        user = new User();
    }

    public boolean addUser(User user) {
        if (getUserByEmail(user.getEmail())==null) {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, user.getName());
            values.put(COL_EMAIL, user.getEmail());
            values.put(COL_PHONE, user.getPhone());
            values.put(COL_PASSWORD, user.getPassword());
            return SQLdb.insert(TABLE_NAME, null, values) > 0;
        } else {
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        usersList = new ArrayList<>();
        Cursor cursor = SQLdb.rawQuery("select * from users", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                usersList.add(user);
            } while (cursor.moveToNext());
        }
        return usersList;
    }

    public User getUserByEmail(String email) {
        String[] columns = {COL_ID, COL_NAME, COL_EMAIL, COL_PHONE, COL_PASSWORD};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = SQLdb.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            return user;
        } else {
            return null;
        }
    }

    public User getUserById(int id) {
        String[] columns = {COL_ID, COL_NAME, COL_EMAIL, COL_PHONE, COL_PASSWORD};
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = SQLdb.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            return user;
        } else {
            return null;
        }
    }

    public boolean loginUser(String email, String password) {
        Cursor cursor = SQLdb.rawQuery("select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor != null && cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PHONE, user.getPhone());
        values.put(COL_PASSWORD, user.getPassword());
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {String.valueOf(user.getId())};
        return SQLdb.update(TABLE_NAME, values, whereClause, whereArgs) > 0;
    }

    public boolean deleteUser(int id) {
        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        return SQLdb.delete(TABLE_NAME, whereClause, whereArgs) > 0;
    }
}