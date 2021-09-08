package com.example.android.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntertainmentActivity extends AppCompatActivity implements LoaderCallbacks<List<Health>> {
Adapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String REQUEST_URL="https://saurav.tech/NewsAPI/top-headlines/category/entertainment/in.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

       // ArrayList<Health> a = QuerryUtils_entertainment.extractEarthquakes();
        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new Adapter(this, new ArrayList<Health>());

        listView.setAdapter(mAdapter);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current news that was clicked on
                Health health = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri uri = Uri.parse(health.Geturl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

        });

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public Loader<List<Health>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Health>> loader, List<Health> health) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (health != null && !health.isEmpty()) {
            mAdapter.addAll(health);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Health>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(EntertainmentActivity.this,"Search",Toast.LENGTH_SHORT);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(EntertainmentActivity.this,"Search Ended",Toast.LENGTH_SHORT);
                return true;
            }
        };
        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search...");
        return true;
    }


}