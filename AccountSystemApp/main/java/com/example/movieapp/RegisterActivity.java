package com.example.movieapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.movieapp.R.drawable.calendar;

public class RegisterActivity extends Activity {

    SQLiteDatabase mDatabase;
    public Calendar calendar;
    private int year, month, day;
    private String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // If you want to empty the database
        /*LoginSystemDatabaseHelper helper = new LoginSystemDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from "+ "ACCOUNTS");
        db.close();*/
    }

    @SuppressWarnings("deprecation")
    public void setBirthday(View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,R.style.DatePickerStyle,
                    BirthdayListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener BirthdayListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view,
                                      int year, int month, int day) {
                    if(month < 10) {
                        birthday = String.valueOf(year) + "/0" + String.valueOf(month + 1) + "/" + String.valueOf(day);
                    }
                    else {
                        birthday = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
                    }
                    Button birthdayButton = (Button) findViewById(R.id.button_birthday);
                    birthdayButton.setText(birthday);
                }
            };

    public boolean checkAccountExistence() {

        Cursor myCursor;
        String emailValue;
        EditText email = (EditText) findViewById(R.id.register_email);
        emailValue = email.getText().toString();

        LoginSystemDatabaseHelper loginSystemDatabaseHelper = new LoginSystemDatabaseHelper(RegisterActivity.this);
            try {
                mDatabase = loginSystemDatabaseHelper.getWritableDatabase();
                myCursor = mDatabase.query("ACCOUNTS", new String[] {"NAME", "EMAIL", "PREFERENCE", "BIRTHDAY"}, null, null, null, null, null);

                if (myCursor.moveToFirst()) {
                    do {
                        if (emailValue.equals(myCursor.getString(1))) {
                            return true;
                        }
                    } while(myCursor.moveToNext());
                }

                myCursor.close();
                mDatabase.close();
            } catch (SQLiteException e) { // If there are any syntax errors in the SQL query, a SQLiteException will get caught.
                Toast toast = Toast.makeText(RegisterActivity.this, "Error accessing the database", Toast.LENGTH_SHORT);
                toast.show();
            }
        return false;
        }

    public boolean goodEmail(String email) {

        if ( (!email.contains("@")) || (!email.contains(".")) ) {
            return false;
        }
        return true;
    }

    public boolean goodPassword(String password) {
        return password.matches(".*\\d+.*");
    }

    public void onRegisterComplete(View view) {

        EditText name = (EditText) findViewById(R.id.register_name);
        EditText email = (EditText) findViewById(R.id.register_email);
        EditText password = (EditText) findViewById(R.id.password_register2);
        EditText rePassword = (EditText) findViewById(R.id.password_register);
        Spinner movieGenre = (Spinner) findViewById(R.id.movie_type);
        String genre = String.valueOf(movieGenre.getSelectedItem());
        boolean toContinue = true;

        if ( (!name.getText().toString().equals("")) && (!email.getText().toString().equals("")) && (!password.getText().toString().equals("")) &&
             (!rePassword.getText().toString().equals("")) && (!birthday.equals("")))  {

            if (!password.getText().toString().equals(rePassword.getText().toString())) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT);
                toast.show();
                toContinue = false;
            }
            if (password.length() < 4) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Password is too short(minimum is 4 characters).", Toast.LENGTH_SHORT);
                toast.show();
                toContinue = false;
            }
            if (!goodEmail(email.getText().toString())) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Invalid email.", Toast.LENGTH_SHORT);
                toast.show();
                toContinue = false;
            }
            if (!goodPassword(password.getText().toString())) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Password should contain at least 1 number.", Toast.LENGTH_SHORT);
                toast.show();
                toContinue = false;
            }
            if (toContinue) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME",name.getText().toString());
                contentValues.put("EMAIL", email.getText().toString());
                contentValues.put("PASSWORD", password.getText().toString());
                contentValues.put("PREFERENCE", genre);
                contentValues.put("BIRTHDAY", birthday);

                if (!checkAccountExistence()) {
                    LoginSystemDatabaseHelper loginSystemDatabaseHelper = new LoginSystemDatabaseHelper(RegisterActivity.this);
                    try {
                        mDatabase = loginSystemDatabaseHelper.getWritableDatabase();
                        mDatabase.insert("ACCOUNTS", null, contentValues);
                        Toast toast = Toast.makeText(RegisterActivity.this, "Account created.", Toast.LENGTH_SHORT);
                        toast.show();
                        mDatabase.close();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        LoginActivity.fromActivity = true;
                        startActivity(intent);

                    } catch (SQLiteException e) {
                        Toast toast = Toast.makeText(RegisterActivity.this, "Error accessing the database", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(RegisterActivity.this, "Email already in use.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    public boolean doesTableExist(String tableName, boolean openDb) {

        LoginSystemDatabaseHelper helper = new LoginSystemDatabaseHelper(this);
        if(openDb) {
            if(mDatabase == null || !mDatabase.isOpen()) {
                mDatabase = helper.getReadableDatabase();
            }

            if(!mDatabase.isReadOnly()) {
                mDatabase.close();
                mDatabase = helper.getReadableDatabase();
            }
        }
        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor!=null) {
            if (cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
