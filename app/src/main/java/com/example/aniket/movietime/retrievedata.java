package com.example.aniket.movietime;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;



public class retrievedata extends AsyncTaskLoader<URL> {
String TAG="ritik";
    URL mp_url;
    public retrievedata(Context context ,URL u) {

        super(context);
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
        try {

           data1 = tab.makeHttpRequest(mp_url);
            Log.i(TAG, "loadInBackground: "+data1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tab.readfromjson(data1);
        return null;
    }
}