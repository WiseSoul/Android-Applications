package com.example.coffbucks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;


// This is a TopLevelActivity(First and Main Activity to navigate through the app)
// It displays the list of drinks
public class TopLevelActivity extends AppCompatActivity {

    // The database and cursor with specific data(from the database) declarations.
    // We declare these globally so we can access them everywhere we need
    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    // the variable used to store the item clicked in favoritesList ListView
    // this variable can be declared either in DrinkActivity,or in TopLevelActivity,but you have to specify which one it is in the intent.
    // it needs to be declared String so it can be passed and retrieved properly by get*() function
       public static final String EXTRA_DRINKNO = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        // Create an OnItemClickListener to listen for clicks on the Options ListView

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){ // -> The OnItemClickListener constructor
                    //Override onItemClick() method that retrieves the click action
                    @Override
                    public void onItemClick(AdapterView<?> listView, //the whole list of views
                                            View v,// the view that has been clicked
                                            int position, // view's position (0 - drinks,1 - food, 3 -..)
                                            long id) //view's id (0 - drinks,1 - food, 3 -..)
                    {
                        if(id == 0) {
                            Intent intent = new Intent(TopLevelActivity.this,DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                    }

                };
        //Add the listener to the ListView
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        // Create the cursor with the required data from the DB and then populate the list_favorites ListView
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper coffBucksDatabaseHelper = new CoffBucksDatabaseHelper(this);
            // Open Database
            db = coffBucksDatabaseHelper.getReadableDatabase();
            // Get data in the cursor with the following SQL selection : Select _id,NAME FROM DRINK WHERE FAVORITES = 1;
            favoritesCursor = db.query("DRINK", new String[] { "_id", "NAME" },
                                     "FAVORITE = 1",
                                     null, null, null, null
                                     );
            CursorAdapter favoritesAdapter = new SimpleCursorAdapter(TopLevelActivity.this, // The current Context (Application)
                    android.R.layout.simple_list_item_1, // Tells Android to display each row in the cursor as a single text view in the ListView
                    favoritesCursor, // The cursor that contains the SQL query selection from the Database
                    new String[]{"NAME"}, // Which column values to be copied in the ListView from the cursor
                    new int[]{android.R.id.text1}, // Map the contents of the NAME column to the text in the ListView
                    0); // Options/Flags

            // Set the created CursorAdapter on the listFavorites ListView to pass the required values from the favoriteCursor.
            listFavorites.setAdapter(favoritesAdapter);

        } catch (SQLiteException e) { // If there are any syntax errors in the SQL query, a SQLiteException will get caught.
            Toast toast = Toast.makeText(TopLevelActivity.this, "Error accessing the database", Toast.LENGTH_SHORT);
            toast.show();
        }

        //Create an OnItemClickListener to listen for clicks on the listFavorites ListView declared in the layout

        AdapterView.OnItemClickListener favoriteItemClickListener = new AdapterView.OnItemClickListener() { // The OnItemClickListener constructor
            // Override the onItemClick() method to listen for clicks in a specific ListView
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // If a item is clicked,create an intent to open DrinkActivity
                Intent intent = new Intent(TopLevelActivity.this,DrinkActivity.class);
                // Pass the id of the item clicked to EXTRA_DRINKNO variable.

                // Remember to cast (long) id to (int) id when using putExtra() method in an intent
                // Otherwise the app will crash
                intent.putExtra(EXTRA_DRINKNO, (int) id);
                // Start the activity.
                startActivity(intent);
            }
        };

        // Set the Listener to listen for clicks in the listFavorites ListView,using the AdapterView.OnItemClickListener()
        listFavorites.setOnItemClickListener(favoriteItemClickListener);
    }

    // In order to refresh the Favorites ListView based on the cursor's values stored from the database, we need to use the CursorAdapter.changeCursor() method
    // We will do this in the activity's onRestart() method because it is the first method called when we return to a Activity
    @Override
    public void onRestart() {
        super.onRestart();
        try { // Get a refference to the Database Helper
            CoffBucksDatabaseHelper coffBucksDatabaseHelper = new CoffBucksDatabaseHelper(this);
            // Open the database stored in CoffBucksDatabaseHelper.java
            coffBucksDatabaseHelper.getReadableDatabase();

            // Get the data in the cursor with the following SQL selection : Select _id,NAME FROM DRINK WHERE FAVORITE = 1;
            Cursor newFavoritesCursor = db.query("DRINK", new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null
            );

            // Get a refference to the list_favorites ListView used to show the favorite drinks selected by the user.
            ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
            // Retrieve the adapter used by the listFavorites ListView in the onCreate() method.
            // This is very important if we want to use CursorAdapter.changeCursor()
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();

            // Change the cursor used initially in the Cursor Adaptor (in onCreate() method) using CursorAdapter.changeCursor()
            // CursorAdapter.changeCursor() not only changes the old cursor with the new one, but also repopulates the list_favorites ListView with the new Cursor.
            // It does this by recreating the adaptor using newFavoritesCursor and then recalling listFavorites.setAdapter(adapter);
            adapter.changeCursor(newFavoritesCursor);
            // Refresh favoritesCursor with the current favorite drinks in the database
            favoritesCursor = newFavoritesCursor;

        } catch (SQLiteException e) { // If there are any syntax errors in the SQL query, a SQLiteException will get caught.
                Toast toast = Toast.makeText(this, "Error accessing the database", Toast.LENGTH_SHORT);
                toast.show();
            }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the cursor that holds specific data from the database
        favoritesCursor.close();
        // Close the database
        db.close();
    }
}
