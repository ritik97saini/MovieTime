package com.example.aniket.movietime;


import java.io.Serializable;

public class moviedata implements Serializable{
    public  String title;
    public  String overview;
    public  String releasedate;
    public   Double rating;
    public   String imgpath;
    public   int _id;
    public   boolean    video;

    public moviedata()
    {
        title="a";
        overview="a";
        releasedate="a";
        rating=1.0;
        imgpath="a";
        _id=0;
        video=false;
    }
}

