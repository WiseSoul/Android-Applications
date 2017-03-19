package com.example.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.util.Log;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

    // Call the super constructor of the IntentService subclass of Service class
    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    // Declared globally
    //private Handler handler;
    public static final int NOTIFICATION_ID = 5354;

    // This method runs on the main thread, so it creates a new handler on the main thread
    // Used only for when we use a handler to show a toast
    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        // Call the IntentService onStartCommand() method
        return super.onStartCommand(intent, flags, startId);
    }*/

    // This method contains the code to run when the service receives an intent
    @Override
    protected void onHandleIntent(Intent intent) {
     // Synchronize the thread so that the method can be accessed once at a time
        synchronized (this) {
            try { // Wait 10 seconds
                wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String text = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Show the message in the logcat
        showText(text);
    }

    private void showText(final String text) {
        // This logs a piece of text so it can appear in the logcat through Android Studio
       // Log.v("DelayedMessageService", "The message is: " + text);

        // Posts the Toast code to the main thread using the handler
        /*handler.post(new Runnable() {
            @Override
            public void run() { // getApplicationContext() - returns the context of the app
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });*/

        /* Create a Notification so that when we click it, it returns us back to MainActivity */
        // Create the intent
        Intent intent = new Intent(this, MainActivity.class);
        // Create a TaskStackBuilder to make sure the back button will run correctly and to add the intent to MainActivity's back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Gains access to MainActivity's back stack. This should be the same as the activity the intent was created for.
        stackBuilder.addParentStack(MainActivity.class);
        // Add the intent to the TaskStackBuilder
        stackBuilder.addNextIntent(intent); // Now the back button will work properly when the activity is started
        // Use the TaskStackBuilder to create a pending Intent based on the original intent
        // A pending intent is a object that wraps a Intent used for granting another app the right to perform a specific operation
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //FLAG_UPDATE_CURRENT - modifies any existing pending intent

        // Build the Notification
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true) // This makes the notification fade away when clicked
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE) // This makes the notification produce vibration when it appears
                .setContentIntent(pendingIntent) // Set what the notification will do when it gets clicked
                .setContentText(text)
                .build();
        // Create a notificationManager to get the NOTIFICATION_SERVICE
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Notify the system , based on the custom ID and the notification
        notificationManager.notify(NOTIFICATION_ID, notification);

    }


}