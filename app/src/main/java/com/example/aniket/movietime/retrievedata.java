package com.example.aniket.movietime;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;



public class retrievedata extends AsyncTaskLoader<URL> {
String TAG="ritik";
    URL mp_url;
    int pos;
    public retrievedata(Context context ,URL u,int i ) {

        super(context);
        pos=i;
        mp_url=u;
        Log.i(TAG, "retrievedata: "+mp_url);
    }
String data1;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public URL loadInBackground() {
        if(pos==0){
        try {

           data1 = tab.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tab.readfromjson(data1);
        return null;
    }
    if(pos==1)
    {
        try {

            data1 = top_rated_tab.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        top_rated_tab.readfromjson(data1);
        return null;
    }
    if(pos==2)
    {
        try {

            data1 = upcoming_tab.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        upcoming_tab.readfromjson(data1);
        return null;
    }
    if(pos==3)
    {
        try {

            data1 = movie_intent.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        movie_intent.readfromjson(data1);
        return null;
    }
    if(pos==4)
    {
        try {

            data1 = movie_intent.makeHttpRequestcast(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        movie_intent.readfromjsoncast(data1);
        return null;
    }
    else
    {
        try {

            data1 = search_intent.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        search_intent.readfromjson(data1);
        return null;
    }

    }

    }
