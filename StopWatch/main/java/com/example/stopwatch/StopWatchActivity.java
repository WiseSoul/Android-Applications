package com.example.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopWatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        // savedInstanceState -> it's value is initially null,but after you tilt your screen onDestroy() is called,and the state loses all it's previous data.
        // you have to save all the data you previously had,if you want your app to continue running after you tilt the screen
        // (savedInstanceState != null) => onDestroy() was called,so the data you saved must be put back in the variabiles you lost within the initial state.
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

/*
    // similar to onPause(),but it runs before it.
        @Override
        protected void onStop(){
            super.onStop();
            wasRunning = running;
            running = false;
        }

    //similar to onResume() but it runs after it.
        @Override
        protected void onStart() {
            super.onStart();
            if (wasRunning) {
                running = true;
            }
        }
*/
    // pauses the app's runTimer,if you put the app in the background
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //resumes the app from where it left off when you re-open it from the background
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    //saving the data in the bundle savedInstanceState,before onDestroy() is called.

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {

        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {

       //declaring a handler to schedule data that is about to be run
       final Handler handler = new Handler();
       final TextView textView = (TextView) findViewById(R.id.time_view);

        //use a handler to post code
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                //Format the seconds into hours,minutes,seconds.
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                //set the textView text
                textView.setText(time);

                if (running) {
                    seconds++;
                }
                //Post the code again with the delay of 1000 miliseconds.
                handler.postDelayed(this, 1000);
            }

        });

    }
}