package com.example.paulius.newsfeedapp;

/**
 * Created by Paulius on 2017-03-07.
 */

public class NewFeed {
    private String mSection;
    private String mName;
    private String mUrl;


    public NewFeed(String section, String name,String url) {
        mSection = section;
        mName = name;
        mUrl = url;

    }

    public String getSection(){
        return mSection;
    }
    public String getName(){
        return mName;
    }
    public String getUrl(){
        return mUrl;
    }

}