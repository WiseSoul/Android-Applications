package com.example.workout;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WorkoutDetailFragment extends Fragment
{
    // This is the workout id,which will will lose it's value when we will rotate the screen.
    private long workoutId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Retrieving the workoutId for when the phone is being rotated
        if(savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        }
        else {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopWatchFragment stopWatchFragment = new StopWatchFragment();
            ft.replace(R.id.stopwatch_container, stopWatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            }

            return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    // We need to implement the title and the description of a specific workout in the onStart() method
    // That's because in the onCreateView() the view(layout) of the fragment hasn't been created
    // Therefore,a nullPointerException will appear,and the program will crash
    // We fix this by writing the code in the onStart() method.
    // note: onStart() < onCreateView();
    @Override
    public void onStart(){
        super.onStart();
        // Retrieves the view of the fragment
        // note: Fragments are not a type of View,therefore it needs to be retrieved by using getView();
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            // Create the selected workout,based on the workoutId
            Workout workout = Workout.workouts[(int) workoutId];
            title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }

    //Saving the workoutId for when the phone is being rotated
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putLong("workoutId",workoutId);
    }
    // This function will get called in the ItemListClick method.
    public void setWorkout(long id){
        this.workoutId = id;
    }
}