package com.example.toggleswitchapp;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void OnClickShowToast(View view)
    {
        CharSequence text = "Hello, I'm a Toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

    }

    public void OnSwitchClicked(View view)
    {
        // isChecked -> checks if the Switch has been pressed or not.
        boolean on = ((Switch) view).isChecked();

        if(on) {
        }
        else
        {
        }

    }

    public void OnToggleClicked(View view)
    {

        // isChecked -> checks if the Switch has been pressed or not.
        boolean on = ((ToggleButton) view).isChecked();

        if(on) {
        }
        else
        {
        }

    }


    public void OnCheckBoxClicked(View view)
    {
    // (CheckBox) view -> gets the checkbox from the view.
    // isChecked() = true -> if a checkbox is fulled(not empty) by being clicked
    // isChecked() = false -> if a checkbox is emptied by being clicked
    boolean checked = ((CheckBox) view).isChecked();

    //Retrieve which checkbox was clicked
     switch(view.getId()) {
         case R.id.checkbox_milk:
             if(checked)
             {
             }

             else
             {
             }
             break;

         case R.id.checkbox_sugar:
             if(checked)
             {
             }

             else
             {
             }
             break;

         case R.id.checkbox_egg:
             if(checked)
             {
             }

             else
             {
             }
             break;
     }
    }

    public void  onRadioButtonClicked(View view)
    {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int id = radioGroup.getCheckedRadioButtonId();

        switch (id) {
            case R.id.radio_cavemen:
           //
            break;

            case R.id.radio_astronauts:
           //
            break;
        }
    }


    /*
    // You can get the value of the currently selected item and transform it to a string like this:
    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    String string = String.valueOf(spinner.getSelectedItem());
    */
}
