package com.example.workout;


import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StopWatchFragment extends Fragment implements View.OnClickListener  {

    //Number of seconds displayed on the StopWatch
    private int seconds = 0;
    //Is the StopWatch running?
    private boolean running;
    // Was the StopWatch running before the app was paused?
    private boolean wasRunning;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // savedInstanceState -> it's value is initially null,but after you tilt your screen onDestroy() is called,and the state loses all it's previous data.
        // you have to save all the data you previously had,if you want your app to continue running after you tilt the screen
        // (savedInstanceState != null) => onDestroy() was called,so the data you saved must be put back in the variabiles you lost within the initial state.
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(layout);

        Button startButton = (Button) layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        Button stopButton = (Button) layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        Button resetButton = (Button) layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start_button:
                onClickStart(v);
                break;
            case R.id.stop_button:
                onClickStop(v);
                break;
            case R.id.reset_button:
                onClickReset(v);
                break;
        }
    }

    // pauses the app's runTimer,if you open another app
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //resumes the app from where it left off when you re-open it from the background
    @Override
    public void onResume() {
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

    private void runTimer(View view) {

        //declaring a handler to schedule data that is about to be run
        final Handler handler = new Handler();
        final TextView textView = (TextView) view.findViewById(R.id.time_view);

        //use a handler to post code
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                //Format the seconds into hours,minutes,seconds.
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);

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