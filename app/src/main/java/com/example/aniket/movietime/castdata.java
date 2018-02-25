package com.example.aniket.movietime;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;



public class castdata  implements Serializable {
    public  String character ;
    public  String rname ;
    public  String pic ;
    public  String _id;

    public castdata() {

        pic ="http://www.stablehands.org/wp-content/uploads/2014/03/blank-person-male.png";
    }



}
