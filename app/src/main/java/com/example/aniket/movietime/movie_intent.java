package com.example.aniket.movietime;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class movie_intent extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,LoaderManager.LoaderCallbacks<URL> {


    YouTubePlayerView trailer;

    URL mp_url;
    String most_popular;
    String id,yapi;
   static String key;
   String img="https://image.tmdb.org/t/p/w500";
   boolean  vid= true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_intent);

        moviedata sourcedata= (moviedata) getIntent().getSerializableExtra("data");

        TextView title=(TextView)findViewById(R.id.title);
        TextView overview=(TextView)findViewById(R.id.overview);
        TextView rating=(TextView)findViewById(R.id.rating);
        ImageView poster=(ImageView)findViewById(R.id.poster);
        id=Integer.toString(sourcedata._id);



        title.setText(sourcedata.title);
        overview.setText(sourcedata.overview);
        rating.setText("  Rating :  "+sourcedata.rating.toString());
         yapi="AIzaSyCz1KGSRQAHbByi5rTQAm27VJ-Sf6TbeY4";
        trailer=(YouTubePlayerView) findViewById(R.id.trailer);
        if(sourcedata.video)
        Log.i("ritik", "onCreate: video ");

            task();





    }


    public  void task()
    {
        most_popular="https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=1a7081ac1a8acf21ddff343f5485bab2";
        mp_url=createUrl(most_popular);
        String TAG="ritik";
        Log.i(TAG, "onCreate: "+mp_url);
        Random random =new Random();
        LoaderManager loaderManager =getLoaderManager();
        loaderManager.initLoader(random.nextInt(100),null,this);
    }

    public  void task1()
    {
        most_popular="https://api.themoviedb.org/3/movie/"+id+"/credits?api_key=1a7081ac1a8acf21ddff343f5485bab2";
        mp_url=createUrl(most_popular);
        String TAG="ritik";
        Log.i(TAG, "onCreate: "+mp_url);
        Random random =new Random();
        LoaderManager loaderManager =getLoaderManager();
        loaderManager.initLoader(random.nextInt(100),null,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
    if(!b)
    {
        youTubePlayer.cueVideo(key);
    }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider , YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
 retrievedata load;
    @Override
    public Loader<URL> onCreateLoader(int i, Bundle bundle) {
        if(vid){
         load= new retrievedata(this,mp_url,3);}
        else {
            load= new retrievedata(this,mp_url,4);
        }
        return load;
    }

    @Override
    public void onLoadFinished(Loader<URL> loader, URL url) {
        if(vid){
        trailer.initialize(yapi,this);
        vid=false;
        task1();
        }
        else
        {
            Log.i("ritik", "onLoadFinished: printing data");
            showcast();
        }

    }


    @Override
    public void onLoaderReset(Loader<URL> loader) {

    }
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String makeHttpRequestcast(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("ritik", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static void readfromjson(String json)
    {


        try {
            JSONObject base=new JSONObject(json);
            JSONArray movies = base.getJSONArray("results");
            if(movies.length()>0)
            {
                JSONObject movie =movies.getJSONObject(0);
                key= movie.getString("key");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

   static castdata[] cast;


    public static void readfromjsoncast(String json)
    {


        try {
            JSONObject base=new JSONObject(json);
            JSONArray movies = base.getJSONArray("cast");
            if(movies.length()>0)
            {
                cast = new castdata[movies.length()];
                for(int i=0;i<movies.length();i++) {
                JSONObject movie = movies.getJSONObject(i);

                castdata temp =new castdata();
                temp._id=movie.getInt("id");
                temp.pic=movie.getString("profile_path");
                temp.character=movie.getString("character");
                temp.rname=movie.getString("name");
                cast[i]=temp;
            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    RecyclerView castview;
    RecyclerView.LayoutManager lman;
public void showcast()
{
    castview=(RecyclerView) findViewById(R.id.cast);
    castview.setHasFixedSize(true);
    lman=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
    castview.setLayoutManager(lman);
    cast_adapter adap=new cast_adapter(cast);
    castview.setAdapter(adap);


}

}
