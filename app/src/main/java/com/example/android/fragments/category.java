package com.example.android.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class category extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    Drawable toolbarShape;
    Drawable toolbarShape2;
    Drawable navShape;
    Drawable navShape2;
    Drawable header;
    Drawable header2;
    FrameLayout card1;
    FrameLayout card2;
    FrameLayout card3;
    FrameLayout card4;
    FrameLayout card5;
    FrameLayout card6;
    Dialog dialog;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Created instance of BottomNavigationView
        //Used findByViewId to find the view in the layout
        //Set a clickListener to made it responsive so that it can open respective fragments
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.category);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menu_home:
                        //Used intent to open up to home fragment
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.category:


                }

                return false;
            }
        });
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView= findViewById(R.id.navigation);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        toolbarShape = getResources().getDrawable(R.drawable.toolbar_shape);
        toolbarShape2 = getResources().getDrawable(R.drawable.toolbar_shape2);
        navShape = getResources().getDrawable(R.drawable.selector);
        navShape2 = getResources().getDrawable(R.drawable.selector_two);
        header = getResources().getDrawable(R.drawable.header_shape);
        header2 = getResources().getDrawable(R.drawable.header_shape_two);


        //Using the DrawerLayout object found its view in the layout with help of findViewById() method
        //Using the NavigationView object found its view in the layout with help of findViewById() method
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView= findViewById(R.id.navigation);


        // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) category.this);

        //Using the Toolbar object found the custom toolbar we created in the layout with help of findViewById() method
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //To made navigation drawer we set a toggle on it so that it can be closed or opened as wished
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        dialog = new Dialog(category.this);
        dialog.setContentView(R.layout.rate_it);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        ratingBar = findViewById(R.id.ratingBar);

        int colorInt = getResources().getColor(R.color.filthyWhite);
        ColorStateList csl = ColorStateList.valueOf(colorInt);


        if (loadState()){
            drawerLayout.setBackgroundColor(getResources().getColor(R.color.light_bg));
            toolbar.setBackground(toolbarShape2);
            bottomNavigationView.setBackground(navShape2);
            navigationView.setBackground(header2);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.light_bg));
            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.light_bg));
            card1.setBackgroundColor(getResources().getColor(R.color.light_card));
            card2.setBackgroundColor(getResources().getColor(R.color.light_card));
            card3.setBackgroundColor(getResources().getColor(R.color.light_card));
            card4.setBackgroundColor(getResources().getColor(R.color.light_card));
            card5.setBackgroundColor(getResources().getColor(R.color.light_card));
            card6.setBackgroundColor(getResources().getColor(R.color.light_card));
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.selector_two));
            navigationView.setItemTextColor(csl);
            navigationView.setItemIconTintList(csl);

        }else{
            drawerLayout.setBackgroundColor(getResources().getColor(R.color.filthyWhite));
            toolbar.setBackground(toolbarShape);
            bottomNavigationView.setBackground(navShape);
            navigationView.setBackground(header);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.filthyWhite));
            getWindow().setNavigationBarColor(this.getResources().getColor(R.color.filthyWhite));
            card1.setBackgroundColor(getResources().getColor(R.color.snow));
            card2.setBackgroundColor(getResources().getColor(R.color.snow));
            card3.setBackgroundColor(getResources().getColor(R.color.snow));
            card4.setBackgroundColor(getResources().getColor(R.color.snow));
            card5.setBackgroundColor(getResources().getColor(R.color.snow));
            card6.setBackgroundColor(getResources().getColor(R.color.snow));
        }

        //Finding the card view and then putting onClick make it user responsive
        //In onClick method used intent to open up the respective activities
        CardView cardView = (CardView)findViewById(R.id.bussiness);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), BusinessActivity.class);
                startActivity(i2);
            }
        });
        CardView cardView1 = (CardView)findViewById(R.id.sports);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), SportsActivity.class);
                startActivity(i2);

            }
        });
        CardView cardView3 = (CardView)findViewById(R.id.technology);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), TechnologyActivity.class);
                startActivity(i2);
            }
        });
        CardView cardView4 = (CardView)findViewById(R.id.entertainment);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), EntertainmentActivity.class);
                startActivity(i2);
            }
        });
        CardView cardView5 = (CardView)findViewById(R.id.lifestyle);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), LifestyleActivity.class);
                startActivity(i2);
            }
        });
        CardView cardView6 = (CardView)findViewById(R.id.health);
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(getApplicationContext(), HealthActivity.class);
                startActivity(i2);
            }
        });



    }
    int counter = 0;

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1) {
            Toast.makeText(category.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        } else if (counter == 2) {
//            super.onBackPressed();
//            this.finishAffinity();
//            moveTaskToBack(true);
//            finish();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);

        }
        else{
            super.onBackPressed();
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
                String sub = "https://play.google.com";
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
                Toast.makeText(category.this,"Search",Toast.LENGTH_SHORT);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(category.this,"Search Ended",Toast.LENGTH_SHORT);
                return true;
            }
        };
        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search...");
        return true;
    }

}