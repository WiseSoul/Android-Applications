package com.example.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PizzaDetailActivity extends Activity {

    public final static String EXTRA_POSITION = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        Intent intent = getIntent();
        int position = (int) intent.getExtras().get(EXTRA_POSITION);
        ImageView imageView = (ImageView) findViewById(R.id.pizza_image);
        imageView.setImageResource(Pizza.pizzas[position].getImageResourceId());
        TextView textView = (TextView) findViewById(R.id.pizza_description);
        textView.setText(Pizza.pizzas[position].getName());

        // Enable the Up icon so it can be used by the ActionBarDrawerToggle
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);

        Intent chosenIntent = Intent.createChooser(intent,getString(R.string.intent_chooser));
        startActivity(chosenIntent);
    }

    // This method is called when the user clicks on an item in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_create_order:
                // Code to run when the Create Order item is clicked
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                // Code to run when the settings item is clicked
                return true;
            case R.id.action_share:
                // Code to run when the share button is clicked
                // shareActionProvider = (ShareActionProvider) item.getActionProvider();
                Intent theIntent = getIntent();
                int position = (int) theIntent.getExtras().get(EXTRA_POSITION);
                String getMsg = "I like the " + Pizza.pizzas[position].getName() + " pizza a lot!";
                setIntent(getMsg);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
