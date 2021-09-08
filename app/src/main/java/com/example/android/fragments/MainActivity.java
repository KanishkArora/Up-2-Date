package com.example.android.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<Health>>, RecyclerAdapter.RecyclerViewClick {
private static final String LOG_TAG = MainActivity.class.getName();
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private LottieAnimationView mEmptyStateTextView;
    Drawable toolbarShape;
    Drawable toolbarShape2;
    Drawable navShape;
    Drawable navShape2;
    Drawable header;
    Drawable header2;
    Dialog dialog;
    RatingBar ratingBar;
    TextView bt;
    ConstraintLayout constraintLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String REQUEST_URL="https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";
    ListAdapter mAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.list_item);
        mAdapter = new ListAdapter(this, new ArrayList<Health>());

        listView.setAdapter(mAdapter);

        Log.e(LOG_TAG,"Loader manager INIT called");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Health health = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri uri = Uri.parse(health.Geturl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

        });

        mEmptyStateTextView = (LottieAnimationView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            mRecyclerView = findViewById(R.id.recycle);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            mExampleList = new ArrayList<>();
            mRequestQueue = Volley.newRequestQueue(this);
            parseJSON();
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setAnimation(R.raw.nointernet_astro);
        }


        //Created instance of BottomNavigationView
        //Used findByViewId to find the view in the layout
        //Set a clickListener to made it responsive so that it can open respective fragments
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.category:
                        //Used intent to open up to category fragment
                        Intent i = new Intent(getApplicationContext(),category.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.menu_home:


                }

                return false;
            }
        });

        //Using the DrawerLayout object found its view in the layout with help of findViewById() method
        //Using the NavigationView object found its view in the layout with help of findViewById() method
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView= findViewById(R.id.navigation);
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.rate_it);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ratingBar = findViewById(R.id.ratingBar);
        bt = dialog.findViewById(R.id.btSub);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        toolbarShape = getResources().getDrawable(R.drawable.toolbar_shape);
        toolbarShape2 = getResources().getDrawable(R.drawable.toolbar_shape2);
        navShape = getResources().getDrawable(R.drawable.selector);
        navShape2 = getResources().getDrawable(R.drawable.selector_two);
        header = getResources().getDrawable(R.drawable.header_shape);
        header2 = getResources().getDrawable(R.drawable.header_shape_two);
        constraintLayout = findViewById(R.id.recyclerDesign);


       // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        //Using the Toolbar object found the custom toolbar we created in the layout with help of findViewById() method
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //To made navigation drawer we set a toggle on it so that it can be closed or opened as wished
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        int colorInt = getResources().getColor(R.color.filthyWhite);
        ColorStateList csl = ColorStateList.valueOf(colorInt);


        //Theme toggle if case...setting the dark theme in it.
        if (loadState()){
            drawerLayout.setBackgroundColor(getResources().getColor(R.color.light_bg));
            toolbar.setBackground(toolbarShape2);
            bottomNavigationView.setBackground(navShape2);
            navigationView.setBackground(header2);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.light_bg));
            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.light_bg));
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.selector_two));
            navigationView.setItemTextColor(csl);
            navigationView.setItemIconTintList(csl);

           //setting light theme....
        }else{
            drawerLayout.setBackgroundColor(getResources().getColor(R.color.filthyWhite));
            toolbar.setBackground(toolbarShape);
            bottomNavigationView.setBackground(navShape);
            navigationView.setBackground(header);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.filthyWhite));
            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.filthyWhite));
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.selector));
        }

    }

    int counter = 0;

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1) {
            Toast.makeText(MainActivity.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        } else if (counter == 2) {

            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

        }
    }

    //We override this method so that items in the drawer can be clicked and can be intented to respective activities
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                Toast.makeText(this,"DEVELOPER",Toast.LENGTH_LONG).show();
                break;

            case R.id.feedback:
                dialog.show();
            break;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Download This app";
                String sub = body + "\n https://play.google.com";
                intent.putExtra(Intent.EXTRA_TEXT, body );
                intent.putExtra(Intent.EXTRA_TEXT, sub );
                startActivity(Intent.createChooser(intent,"Share using..."));
                break;

            case R.id.theme:
                item.setActionView(R.layout.theme_switch);
                final SwitchMaterial themeSwitch = (SwitchMaterial) item.getActionView().findViewById(R.id.actionSwitch);
                if(loadState()){
                    themeSwitch.setChecked(true);
                }
                themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            saveState(true);
                            recreate();
                        }else{
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            saveState(false);
                        }
                    }
                });
                break;
        }
        return true;
    }

    public void saveState(Boolean state){
        SharedPreferences sharedPreferences = getSharedPreferences("Up-2-Date", MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    private Boolean loadState(){
        SharedPreferences sharedPreferences = getSharedPreferences("Up-2-Date", MODE_PRIVATE );
        Boolean state = sharedPreferences.getBoolean("NightMode",false);
        return state;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this,"Search Ended",Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void parseJSON() {
        String url = "https://saurav.tech/NewsAPI/everything/cnn.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String creatorName = hit.getString("author");
                                String imageUrl = hit.getString("urlToImage");
                                String likeCount = hit.getString("title");
                                String murl=hit.getString("url");
                                if(creatorName.equals("null")){
                                    creatorName= "Unknown";
                                }
                                mExampleList.add(new ExampleItem(imageUrl, creatorName, likeCount,murl));
                            }
                            mExampleAdapter = new RecyclerAdapter(MainActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);

    }

    @Override
    public Loader<List<Health>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Health>> loader, List<Health> earthquakes) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Health>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
    @Override
    public void onClick1(int weatherForDay) {
        ExampleItem getitem= mExampleList.get(weatherForDay);

        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri uri = Uri.parse(getitem.getUrl());

        // Create a new intent to view the earthquake URI
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);

        // Send the intent to launch a new activity
        startActivity(websiteIntent);
    }



}