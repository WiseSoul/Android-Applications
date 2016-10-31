package com.example.workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        ImageView photo = (ImageView) findViewById(R.id.Credits);
        photo.setImageResource(R.drawable.credits);
        photo.setContentDescription("Credits");
    }
}
