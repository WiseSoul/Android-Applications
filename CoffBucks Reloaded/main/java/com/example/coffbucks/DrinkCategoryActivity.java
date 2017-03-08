package com.example.coffbucks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

// This is a CategoryActivity(extends a ListActivity(a activity that uses a ListView)

public class DrinkCategoryActivity extends ListActivity {

    // Declaration of the database and the cursor used in the SQL query selection.
    private SQLiteDatabase db;
    private Cursor cursor;


    // the variable used to store the id of the item clicked in DrinkCategoryActivity's ListView
    // it needs to be declared String so it can be passed and retrieved properly by get*() function

    public static final String EXTRA_DRINKNO = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listDrinks = getListView();

        try {
            SQLiteOpenHelper coffBucksDatabaseHelper = new CoffBucksDatabaseHelper(this);
            // Get a reference to the database created in CoffBucksDatabaseHelper.java
            db = coffBucksDatabaseHelper.getReadableDatabase();
            // Create the cursor
            cursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this, // The current Context (Application)
                    android.R.layout.simple_list_item_1, // Tells Android to display each row in the cursor as a single text view in the ListView
                    cursor, // The cursor that contains the SQL query selection from the Database
                    new String[]{"NAME"}, // Which column values to be copied in the ListView from the cursor
                    new int[]{android.R.id.text1}, // Map the contents of the NAME column to the text in the ListView
                    0); // Options/Flags

            // Using the cursor adapter ...
            listDrinks.setAdapter(listAdapter);

        } catch (SQLiteException e) { // If there are any syntax errors in the SQL query, a SQLiteException will get caught.
            Toast toast = Toast.makeText(DrinkCategoryActivity.this, "Error accessing the database", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the cursor that holds specific data from the database
        cursor.close();
        // Close the database
        db.close();
    }

    // in a ListActivity we don't need to declare a AdapterView.OnItemClickListener because it already has one predefined
    // onListItemClick() -> similar to onItemClick(), it retrieves the click action
    @Override
    public void onListItemClick(ListView listView,// the whole ListView
                                View itemView,// the item that's being clicked
                                int position,//its position: 0-first item,1-second item,...
                                long id) /*its id(similar to it's position if the position in the ListView is similar to the index in the array ).*/{
        //will open a new activity if any itemView is clicked.
        Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);

        // Carry in the intent,using the variable EXTRA_DRINKNO the value of the id of the item clicked in the ListView
        // Remember to cast (long) id to (int) id when using putExtra() method in a intent
        // Otherwise the app will crash
        intent.putExtra(EXTRA_DRINKNO,(int) id);
        startActivity(intent);
    }
}

