package com.example.coffbucks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {


    public String sign = "";
    public String total = "";
    public Double auxDouble1,auxDouble2,auxDouble3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        //Number Keys
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);

        //Clear and Equal
        Button clearbutton = (Button) findViewById(R.id.cButton);
        Button equalbutton = (Button) findViewById(R.id.equalButton);

        //Operations Buttons
        Button plusbutton = (Button) findViewById(R.id.plusButton);
        Button minusbutton = (Button) findViewById(R.id.minusButton);
        Button mulbutton = (Button) findViewById(R.id.mulButton);
        Button divbutton = (Button) findViewById(R.id.divButton);
        final Button sqrtbutton = (Button) findViewById(R.id.sqrtButton);


        //Exit Button
        Button exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                finish();
            }

        });


        //Buttons Listeners

        TextView output = (TextView) findViewById(R.id.editText);

        button0.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView output = (TextView) findViewById(R.id.editText);

                if (total == null)
                    output.append("0");

                else {
                    total = null;
                    output.setText("");
                    output.append("0");
                }
            }
        });

        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("1");

                else
                {   total = null;
                    output.setText("");
                    output.append("1");
                }

            }
        });

        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("2");

                else
                {   total = null;
                    output.setText("");
                    output.append("2");
                }

            }
        });

        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("3");

                else
                {   total = null;
                    output.setText("");
                    output.append("3");
                }
            }
        });

        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("4");

                else
                {   total = null;
                    output.setText("");
                    output.append("4");
                }
            }
        });

        button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("5");

                else
                {   total = null;
                    output.setText("");
                    output.append("5");
                }
            }
        });

        button6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("6");

                else
                {   total = null;
                    output.setText("");
                    output.append("6");
                }
            }
        });

        button7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("7");

                else
                {   total = null;
                    output.setText("");
                    output.append("7");
                }
            }
        });

        button8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("8");

                else
                {   total = null;
                    output.setText("");
                    output.append("8");
                }
            }
        });

        button9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(total == null)
                    output.append("9");

                else
                {   total = null;
                    output.setText("");
                    output.append("9");
                }
            }
        });

        clearbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);
                output.setText("0");

            }
        });


        equalbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);
                auxDouble2 = Double.parseDouble(output.getText().toString());

                if( sign == "+" )
                {
                    total = Double.toString(auxDouble1 + auxDouble2);
                    output.setText(total);

                }

                if( sign == "-" )
                {
                    total = Double.toString(auxDouble1 - auxDouble2);
                    output.setText(total);                }

                if( sign == "*" )
                {
                    total = Double.toString(auxDouble1 * auxDouble2);
                    output.setText(total);                }

                if( sign == "/" )
                {
                    if(auxDouble2 == 0)
                    {
                        //Can't divide by 0
                        output.setText("You cannot divide by Zero!");
                    }
                    else
                    {
                        total = Double.toString(auxDouble1 / auxDouble2);
                        output.setText(total);                    }
                }

                if( sign == "sqrt")
                {
                    total = Double.toString(Math.sqrt(auxDouble1));
                    output.setText(total);

                }

                sign = "";
            }
        });


        plusbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(sign == "-" || sign == "*" || sign == "/")
                {
                    sign = "+";
                }

                if( !(output.getText().toString().matches("")) ) {
                    auxDouble1 = Double.parseDouble(output.getText().toString());
                    output.setText("");
                    sign = "+";
                }

            }
        });


        minusbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(sign == "+" || sign == "*" || sign == "/" || sign =="sqrt")
                {
                    sign = "-";
                }

                if( !(output.getText().toString().matches("")) ) {
                    auxDouble1 = Double.parseDouble(output.getText().toString());
                    output.setText("");
                    sign = "-";
                }

            }
        });


        mulbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(sign == "+" || sign == "-" || sign == "/" || sign =="sqrt")
                {
                    sign = "*";
                }

                if( !(output.getText().toString().matches("")) ) {
                    auxDouble1 = Double.parseDouble(output.getText().toString());
                    output.setText("");
                    sign = "*";
                }

            }
        });


        divbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);

                if(sign == "-" || sign == "*" || sign == "+" || sign =="sqrt")
                {
                    sign = "/";
                }

                if( !(output.getText().toString().matches("")) ) {
                    auxDouble1 = Double.parseDouble(output.getText().toString());
                    output.setText("");
                    sign = "/";
                }

            }
        });

        sqrtbutton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                TextView output = (TextView) findViewById(R.id.editText);
                if( !(output.getText().toString().matches("")) ) {
                    auxDouble1 = Double.parseDouble(output.getText().toString());
                    sign = "sqrt";
                }
            }
        });

    }
}