package com.example.paulius.newsfeedapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewFeed>> {

    private SimpleListAdapter adapter;
    private boolean isConnected;
    private ImageView errorpage;
    private ProgressBar progressBar;
    private TabLayout tabLayout;
    private static final int NEWS_FEED_ID = 1;
    private static final String URL = "http://content.guardianapis.com/search?q=all&order-by=newest&page-size=30&api-key=test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent sport = new Intent(this,SportsActivity.class);
        final Intent politics = new Intent(this,PoliticsAndDebatesActivity.class);
        final Intent movie = new Intent(this,FilmsAndMusicActivity.class);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Politics"));
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.addTab(tabLayout.newTab().setText("LifeStyle"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        startActivity(politics);
                        break;
                    case 2:
                        startActivity(sport);
                        break;
                    case 3:
                        startActivity(movie);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        ListView listview = (ListView)findViewById(R.id.list);
        errorpage = (ImageView)findViewById(R.id.error);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        adapter = new SimpleListAdapter(this,new ArrayList<NewFeed>());

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            NewFeed curentposition =adapter.getItem(position);

                Uri feedsuri = Uri.parse(curentposition.getUrl());

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, feedsuri);
                startActivity(browserIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWS_FEED_ID, null, this);
    }

    @Override
    public Loader<List<NewFeed>> onCreateLoader(int id, Bundle args) {
        return new NewFeedLoader(this, URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewFeed>> loader, List<NewFeed> data) {

        progressBar.setVisibility(View.GONE);

        adapter.clear();

        if (isConnected) {
            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                adapter.addAll(data);
            }
        }else {
            errorpage.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<NewFeed>> loader) {
        adapter.clear();
    }
}
