package com.example.aniket.movietime;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class movie_intent extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_intent);

        moviedata sourcedata= (moviedata) getIntent().getSerializableExtra("data");

        TextView title=(TextView)findViewById(R.id.title);
        TextView overview=(TextView)findViewById(R.id.overview);
        TextView rating=(TextView)findViewById(R.id.rating);
        ImageView poster=(ImageView)findViewById(R.id.poster);

        String url=sourcedata.imgpath;
        url="https://image.tmdb.org/t/p/w500/"+url;

        title.setText(sourcedata.title);
        overview.setText(sourcedata.overview);
        rating.setText("  Rating :  "+sourcedata.rating.toString());
        Glide.with(this).load(url).into(poster);


    }
}
