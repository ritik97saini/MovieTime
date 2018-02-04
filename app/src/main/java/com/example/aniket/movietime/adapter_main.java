package com.example.aniket.movietime;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.*;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

public class adapter_main extends ArrayAdapter<moviedata>{

   moviedata[] data;

    public adapter_main(@NonNull Context context, @NonNull moviedata[] objects) {
        super(context,R.layout.adapter, objects);
        this.data=objects;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View adap=inflater.inflate(R.layout.adapter,parent,false);
        TextView title=(TextView)adap.findViewById(R.id.title);
        TextView rating=(TextView)adap.findViewById(R.id.rating);
        String posterp="https://image.tmdb.org/t/p/w500";
        posterp+=data[i].imgpath;
        URL url=MainActivity.createUrl(posterp);
        Bitmap bmp= null;
      /*  try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ImageView poster=(ImageView)adap.findViewById(R.id.poster);
        Glide.with(getContext()).load(url).into(poster);

        //poster.setImageBitmap(bmp);

if(data[i]!=null){
        title.setText(data[i].title);
        rating.setText((data[i].rating).toString());   }
        return adap;


    }
}
