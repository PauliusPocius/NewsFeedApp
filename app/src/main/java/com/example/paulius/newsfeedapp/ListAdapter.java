package com.example.paulius.newsfeedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Paulius on 2017-03-08.
 */

public class ListAdapter extends ArrayAdapter<NewFeed> {

    public ListAdapter(Context context, ArrayList<NewFeed> feeds) {
        super(context,0, feeds);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listview = convertView;
        if (listview == null) {
            listview = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        NewFeed curentposition = getItem(position);

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

}
