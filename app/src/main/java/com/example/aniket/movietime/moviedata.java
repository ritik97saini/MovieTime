package com.example.aniket.movietime;


import java.io.Serializable;

public class moviedata implements Serializable{
    public  String title;
    public  String overview;
    public  Integer releasedate;
    public   Double rating;
    public   String imgpath;
    public   int _id;
    public   String    video;

    public moviedata()
    {
        title="a";
        overview="a";
        releasedate=1;
        rating=1.0;
        imgpath="http://clipartix.com/wp-content/uploads/2016/06/Movie-reel-film-reel-clipart.jpeg";
        _id=0;
        video="a";
    }
}

