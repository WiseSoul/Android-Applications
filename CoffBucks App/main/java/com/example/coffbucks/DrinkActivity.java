package com.example.coffbucks;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

// This is a DetailLevelActivity(in which you show details of certain items selected in other activities(like CategoryActivities).

public class DrinkActivity extends AppCompatActivity {

    // the variable used to store the item clicked in DrinkCategoryActivity's ListView
    // this variable can be declared either in DrinkActivity,or in DrinkCatergoryActivity,but you have to specify which one it is
    // it needs to be declared String so it can be passed and retrieved properly by get*() function

 //   public static final Integer EXTRA_DRINKNO ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the clicked item's id(or position if it is the case) from the intent
        // it needs to be transformed into Integer because get*() function transforms it's parameter to a String.
        int drinkNo = (Integer) getIntent().getExtras().get(DrinkCategoryActivity.EXTRA_DRINKNO);

        // creates a new item based on the id(of the item clicked before) , from the drinks array defined in Drinks.java
        // retrieves the data of the specified drink of the array.
        Drink drink = Drink.drinks[drinkNo];


        // now that drink object is initialized with it's proper values from the Drink.drinks array(in Drinks.java),we can start populating views.

        //Populate the drink image
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());

        //Populate the drink name
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        //Populate the drink description
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}

