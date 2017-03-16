package com.example.paulius.newsfeedapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Paulius on 2017-03-07.
 */

public class NewFeedLoader extends AsyncTaskLoader<List<NewFeed>>{
    public static final String LOG_TAG = NewFeedLoader.class.getName();

    private String mUrl;

    public NewFeedLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List loadInBackground() {


        if (mUrl == null) {
            return null;
        }

        List<NewFeed> newFeeds = QueryUtils.fetchnewfeeds(mUrl);
        return newFeeds;

    }
}
