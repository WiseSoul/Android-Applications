package com.example.beerselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BeerSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_selector);
    }

    private BeerExpert expert = new BeerExpert();

    public void onClickFindBeer(View view) {
        //Get a reference to the TextView
        TextView brands = (TextView) findViewById(R.id.brands);

        //Get a reference to the Spinner
        Spinner color = (Spinner) findViewById(R.id.color);

        //Get the selected item in the Spinner
        String beerType = String.valueOf(color.getSelectedItem());

        //Display the selected item
        brands.setText(beerType);

        List<String> brandsList = expert.getBrands(beerType);

        StringBuilder brandsFormatted = new StringBuilder();

        for(String brand : brandsList)
        {
            brandsFormatted.append(brand).append('\n');
        }

        brands.setText(brandsFormatted);
    }
}

