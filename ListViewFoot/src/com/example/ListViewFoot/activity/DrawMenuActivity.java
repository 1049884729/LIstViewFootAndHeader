package com.example.ListViewFoot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.ListViewFoot.ParentActivity;
import com.example.ListViewFoot.R;
/**
 * Created by xff on 14-7-18.
 */
public class DrawMenuActivity extends ParentActivity {
     private DrawerLayout drawerLayout;
    private ListView mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] menuStrings={"menu1","menu2"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer= (ListView) findViewById(R.id.menulist);
        mDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuStrings));
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(mDrawer);
            }
        });
        // The drawer title must be set in order to announce state changes when
        // accessibility is turned on. This is typically a simple description,
        // e.g. "Navigation".
        drawerLayout.setDrawerTitle(GravityCompat.START, "drawmenu");

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerToggle.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerToggle.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }



        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();

    }
}