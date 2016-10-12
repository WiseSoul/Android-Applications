package com.example.coffbucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


// this is a TopLevelActivity(First and Main Activity to navigate through the app)

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);


    //Create an OnItemClickListener to listen for clicks on the ListView declared in the layout

        AdapterView.OnItemClickListener itemClickListener =
                                    new AdapterView.OnItemClickListener(){
                                        //onItemClick -> retrieves the click action
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

            //Add the listener to the list view
            ListView listView = (ListView) findViewById(R.id.list_options);
            listView.setOnItemClickListener(itemClickListener);
    }
}
