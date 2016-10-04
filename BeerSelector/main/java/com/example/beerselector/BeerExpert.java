package com.example.beerselector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Witch on 10/4/2016.
 */

public class BeerExpert {
    List<String> getBrands(String color){
        List<String> brands = new ArrayList<String>();
        if(color.equals("amber")){
            brands.add("Jack Amber");
            brands.add("Red Moose");
        } else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }
}

