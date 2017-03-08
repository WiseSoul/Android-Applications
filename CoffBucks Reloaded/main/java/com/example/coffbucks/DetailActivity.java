package com.example.coffbucks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {


    // This is a string used to save the Id of the Workout selected in the ListFragmentView.
    public static final String EXTRA_WORKOUT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Create a workoutDetailFragment which will show the details of the Workout clicked in the ListFragmentView.
        // We also need to specify the fragment's id defined the activity_detail.xml layout in order to show the details of the Workout.
        // getFragmentManager() -> this is the manager of the fragment utilised in getting access to specific functions of the fragment.
       WorkoutDetailFragment workoutDetailFragment = (WorkoutDetailFragment)
               getFragmentManager().findFragmentById(R.id.detail_frag);
        // Retrieve the Id of the Workout clicked in the ListFragmentView.
        int workoutId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        // Set the workoutId in the workoutDetailFragment to show the details for the selected Workout.
        workoutDetailFragment.setWorkout(workoutId);
    }

}
