package com.example.bitsandpizzas;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PizzaMaterialFragment extends Fragment {

    public final static String EXTRA_POSITION = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Transform the layout of this fragment into a RecyclerView using LayoutInflater.inflate() method.
        // Note: The layout has to also be defined as a RecyclerView in XML.
        RecyclerView pizzaRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_pizza_material, container, false);

        // Create an array of Pizza Names
        String[] pizzaNames = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }

        // Create an array of pizza ImageResourceId's.
        int[] pizzaImageRsrIds = new int[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaImageRsrIds[i] = Pizza.pizzas[i].getImageResourceId();
        }

        // Get a reference to the Custom RecyclerView Adapter.
        CaptionedImagesAdapter captionedImagesAdapter = new CaptionedImagesAdapter(pizzaNames, pizzaImageRsrIds);
        // Set the RecyclerView to use the adapter.
        pizzaRecycler.setAdapter(captionedImagesAdapter);
        // Display the CardViews in a linear list.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        pizzaRecycler.setLayoutManager(linearLayoutManager);

        // When the onBindViewHolder() method gets called, the CardView's onClickListener() will call customOnClick()
        // Override this in any other RecyclerView that uses CaptionedImagesAdapter
        // Note : You can use this method before pizzaRecycler.setAdapter() because captionedImagesAdapter is an object passed by reference!!!
        captionedImagesAdapter.setCustomListener(new CaptionedImagesAdapter.CustomListener() {
            @Override
            public void customOnClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_POSITION, position);
                getActivity().startActivity(intent);
            }
        });

        // The onCreateView() method of a fragment has to return the fragment's layout
        // In this case the layout is the RecyclerView itself.
        return pizzaRecycler;
    }
}
