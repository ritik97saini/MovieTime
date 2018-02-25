package com.example.aniket.movietime;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.Random;

public class search_intent extends AppCompatActivity implements LoaderManager.LoaderCallbacks<URL>{
    EditText search ;
    ListView list;
    URL mp_url;
    int page=1;
    int totalpages=1;
    int k=0;
    String page1=Integer.toString(page);
    String pageq="popular";
    String query ="fast";
    String most_popular="https://api.themoviedb.org/3/search/movie?api_key=1a7081ac1a8acf21ddff343f5485bab2&language=en-US&query="+query+"&include_adult=true&page="+page1;
    ProgressBar load;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent);
        search =(EditText)findViewById(R.id.search);
        list=(ListView)findViewById(R.id.movies);

        search.addTextChangedListener(textchange);

        search.setOnEditorActionListener(
                new EditText.OnEditorActionListener(){
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_DONE)
                        {
                            search.performClick();
                            query=search.getText().toString();

                            task();


                            InputMethodManager imm =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);


                            return true;

                        }return false;
                    }
                }

        );

    }

    private TextWatcher textchange =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            query=  s.toString();

            task();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    public  void task()
    {
        most_popular="https://yts.am/api/v2/list_movies.json?query_term="+query;
        mp_url=createUrl(most_popular);
        String TAG="ritik";
        Log.i(TAG, "onCreate: "+mp_url);
        Random random =new Random();
        LoaderManager loaderManager =getLoaderManager();
        loaderManager.initLoader(random.nextInt(100),null,this);
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

    static moviedata[] data;
    public static void readfromjson(String json)
    {


        try {
            JSONObject base=new JSONObject(json);
            JSONObject data1=base.getJSONObject("data");
            JSONArray movies = data1.getJSONArray("movies");
            if(movies.length()>0)
            {

                int totalres=data1.getInt("movie_count");
                int no;
                if(totalres>20)
                    no=20;
                else
                    no=totalres;
                data=new moviedata[no];

                for(int i=0;i<no;i++)
                {
                    JSONObject movie=movies.getJSONObject(i);
                    int id=movie.getInt("id");

                    String title=movie.getString("title");
                    String poster_path=movie.getString("large_cover_image");
                    String overview=movie.getString("synopsis");
                    Integer release_date=movie.getInt("year");
                    Double vote_average=movie.getDouble("rating");
                    String vid=movie.getString("yt_trailer_code");
                    moviedata obj =new moviedata();
                    obj.releasedate=release_date;
                    obj.rating=vote_average;
                    obj.overview=overview;
                    obj.imgpath=poster_path;
                    obj.title=title;
                    obj._id=id;
                    obj.video=vid;
                    data[i]=obj;


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Loader<URL> onCreateLoader(int id, Bundle args) {
        retrievedata load= new retrievedata(this,mp_url,5);
        return load;
    }



    @Override
    public void onLoadFinished(Loader<URL> loader, URL data) {

        printdata();
    }

    @Override
    public void onLoaderReset(Loader<URL> loader) {

    }



    void printdata() {
        if (data != null) {
            search_adapter adapter = new search_adapter(this, data);
            list.setAdapter(adapter);
            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent movie_intent = new Intent(view.getContext(), com.example.aniket.movietime.movie_intent.class);
                            movie_intent.putExtra("data", data[position]);
                            startActivity(movie_intent);
                        }
                    }
            );
        }
    }
}
