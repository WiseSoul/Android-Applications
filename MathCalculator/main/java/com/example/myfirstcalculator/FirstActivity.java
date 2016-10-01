package com.example.myfirstcalculator;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    public void createNotification() {


        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
        nBuilder.setContentTitle("Credits");
        nBuilder.setContentText("Made by Silviu Patras");
        nBuilder.setSmallIcon(R.drawable.ic_stat_name);

        Notification notification = nBuilder.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.notify(1, notification);
    }

    @Override   // int main()
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

//My portion of code

        Button creditsButton = (Button) findViewById(R.id.creditsView);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
            }

        });

        final TextView textView;
        final EditText percentageView;
        final EditText numberView;
        final TextView textView2;

        textView = (TextView) findViewById(R.id.textView);
        percentageView = (EditText) findViewById(R.id.percentageView);
        numberView = (EditText) findViewById(R.id.numberView);
        textView2 = (TextView) findViewById(R.id.textView2);

        float first = 0;
        textView.setText(Float.toString(first));

        Button calcButton = (Button) findViewById(R.id.calcButton);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verificationOne = (String)percentageView.getText().toString();
                String verificationTwo = (String)numberView.getText().toString();

                if(!(verificationOne.matches("") || verificationTwo.matches("") || (verificationOne.matches("") && verificationTwo.matches("")))) {

                    float percentage = Float.parseFloat(percentageView.getText().toString());
                    float dec = percentage / 100;
                    float total = dec * Float.parseFloat(numberView.getText().toString());

                    textView.setText(Float.toString(total));
                    textView2.setText("That's right!");
                }

                else
                {
                    Toast error = (Toast) Toast.makeText(getApplicationContext(),"No numbers available!", Toast.LENGTH_SHORT);
                    error.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    error.show();

                    textView.setText("0");
                    textView2.setText(":(");
                }

            }
        });
    }
}
// end of my code


