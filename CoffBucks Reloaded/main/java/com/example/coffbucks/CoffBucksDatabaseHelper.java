package com.example.coffbucks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Witch on 2/18/2017.
 */

public class CoffBucksDatabaseHelper extends SQLiteOpenHelper {

    /* If you want to delete this database you have to use the following commands :
        Context context = getBaseContext();
        context.deleteDatabase("CoffBucks");
        SQLiteOpenHelper helper = new CoffBucksDatabaseHelper(this);

        db = helper.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + "DRINK");
        db.close();

     */

    private static final String DB_NAME = "CoffBucks"; // the name of the database
    private static final int DB_VERSION = 2; // the version of the database

    // To specify the name and version of the Database you need to pass the values to the (copy)constructor in the SQLiteOpenHelper superclass
    CoffBucksDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // The onCreate() method will(of this class) will only run if the user doesn't have the database created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");
        insertDrink(db, "Cappuccino", "Taste now this Italian coffee drink that is traditionally prepared with double espresso, hot milk, and steamed milk foam.", R.drawable.cappuccino);
        insertDrink(db, "Frappe", "Taste now this foam-covered iced coffee drink made from spray-dried instant coffee.", R.drawable.frappuccino);
        insertDrink(db, "Mocaccino", "Taste now this drink based on espresso and hot milk, but with added chocolate, in the form of sweet cocoa powder, although you can choose to use chocolate syrup.", R.drawable.moccacino);
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

        insertDrink(db, "Irish Coffee", "Taste now this cocktail consisting of hot coffee, Irish whiskey, and sugar , stirred, and topped with thick cream.", R.drawable.irish_coffee);
        insertDrink(db, "Caffè Corretto", "Taste now this Italian beverage, consisting of a shot of espresso with a small amount of liquor, like grappa, sambuca or brandy", R.drawable.corretto);
        insertDrink(db, "Caffè Breve", "Taste now the American variation of a latte: a milk-based espresso drink using steamed half-and-half mixture of milk and cream instead of milk.", R.drawable.breve);
        insertDrink(db, "Affogato", "Taste now this coffee-based dessert that takes the form of a scoop of vanilla gelato or ice cream topped or drowned with a shot of hot espresso. Some variations also include a shot of amaretto, Bicerin or other liqueur.", R.drawable.affogato);
        db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }


    // Method used to populate the Database
    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        // Container used to store (a) value(s) in (a) column(s).
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID",resourceId);
        // Insert the drinkValues into the DRINK table
        db.insert("DRINK", null, drinkValues);
    }

}
