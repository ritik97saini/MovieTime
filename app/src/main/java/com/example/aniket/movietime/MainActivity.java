package com.example.aniket.movietime;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<URL>{

    URL mp_url;
int page=1;
int k=0;
String page1=Integer.toString(page);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task();



        Button prev=(Button)findViewById(R.id.prev);
        prev.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(page>1){
                            page--;
                            page1=Integer.toString(page);
                        task();}
                    }
                }
        );


        Button forward=(Button)findViewById(R.id.forward);
        forward.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(page<800){
                            page++;
                            page1=Integer.toString(page);
                        task();}
                    }
                }
        );


    }
    String data1;

    public  void task()
    {
        String most_popular="https://api.themoviedb.org/3/movie/top_rated?api_key=1a7081ac1a8acf21ddff343f5485bab2&language=en-US&page="+page1;
        mp_url=createUrl(most_popular);
        String TAG="ritik";
        Log.i(TAG, "onCreate: "+mp_url);

        LoaderManager loaderManager =getLoaderManager();
        loaderManager.initLoader(k++,null,this);
    }

    @Override
    public Loader<URL> onCreateLoader(int i, Bundle bundle) {
        retrievedata load= new retrievedata(this,mp_url,0);
        return load;
    }

    @Override
    public void onLoadFinished(Loader<URL> loader, URL url) {
        printdata();
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

   static moviedata[] data=new moviedata[20];
    public static void readfromjson(String json)
    {


        try {
            JSONObject base=new JSONObject(json);
           JSONArray movies = base.getJSONArray("results");
            if(movies.length()>0)
            {
                for(int i=0;i<movies.length();i++)
                {
                    JSONObject movie=movies.getJSONObject(i);
                    int id=movie.getInt("id");


                    String title=movie.getString("title");
                    String poster_path=movie.getString("poster_path");
                    String overview=movie.getString("overview");
                    Double vote_average=movie.getDouble("vote_average");
                    String release_date=movie.getString("release_date");
                 moviedata obj =new moviedata();
                 obj.releasedate=release_date;
                 obj.rating=vote_average;
                 obj.overview=overview;
                 obj.imgpath=poster_path;
                 obj.title=title;
                 obj._id=id;
                 data[i]=obj;


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    GridView view;


    private void printdata()
    {
        view = (GridView) findViewById(R.id.mainview);
        ListAdapter adapter=new adapter_main(this,data);
        view.setAdapter(adapter);
        view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent movie_intent=new Intent(view.getContext(), com.example.aniket.movietime.movie_intent.class);
                        movie_intent.putExtra("data",  data[i]);
                        startActivity(movie_intent);
                    }
                }
        );

    }




}
