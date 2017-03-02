package com.example.coffbucks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.coffbucks.DrinkCategoryActivity.EXTRA_DRINKNO;


// This is a DetailLevelActivity(in which you show details of certain items selected in other activities(like CategoryActivities).

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Get the clicked item's id from the intent
        // it needs to be transformed into Integer because get*() function transforms it's parameter to a String.
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        // Execute the UpdateDrinkActivityTask AsyncTask with passing drinkNo as it's argument
        new UpdateDrinkActivityTask().execute(drinkNo);
    }

    // Update the database when the checkbox is clicked
    public void onFavoriteClick(View view) {

        // Retrieve the instance of the drinkNo passed from DrinkCategoryActivity
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        // Execute the UpdateDrinkDBTask AsyncTask with passing drinkNo as it's argument
        new UpdateDrinkDBTask().execute(drinkNo);
}

    /*
    AsyncTask performs asynchronous tasks

    The AsyncTask class lets you perform operations in the background. When they’ve finished running, it then allows you to
    update views in the main event thread. If the task is repetitive, you can even use it to publish the progress of the task while it’s running.
    You create an AsyncTask by extending the AsyncTask class, and implementing its doInBackground() method. The code in
    this method runs in a background thread, so it’s the perfect place for you to put database code. The AsyncTask class also has
    an onPreExecute() method that runs before doInBackground(), and an onPostExecute() method that runs afterward.
    There’s an onProgressUpdate() method if you need to publish task progress.

    Here’s what it looks like:
    private class MyAsyncTask extends AsyncTask<Params, Progress, Result>
    protected void onPreExecute() {
    //Code to run before executing the task
    }
    protected Result doInBackground(Params... params) {
    //Code that you want to run in a background thread
    }
    protected void onProgressUpdate(Progress... values) {
    //Code that you want to run to publish the progress of your task
    }
    protected void onPostExecute(Result result) {
    //Code that you want to run when the task is complete
    }
    }
     */

    // This class is used to Update the Drink Activity every time it is opened
    public class UpdateDrinkActivityTask extends AsyncTask<Integer, Void, Boolean> {

        // We need to declare the Database and the Cursor that will store the db.query globally in order to access them in onPostExecute() method
        SQLiteDatabase db;
        Cursor theCursor;


        // This method runs in the Background thread
        protected Boolean doInBackground(Integer... drinks) {

            // Get the Id of the Drink in order to properly create Drink Activity
            int drinkNo = drinks[0];

            // Get a refference to the DatabaseHelper
            SQLiteOpenHelper coffBucksDatabaseHelper = new CoffBucksDatabaseHelper(DrinkActivity.this);

            try { // Open the Database to read from it to create the cursor
                db = coffBucksDatabaseHelper.getReadableDatabase();

                // Create a cursor that will hold data based on the following SQL query :
                // SELECT name,description,image_resource_id FROM DRINK WHERE _id = drinkNo; -> Always returns only one row.
                theCursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                        "_id = ?", new String[]{Integer.toString(drinkNo)}, null, null, null);

                // return true if the Task ran successfully
                return true;
            } catch (SQLiteException e) { // Otherwise catch a SQLiteException and return false
                return false;
            }
        }

        // This method will run only after the primary task has ended it's execution
        protected void onPostExecute(Boolean success) {

            // If the task ran successfully
            if(success) {
                // Move to the first and only record in the Cursor
                if (theCursor.moveToFirst()) {

                    // Initialise the activity's views based on the details from the cursor

                    TextView drinkName = (TextView) findViewById(R.id.name);
                    // getString(0) <=> The first element(String) in the row/record
                    drinkName.setText(theCursor.getString(0));

                    TextView drinkDescription = (TextView) findViewById(R.id.description);
                    // getString(1) <=> The first element(String) in the row/record
                    drinkDescription.setText(theCursor.getString(1));

                    ImageView drinkImage = (ImageView) findViewById(R.id.photo);
                    // getInt(2) <=> The second element(int) in the row/record
                    drinkImage.setImageResource(theCursor.getInt(2));
                    // Set the image description. That is the drink name.
                    drinkImage.setContentDescription(theCursor.getString(0));

                    CheckBox drinkCheckBox = (CheckBox) findViewById(R.id.favorite);
                    // theCursor.getInt(3) == 1  <=>  if it ( theCursor.getInt(3) ) is equal to 1 , return true. Otherwise return false.
                    drinkCheckBox.setChecked(theCursor.getInt(3) == 1);

                }
                // Close the cursor and the database only after we stopped using the cursor
                theCursor.close();
                db.close();
            }

            else { // If the task didn't run successfully , create a toast to show a message
                    Toast toast = Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    // This class is used to update the Database when a user clicks on the Favorite CheckBox
    public class UpdateDrinkDBTask extends AsyncTask<Integer, Void, Boolean> {

        // This is the container in which we'll store a column name and the value to be added in it.
        ContentValues drinkValues;

        // Initialise the CheckBox and the drinkValues with the proper data
        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE",favorite.isChecked());
        }

        // This is the method that runs in the background and executes certain tasks in a background thread
        protected Boolean doInBackground(Integer... drinks) {
            // The CheckBox will be pressed once at a time so we need to get the first value inserted in Integer... drinks in order to update the DB based on it
            int drinkNo = drinks[0];
            // Get a refference to the DatabaseHelper
            SQLiteOpenHelper coffBucksDatabaseHelper = new CoffBucksDatabaseHelper(DrinkActivity.this);

            try { // Open the Database for writing data
                SQLiteDatabase db = coffBucksDatabaseHelper.getWritableDatabase();
                  // Update the DB in the following form : Add drinkValues ( in column FAVORITE add the value isChecked() ) WHERE _id = drinkNo;
                db.update("DRINK", drinkValues,
                          "_id = ?" , new String [] {Integer.toString(drinkNo)});
            // Close the database
            db.close();
            // Return true if the task completed successfully
            return true;
                } catch (SQLiteException e) {
                    return false;

                }
        }

        protected void onPostExecute(Boolean success) {
            // If the doInBackGround() method didn't run successfully
            if (!success) { // Create a toast to show a message
                Toast toast = Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }


    }

}
