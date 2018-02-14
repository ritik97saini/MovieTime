package com.example.aniket.movietime;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
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


public class upcoming_tab extends Fragment implements LoaderManager.LoaderCallbacks<URL> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    URL mp_url;
    int page=1;
    int k=0;
    String page1=Integer.toString(page);

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String pageq="popular";
    String most_popular="https://api.themoviedb.org/3/movie/"+pageq+"?api_key=1a7081ac1a8acf21ddff343f5485bab2&language=en-US&page="+page1;

    public upcoming_tab() {
        pageq="upcoming"; most_popular="https://api.themoviedb.org/3/movie/"+pageq+"?api_key=1a7081ac1a8acf21ddff343f5485bab2&language=en-US&page="+page1;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }






    }
    public  void task()
    {
        most_popular="https://api.themoviedb.org/3/movie/"+pageq+"?api_key=1a7081ac1a8acf21ddff343f5485bab2&language=en-US&page="+page1;
        mp_url=createUrl(most_popular);
        String TAG="ritik";
        Log.i(TAG, "onCreate: "+mp_url);
        Random random =new Random();
        LoaderManager loaderManager =getActivity().getLoaderManager();
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
                    Boolean vid=movie.getBoolean("video");
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

    GridView view1;


    private void printdata()
    {
        if(isAdded()) {
            Log.i("ritik", "printdata: printing ");
            ListAdapter adapter = new adapter_main(getContext(), data);
            view1.setAdapter(adapter);
            view1.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent movie_intent = new Intent(view.getContext(), com.example.aniket.movietime.movie_intent.class);
                            movie_intent.putExtra("data", data[i]);
                            startActivity(movie_intent);
                        }
                    }
            );
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.upcoming,container,false);

        Button prev=(Button)view.findViewById(R.id.prev);
        prev.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(page>1){
                            page--;
                            page1=Integer.toString(page);

                            task();

                        }
                    }
                }
        );

        view1 = (GridView) view.findViewById(R.id.mainview);

        Button forward=(Button)view.findViewById(R.id.forward);
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

        task();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<URL> onCreateLoader(int i, Bundle bundle) {
        retrievedata load= new retrievedata(getContext(),mp_url,2);
        return load;    }

    @Override
    public void onLoadFinished(Loader<URL> loader, URL url) {
        printdata();
    }

    @Override
    public void onLoaderReset(Loader<URL> loader) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
