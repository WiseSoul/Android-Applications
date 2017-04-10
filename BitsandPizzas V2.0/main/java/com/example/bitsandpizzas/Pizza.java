package com.example.bitsandpizzas;


public class Pizza {
    private String name;
    private int imageResourceId;
    // Note: You need to put the images you want to show in the CardView in the drawable-nodpi folder.
    // That is because the CardView always needs to show the images, regardless of the mobile screen size.
    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.drawable.diavolo),
            new Pizza("Funghi", R.drawable.funghi),
            new Pizza("Margherita", R.drawable.margherita)
    };

    private Pizza(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
