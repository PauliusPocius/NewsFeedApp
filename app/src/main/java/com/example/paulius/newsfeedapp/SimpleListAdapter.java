package com.example.paulius.newsfeedapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paulius on 2017-03-07.
 */

public class SimpleListAdapter extends ArrayAdapter<NewFeed> {

    public SimpleListAdapter(Context context, ArrayList<NewFeed> feeds) {
        super(context,0, feeds);
    }

    public View getView(int position, View convertView,  ViewGroup parent) {

        View listview = convertView;
        if (listview == null) {
            listview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        NewFeed curentposition = getItem(position);

        String imagee = curentposition.getSection();
        ImageView photo = (ImageView)listview.findViewById(R.id.imageView);
        photo.setImageResource(getImage(imagee));

        TextView name = (TextView)listview.findViewById(R.id.name);
        String feedname = curentposition.getName();
        if (feedname.contains("|")){
            String [] part = feedname.split("\\|");
            String shorter = part[0];
            name.setText(shorter);
        }else {
            name.setText(feedname);
        }
        return listview;
    }
    private int getImage(String photo){
        String imagenumber;
        imagenumber = photo;
        int image;
        switch (imagenumber) {
            case "Politics":
                image = R.drawable.politics;
                break;
            case "Football":
                image = R.drawable.football;
                break;
            case "Music":
                image = R.drawable.music;
                break;
            case "Books":
                image = R.drawable.books;
                break;
            case "Opinion":
                image = R.drawable.opinion;
                break;
            case "Film":
                image = R.drawable.films;
                break;
            case "World news":
                image = R.drawable.worldnews;
                break;
            case "Television & radio":
                image = R.drawable.tvradio;
                break;
            case "Law":
                image = R.drawable.law;
                break;
            case "US news":
                image = R.drawable.usnews;
                break;
            case "Business":
                image = R.drawable.bussines;
                break;
            case "Technology":
                image = R.drawable.technology;
                break;
            case "Sport":
                image = R.drawable.sports;
                break;
            case "UK news":
                image = R.drawable.sports;
                break;
            default:
                image = R.drawable.news;
                break;
        }
        return image;

    }
}
