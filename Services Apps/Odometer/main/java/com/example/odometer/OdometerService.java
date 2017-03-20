package com.example.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.security.Permission;

import static java.security.AccessController.getContext;


public class OdometerService extends Service {

    // Initialise the IBinder with a OdometerBinder() object that extends Binder class.
    private final IBinder binder = new OdometerBinder();
    // This variable holds the distance in meters that the device has covered.
    private static double distanceInMeters;
    // This variable holds the lastLocation the device has been updated at.
    private static Location lastLocation = null;

    // When you create a bound service, you have to define a Binder object.
    // It enables the activity to bind to the service.
    public class OdometerBinder extends Binder {
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    // The onBind() method gets called when the activity binds to the service.
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        // Set up the location listener when the service is created
        LocationListener listener = new LocationListener() {
            @Override // Called when the location of the device changed.
            public void onLocationChanged(Location location) {
                // Used for the first update of lastLocation
                if (lastLocation == null) {
                    // Update the lastLocation with the new Location
                    lastLocation = location;
                }
                // Calculate the distance in meters using distanceTo() method.
                distanceInMeters += location.distanceTo(lastLocation);
                // Update the lastLocation with the new Location
                lastLocation = location;
            }

            // The next three methods get called when the GPS is enabled or disabled
            // Or if its status has changed

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        // Get a reference to the LocationManager by getting the LOCATION_SERVICE System Service.
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Use the LocationManager to request LocationUpdates for the location listener.
        // Everytime we use a service that needs to access specific Android applications,like the GPS in this case,we need to give permissions to our app in AndroidManifest.xml
        // Also don't forget to declare the service in AndroidManifest.xml
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // The GPS provider
                                          1000, // Ask for updates every second
                                          1, // The distance in meters
                                          listener); // The LocationListener to be updated

    }

    // Method to convert the distance traveled to km and return it.
    public double getKm() {
        return distanceInMeters;
    }











}
