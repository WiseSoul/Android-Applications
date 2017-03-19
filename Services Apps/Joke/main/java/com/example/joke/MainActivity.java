package com.example.joke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This will run when the button gets clicked
    public void onClick(View view) {
        Intent intent = new Intent(this,DelayedMessageService.class);
        intent.putExtra(EXTRA_MESSAGE,getResources().getString(R.string.button_response));
        // Start the service
        startService(intent);
    }

}
