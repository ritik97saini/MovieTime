package com.example.aniket.movietime;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;


public class torrentadap extends RecyclerView.Adapter<torrentadap.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }


    torrdata[] data;


    public torrentadap(torrdata[] obj)
    {
        data=obj;
        setHasStableIds(true);
    }

   TextView quality,size;

Context context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.torrent,parent,false);
        quality = (TextView)view.findViewById(R.id.quality);
        size=(TextView)view.findViewById(R.id.size);

        ViewHolder hold= new ViewHolder(view);
        context=parent.getContext();
        return hold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        quality.setText(data[position].quality);
        size.setText(data[position].size);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String moviename=data[position].name;
                        String hash=data[position].hash;

                        String download="magnet:?xt=urn:btih:"+hash+"&dn="+moviename+"&tr=http://track.one:1234/announce&tr=udp://track.two:80&tr=udp://glotorrents.pw:6969/announce" +
                                "&tr=udp://glotorrents.pw:6969/announce" +
                                "&tr= udp://tracker.opentrackr.org:1337/announce" +
                                "&tr=udp://torrent.gresille.org:80/announce";
                        String no="https://yts.am/torrent/download/"+hash;

                        Intent down=new Intent();
                        down.setAction(Intent.ACTION_VIEW);

                       down.setData(Uri.parse(download));
                        context.startActivity(Intent.createChooser(down,"Torrent"));
                    }
                }
        );

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
    public int getItemCount() {
        return data.length;
    }
}
