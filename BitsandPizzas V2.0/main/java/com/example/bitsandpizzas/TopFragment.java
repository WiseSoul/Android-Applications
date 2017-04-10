package com.example.bitsandpizzas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class TopFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_top, container, false);
        // Create the RecyclerView
        RecyclerView topPizzaRecycler = (RecyclerView) layout.findViewById(R.id.pizza_recycler);

        // Only display 2 pizzas
        String[] pizzaNames = new String[2];
        for(int i = 0; i < 2; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }

        int[] pizzaImageIds = new int[2];
        for(int i = 0; i < 2; i++) {
            pizzaImageIds[i] = Pizza.pizzas[i].getImageResourceId();
        }

        // Create a layout manager for the RecyclerView to arrange the CardViews.
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        topPizzaRecycler.setLayoutManager(manager);
        // Create the Custom Adapter.
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImageIds);
        // Set the adapter.
        topPizzaRecycler.setAdapter(adapter);

        // When the onBindViewHolder() method gets called, the CardView's onClickListener() will call customOnClick()
        // Override this in any other RecyclerView that uses CaptionedImagesAdapter
        // Note : You can use this method before pizzaRecycler.setAdapter() because captionedImagesAdapter is an object passed by reference!!!
        adapter.setCustomListener(new CaptionedImagesAdapter.CustomListener() {
            @Override
            public void customOnClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_POSITION, position);
                getActivity().startActivity(intent);

            }
        });

        return layout;
    }
}
