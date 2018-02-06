package com.example.aniket.movietime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class pageradapter extends FragmentStatePagerAdapter {


    public pageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new tab(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}

