package com.example.movieapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    Handler handler = new Handler();
    boolean running1 = true, running2 = true;
    int counter1 = 0, counter2 = 0;
    static boolean fromActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean restartSplash = false;

        Cursor myCursor;
        LoginSystemDatabaseHelper dbHelper = new LoginSystemDatabaseHelper(this);
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            myCursor = db.query("SIGNED_IN", new String[]{"CHECKBOX"}, null, null, null, null, null);
            if (myCursor.moveToFirst()) {
                if (myCursor.getString(0).equals("TRUE")) {
                    restartSplash = true;
                }
            }
            db.close();
            myCursor.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(LoginActivity.this, "Error accessing the database.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (restartSplash) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    counter1++;
                    if (counter1 == 2) {
                        Intent intent = new Intent(LoginActivity.this, InsideActivity.class);
                        startActivity(intent);
                    }
                    if (counter1 > 2) {
                        counter1 = 3;
                    }

                    if (running1) {
                        setContentView(R.layout.layout_splash);
                        running1 = false;
                    }

                    handler.postDelayed(this, 3000);
                }
            });
        }
        else if (!fromActivity) {
                    handler = new Handler();
                    handler.post(new Runnable() {
                @Override
                public void run() {
                    counter2++;
                    if (counter2 == 2) {
                        setContentView(R.layout.activity_login);
                    }
                    if (counter2 > 2) {
                        counter2 = 3;
                    }

                    if (running2) {
                        setContentView(R.layout.layout_splash);
                        running2 = false;
                    }

                    handler.postDelayed(this, 3000);
                }
            });
        } else {
            setContentView(R.layout.activity_login);
        }
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginClick(View view) {
        EditText username = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        String name = username.getText().toString(), pass = password.getText().toString();
        CheckBox isSigned = (CheckBox) findViewById(R.id.checkbox_signed);

        if ((!name.equals("")) && (!pass.equals(""))) {
            LoginSystemDatabaseHelper helper = new LoginSystemDatabaseHelper(this);
            Cursor cursor;

            boolean result = false;

            try {
                SQLiteDatabase db = helper.getReadableDatabase();
                cursor = db.query("ACCOUNTS", new String[]{"EMAIL", "PASSWORD"}, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        if (name.equals(cursor.getString(0)) && pass.equals(cursor.getString(1))) {
                            Toast toast = Toast.makeText(LoginActivity.this, "Account found.", Toast.LENGTH_SHORT);
                            toast.show();
                            result = true;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(LoginActivity.this, "Error accessing the database.", Toast.LENGTH_SHORT);
                toast.show();
            }

            if (result) {

                if (!isSigned.isChecked()) {
                    Intent intent = new Intent(LoginActivity.this, InsideActivity.class);
                    startActivity(intent);
                }
                else if (isSigned.isChecked()){
                    ContentValues contentValues = new ContentValues();
                    LoginSystemDatabaseHelper dbHelper = new LoginSystemDatabaseHelper(this);
                    try {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        contentValues.put("CHECKBOX", "TRUE");
                        db.insert("SIGNED_IN", null, contentValues);
                        db.close();
                    } catch(SQLiteException e) {
                        Toast toast = Toast.makeText(LoginActivity.this, "Error accessing the database.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    Intent intent = new Intent(LoginActivity.this, InsideActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast toast = Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onShowDatabase(View view) {
        LoginSystemDatabaseHelper loginSystemDatabaseHelper = new LoginSystemDatabaseHelper(LoginActivity.this);
        try {
            SQLiteDatabase mDatabase = loginSystemDatabaseHelper.getWritableDatabase();
            Cursor myCursor = mDatabase.query("ACCOUNTS", new String[] {"NAME", "EMAIL", "PREFERENCE", "BIRTHDAY"}, null, null, null, null, null);

            if (myCursor.moveToFirst()) {
                do {
                    // Display the cursor
                    Toast toast1 = Toast.makeText(LoginActivity.this, myCursor.getString(0), Toast.LENGTH_SHORT);
                    toast1.show();
                    Toast toast2 = Toast.makeText(LoginActivity.this, myCursor.getString(1), Toast.LENGTH_SHORT);
                    toast2.show();
                    Toast toast3 = Toast.makeText(LoginActivity.this, myCursor.getString(2), Toast.LENGTH_SHORT);
                    toast3.show();
                    Toast toast4 = Toast.makeText(LoginActivity.this, myCursor.getString(3), Toast.LENGTH_SHORT);
                    toast4.show();

                } while(myCursor.moveToNext());
            }

            myCursor.close();
            mDatabase.close();
        } catch (SQLiteException e) { // If there are any syntax errors in the SQL query, a SQLiteException will get caught.
            Toast toast = Toast.makeText(LoginActivity.this, "Error accessing the database", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
