package com.example.coffbucks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Witch on 2/18/2017.
 */

public class CoffBucksDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CoffBucks"; // the name of the database
    private static final int DB_VERSION = 2; // the version of the database

    // To specify the name and version of the Database you need to pass the values to the (copy)constructor in the SQLiteOpenHelper superclass
    CoffBucksDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // The onCreate() method will(of this class) will only run if the user doesn't have the database created
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    // The onUpgrade() method gets called when the current DB_VERSION is greater than the one the user has.
    // The onDowngrade() method gets called when the current DB_VERSION is lower than the one the user has.
    // Example:
    /*
    db.update("DRINK", Mochachino, "NAME = ?", new String[] {"Espresso"});
    // <=> Set NAME to Mochachino where NAME = Espresso
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDatabase(db, oldVersion, newVersion);
    }

    // This method is used in both onCreate() and onUpgrade methods to update the database based on the current DB version
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion,int newVersion){
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Cappuccino", "Taste now this SUGAR-FREE coffee-based drink!", R.drawable.cappuccino);
            insertDrink(db, "Frappuccino", "The best middle-eastern coffee drink!", R.drawable.frappuccino);
            insertDrink(db, "Moccacino", "Taste now the most popular italian coffee drink!", R.drawable.moccacino);
        }

        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID",resourceId);
        db.insert("DRINK", null, drinkValues);
    }

}
