package com.example.coffbucks;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class WorkoutListFragment extends ListFragment {

    static interface WorkoutListListener{
        void itemClicked(long id);
    }

    private WorkoutListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create a string with the dimension of the Workout.workouts array
        // Retrieve the names of the workouts.
        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = Workout.workouts[i].toString();
        }
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                names);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // We need to transform the listener in a activity to receive the implemented method itemClicked().
    // Otherwise,we would get a nullPointerException.
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

            this.listener = (WorkoutListListener) activity;
    }

// Tell the listener when an item in the ListView is clicked

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if(listener != null)
        {
            listener.itemClicked(id);
        }
    }
}