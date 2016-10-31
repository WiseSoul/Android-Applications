package com.example.workout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {

    // This application contains 2 Layouts: One for standard mobile phones and One for tablets with large-layouts needs.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method needs to be implemented in MainActivity because we need to send a intent to open DetailActivity if the app is ran on a standard mobile.
    // note: Intents cannot be used inside fragments to open other Activities.
    @Override
    public void itemClicked(long id) {

         // Create a View to check if the app is ran on a tablet or standard mobile.
         View fragmentContainer = findViewById(R.id.fragment_container);

        // If the view is not null,it means the View(FrameLayout) is present,so it must behave differently
        if (fragmentContainer != null) {
            // Create a WorkoutDetailFragment,that will be utilised to replace the FrameLayout in the ActivityMain
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            //Start the fragment transaction for the MainActivity.java
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            // Set the WorkoutDetailFragment on the id of the item that has been clicked.
            details.setWorkout(id);
            //Replace the fragment and add it to the back stack
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            //Get the new and old fragments to fade in and out
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //Commit the transaction
            ft.commit();
        } // Otherwise,we create a intent to open a new Activity where the details of the specific workout will be shown.
          // We need to pass along with the intent,the value of the id of the Workout selected in the ListFragmentView.
        else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int) id);
            startActivity(intent);
        }
    }

    public void OnClickShowCredits(View view)
    {
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        startActivity(intent);
    }
}



