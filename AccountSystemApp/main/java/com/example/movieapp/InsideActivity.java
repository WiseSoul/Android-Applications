package com.example.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InsideActivity extends Activity {

    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    // The OnItemClickListener's onItemClick() method gets called when the user clicks on aan item in the drawer's ListView
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        titles = getResources().getStringArray(R.array.options_list);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Add the Drawer Listener on the drawerToggle to listen for clicks
        drawerLayout.addDrawerListener(drawerToggle);
        // Enable the Up icon so it can be used by the ActionBarDrawerToggle
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        //Sync the state of the ActionBarDrawerToggle with the state of the drawer
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        // Pass any configuration changes to the ActionBarDrawerToggle
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position){
        // Update currentPosition when an item is selected
        currentPosition = position;

        //Log out option
        if (titles[position].equals("Log Out")) {
            LoginSystemDatabaseHelper helper = new LoginSystemDatabaseHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("delete from "+ "SIGNED_IN");
            db.close();
            LoginActivity.fromActivity = true;
            Intent intent = new Intent(InsideActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(drawerList);
    }

    // Add items in the menu resource file to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    // This method is called when the user clicks on an item in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
