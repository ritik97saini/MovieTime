package com.example.aniket.movietime;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class movie_intent extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    YouTubePlayerView trailer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_intent);

        moviedata sourcedata= (moviedata) getIntent().getSerializableExtra("data");

        TextView title=(TextView)findViewById(R.id.title);
        TextView overview=(TextView)findViewById(R.id.overview);
        TextView rating=(TextView)findViewById(R.id.rating);
        ImageView poster=(ImageView)findViewById(R.id.poster);


        String yapi="AIzaSyCz1KGSRQAHbByi5rTQAm27VJ-Sf6TbeY4";
         trailer=(YouTubePlayerView) findViewById(R.id.trailer);
        trailer.initialize(yapi,this);

        title.setText(sourcedata.title);
        overview.setText(sourcedata.overview);
        rating.setText("  Rating :  "+sourcedata.rating.toString());



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
    if(!b)
    {
        youTubePlayer.cueVideo("64-iSYVmMVY");
    }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
    }
}
