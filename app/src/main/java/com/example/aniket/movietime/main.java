package com.example.aniket.movietime;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;


public class main extends AppCompatActivity implements tab.OnFragmentInteractionListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_display);

        final TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        tabs.addTab(tabs.newTab().setText("upcoming"));
        tabs.addTab(tabs.newTab().setText("now_playing"));
        tabs.addTab(tabs.newTab().setText("popular"));
        tabs.addTab(tabs.newTab().setText("top_rated"));
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

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
