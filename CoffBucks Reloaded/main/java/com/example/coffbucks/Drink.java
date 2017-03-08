package com.example.coffbucks;

/**
 * Created by Witch on 10/11/2016.
 */

public class Drink {

    private String name;
    private String description;
    private int imageResourceId;

    // drink is a array of drinks;
    public static final Drink[] drinks = { new Drink("Frappuccino","The best middle-east coffee drink!",R.drawable.frappuccino),
                                          new Drink("Moccacino","Taste now the most popular italian coffee drink!",R.drawable.moccacino),
                                          new Drink("Cappuccino","Taste now this SUGARFREE coffee-based drink!",R.drawable.cappuccino)
                                        };

    //class constructor
    private Drink(String name,String description,int imageResourceId)
    {
        this.name=name;
        this.description=description;
        this.imageResourceId=imageResourceId;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getImageResourceId()
    {
     return imageResourceId;
    }


    // this function is critical as it is needed when you call DrinkObject.toString()
    // when you transmit data through a arrayAdapter to a ListView, you call setArrayAdapter() function,which will call DrinkObject.toString() to know what will it show in the ListView
    public String toString()
    {
        return this.name;
    }

}
