package com.example.coffbucks;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.health.ServiceHealthStats;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OdometerActivity extends AppCompatActivity {
    // This is used for the OdometerService
    private  OdometerService odometerService;
    // This is used to store whether or not the activity's bound to the service
    private boolean bound = false;
    // This is used to check whether or not the Odometer is running
    protected boolean running = false;
    // This is used to store the distance traveled by the device
    protected double distance = 0.0;


    // Establish a connection with the service, so that the Activity can bound to it.
    private ServiceConnection connection = new ServiceConnection() {
        @Override // When the service is connected...
        public void onServiceConnected(ComponentName name, IBinder binder) { // <- We need to define a service connection
            // Create a odometerBinder based on a IBinder obj, casted to OdometerBinder
            // This is why we always need to define a Binder object in our Service
            // (because onServiceConnected() has a IBinder as a parameter that is used to create an instance of the service)
            OdometerService.OdometerBinder odometerBinder =
                                   (OdometerService.OdometerBinder) binder;
           // Get a reference to the OdometerService when the service is connected.
            odometerService = odometerBinder.getOdometer();
            bound = true;
        }

        @Override   // When the service is disconnected...
        public void onServiceDisconnected(ComponentName name) {
        bound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // savedInstanceState -> it's value is initially null,but after you tilt your screen onDestroy() is called,and the state loses all it's previous data.
        // you have to save all the data you previously had,if you want your app to continue running after you tilt the screen
        // (savedInstanceState != null) => onDestroy() was called,so the data you saved must be put back in the variables you lost within the initial state

        if (savedInstanceState != null) {
            savedInstanceState.getDouble("distance");
            savedInstanceState.getBoolean("bound");
            savedInstanceState.getBoolean("running");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odometer);
        // This method is called when the activity is created.
        updateKm();
    }

    // Saving the data in the bundle savedInstanceState,before onDestroy() is called.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("bound", bound);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putDouble("distance",distance);
    }

    // We want the service to be bound to the activity when the activity starts.
    // That is because onStart() is on average more frequently called than onCreate()
    @Override
    protected void onStart() {
        super.onStart();
        // Bind the service when the activity starts.
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    // And to unbound when the activity stops.
    // That is because onStop() is on average more frequently called than onDestroy()
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind the service when the activity stops.
        if (bound) {
            unbindService(connection);
        }
    }

    private void updateKm() {
        // Get a reference to the TextView that will display the distance.
        final TextView distanceView = (TextView) findViewById(R.id.distance);
        // Create a handler to post data using a thread.
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Check to avoid NullPointerException
                if (odometerService != null && running) {
                    distance = odometerService.getKm();
                }
                // Create the string that holds the distance
                String distanceStr = String.valueOf(distance);
                // Set the text
                distanceView.setText(distanceStr);
                // Update the distance every second
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickReset(View view) {
        running = false;
        distance = 0.01;
    }

    public void onClickStop(View view) {
        running = false;
    }

}

