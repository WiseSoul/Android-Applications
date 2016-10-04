package com.example.passmessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }


    //Call onSendMessage() when the button is clicked
    public void onSendMessage(View view)
    {
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString();
       // Intent intent = new Intent(this,ReceiveMessageActivity.class);
      //  intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE,messageText);

        //ACTION_SEND -> send a message action
        Intent intent = new Intent(Intent.ACTION_SEND);
        //setType("text/plain") -> the type of text being sent
        intent.setType("text/plain");
        //putExtra(Intent.EXTRA_TEXT, messageText) -> carries the messageText string value with the intent and puts it in the app's EditText.
        intent.putExtra(Intent.EXTRA_TEXT, messageText);

        // creates a string with the value of the string id("chooser"),detailed in the strings.xml
        String chooserTitle = getString(R.string.chooser);

        //Intent.createChooser(intent, chooserTitle) -> creates a intent chooser,based on the original intent,with the title of the chooserTitle string value.
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);

        //starts the new activity based on the chosenIntent.
        startActivity(chosenIntent);

    }
}
