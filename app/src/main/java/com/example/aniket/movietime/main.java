package com.example.aniket.movietime;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.media.AsyncPlayer;


public class main extends AppCompatActivity implements tab.OnFragmentInteractionListener,upcoming_tab.OnFragmentInteractionListener,top_rated_tab.OnFragmentInteractionListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle abdt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        tabs.addTab(tabs.newTab().setText("Popular"));
        tabs.addTab(tabs.newTab().setText("Top Rated"));
        tabs.addTab(tabs.newTab().setText("Latest"));
        tabs.setTabGravity(TableLayout.TEXT_ALIGNMENT_GRAVITY);

        final ViewPager pager= (ViewPager)findViewById(R.id.viewpager);
        pageradapter adapter=new pageradapter( getSupportFragmentManager()) ;
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabs)
        );

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        drawer=(DrawerLayout)findViewById(R.id.drawer);
        abdt=new ActionBarDrawerToggle(this, drawer,R.string.open,R.string.close);
        abdt.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(abdt);
        abdt.syncState();



        NavigationView navbar= (NavigationView)findViewById(R.id.navbar);
        navbar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();

                return false;
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        abdt.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        abdt.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void search(View view)
    {
        Intent movie_intent = new Intent(view.getContext(), com.example.aniket.movietime.search_intent.class);

        startActivity(movie_intent);
    }
}
