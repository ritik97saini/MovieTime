package com.example.aniket.movietime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class cast_adapter extends RecyclerView.Adapter<cast_adapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }


    castdata[] data;


public cast_adapter(castdata[] obj)
{
    data=obj;
    setHasStableIds(true);
}

    ImageView img;
    TextView name,cname;
Context context;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cast_adapter,parent,false);
        name = (TextView)view.findViewById(R.id.name);
        cname=(TextView)view.findViewById(R.id.cname);


        img = (ImageView)view.findViewById(R.id.pic);
        ViewHolder hold= new ViewHolder(view);
        context=parent.getContext();
        return hold;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nm=data[position].rname;
        name.setText(nm);
        cname.setText(data[position].character);
        String url =data[position].pic;
        Glide.with(context).load(url).into(img);
    }



    @Override
    public int getItemCount() {
        return data.length;
    }
}
