package com.example.bitsandpizzas;

import android.content.res.Configuration;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {


    // The String array with the titles that will appear when we open the drawer
    private String[] titles;
    // The drawer list is a ListView
    private ListView drawerList;
    // This is the drawer's layout
    private DrawerLayout drawerLayout;
    // This provides the option to share something
  //  private ShareActionProvider shareActionProvider;
    // This is the button used to open or close the Drawer
    private ActionBarDrawerToggle drawerToggle;
    // Set currentPosition to 0 by default
    private int currentPosition = 0;

    // The OnItemClickListener's onItemClick() method gets called when the user clicks on an item in the drawer's ListView
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //Call the selectItem() method when an item in the drawerListView is clicked
            selectItem(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Note: activity_main.xml uses android.support.v4.widget.DrawerLayout instead of a normal layout
        // Get the titles array from strings.xml
        titles = getResources().getStringArray(R.array.titles);
        // Initialise the ListView from activity_main.xml
        drawerList = (ListView) findViewById(R.id.drawer);
        // Initialise the drawerLayout from activity_main.xml
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Set the adapter with the titles array on the ListView
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        // Set the Listener on the ListView to listen for clicks on items
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //Display the correct fragment
        /* If the activity has been destroyed and recreated,set the value of currentPosition
           from the activity's previous state and use it to set the bar title */
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
           //If the activity's been destroyed and re-created,set the correct action bar title.
            setActionBarTitle(currentPosition);
        }
        else { // If the MainActivity is newly created,use the selectItem() method to display TopFragment
            selectItem(0);
        }

        // Create the ActionBarDrawerToggle to open or close the DrawerList
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            // Called when a drawer has settled in a completely closed state
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Call invalidateOptionsMenu when the drawer is closed because we want to change the action items displayed in the action bar
                invalidateOptionsMenu();
            }

            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // Call invalidateOptionsMenu when the drawer is open because we want to change the action items displayed in the action bar
                invalidateOptionsMenu();
            }
        };
        // Add the Drawer Listener on the drawerToggle to listen for clicks
        drawerLayout.addDrawerListener(drawerToggle);

        // Enable the Up icon so it can be used by the ActionBarDrawerToggle
         getActionBar().setDisplayHomeAsUpEnabled(true);
         getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
           new FragmentManager.OnBackStackChangedListener() {
               //This gets called when the back stack changes.
               public void onBackStackChanged() {
                   FragmentManager fragMan = getFragmentManager();
                   Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
               // Check which class the fragment currently attached to the activity is an instance of,and set currentPosition accordingly.
               // All fragments are tagged as "visible_fragment" but fragment's value will be the last fragment added to the backstack
                if (fragment instanceof TopFragment){
                    currentPosition = 0;
                }

                if (fragment instanceof  PizzaMaterialFragment) {
                    currentPosition = 1;
                }

                if(fragment instanceof  PastaFragment) {
                    currentPosition = 2;
                }

                if(fragment instanceof  StoresFragment) {
                    currentPosition = 3;
                }
                // Set the action bar title and highlight the correct item in the drawer ListView
                setActionBarTitle(currentPosition);
                drawerList.setItemChecked(currentPosition, true);
               }
           }
        );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        //Sync the state of the ActionBarDrawerToggle with the state of the drawer
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // Pass any configuration changes to the ActionBarDrawerToggle
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    //Called whenever we call invalidateOptionsmenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open,hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        // Display the Share action if the drawer is closed,hide it if the drawer is open
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(int position){
        // Update currentPosition when an item is selected
         currentPosition = position;

        // update the main content by replacing fragments in the FrameLayout
        Fragment fragment;
        // Decide which fragment to display based on the position of the item the user selects in the drawer's ListView
        switch (position)
        {
            case 1:
                fragment = new PizzaMaterialFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        // Display the fragment using a fragment transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment,"visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        // Set the action bar title
        setActionBarTitle(position);
        // Close drawer
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        // Save the state of the currentPosition if the activity's going to be destroyed.
        outState.putInt("position",currentPosition);
    }

    private void setActionBarTitle(int position) {
        String title;
        // If the user clicks on the "home" option,it will use the app name for the title
        if (position == 0){
            title = getResources().getString(R.string.app_name);
        } else { // Otherwise get the String from the tittles array for the position that was clicked and use that as the ActionBarTitle
            title = titles[position];
        }
        // Display the title in the action bar // Check to see what happens when not used
        getActionBar().setTitle(title);
    }

    // Add items in the menu resource file to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu;this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);

        /*// You can either set here the share button or in the onOptionsItemSelected() method but only with a shareActionProvider
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();


        */
        return super.onCreateOptionsMenu(menu);
    }

    // Pass the Share action an intent for it to share.
    // This sets the default text that the share action provider should share
    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        // Intent used to break the option to permanently use a specific app to send the intent's extra text
        // It lets the user to always choose what app to share the message with
        Intent chosenIntent = Intent.createChooser(intent,getString(R.string.intent_chooser));

       // shareActionProvider.setShareIntent(intent);
        startActivity(chosenIntent);
    }

    // This method is called when the user clicks on an item in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // If the ActionBarDrawerToggle is clicked,let it handle what happens
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

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
                String getMsg = "Evi e iubita mea.";
                setIntent(getMsg);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
