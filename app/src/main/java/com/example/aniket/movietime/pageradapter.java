package com.example.aniket.movietime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class pageradapter extends FragmentStatePagerAdapter {


    public pageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position==0)
        return new tab();
        if(position==1)
            return new top_rated_tab();
        else return new upcoming_tab();

    }

    @Override
    public int getCount() {
        return 3;
    }
}

