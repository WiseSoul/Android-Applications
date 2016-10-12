package com.example.coffbucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;

// This is a CategoryActivity(extends a ListActivity(a activity for ListViews),in which you have lists(in our case:categories) of certain items)

public class DrinkCategoryActivity extends ListActivity {

    // the variable used to store the item clicked in DrinkCategoryActivity's ListView
    // it needs to be declared String so it can be passed and retrieved properly by get*() function

    public static final String EXTRA_DRINKNO="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getListView() -> gets the current ListView.
        //we already have a pre-defined ListView because this activity extends ListActivity(activity special made for ListViews)
        //ListActivity also has a pre-defined layout
        ListView listDrinks = getListView();

        // creates a array adapter that acts as a bridge between the ListView retrieved and the Drink.drinks array.
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
                //current activity
                this,
                // shows every item in a unique view
                android.R.layout.simple_list_item_1,
                //the drinks array declared in Drink.java class
                Drink.drinks);

        // Transmit the toString() data of the Drink.drinks array
        // we have 3 objects in the array,so it will call drinks[0].toString(),drinks[1].toString(),drinks[2].toString()
        listDrinks.setAdapter(listAdapter);
    }

    // in a ListActivity we don't need to declare a AdapterView.setOnClickListener because it already has one predefined
    // onListItemClick -> similar to onItemClick, it retrieves the click action
    @Override
    public void onListItemClick(ListView listView,// the whole ListView
                                View itemView,// the item that's being clicked
                                int position,//its position: 0-first item,1-second item,...
                                long id) /*its id(similar to it's position if the position in the ListView is similar to the index in the array ).*/{
        //will open a new activity if any itemView is clicked.
        Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
        //adds to the DrinkActivity.EXTRA_DRINKNO variable,the id(or position) of the item clicked
        intent.putExtra(EXTRA_DRINKNO,(int) id);
        startActivity(intent);
    }
}
