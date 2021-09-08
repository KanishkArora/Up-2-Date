package com.example.android.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.jetbrains.annotations.NotNull;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    LottieAnimationView lottieAnimationView;
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePageAdapter pageAdapter;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.splash_screen);
        imageView = findViewById(R.id.bg);
        textView = findViewById(R.id.app);
        lottieAnimationView = findViewById(R.id.ani);

        //method to find the liquidPager and make it responsive
        viewPager = findViewById(R.id.liquid);
        pageAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        //methods for the animations on the splash screen
        imageView.animate().translationZ(-2500).setDuration(1000).setStartDelay(3000);
        textView.animate().translationZ(-2500).setDuration(1000).setStartDelay(3000);
        lottieAnimationView.animate().translationZ(-2500).setDuration(1000).setStartDelay(3000);

        //These methods are used so that a fading effect can be given after the splash screen is gone
        //In drawable first we created a new resource file named anim
        //Then in anim we created a new animation resourse file named intent_animation
        //In this file we set attributes to make the opacity lesser so that a fading effect can be achieved
        animation = AnimationUtils.loadAnimation(this, R.anim.intent_animation);
        viewPager.startAnimation(animation);

    }

    //We created a class so that we can make the liquid animation slidable
    //And set intents using switch 
    private class ScreenSlidePageAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePageAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                OnboardingFragment_1 tab1 = new OnboardingFragment_1();
                return tab1;
                case 1:
                    OnboardingFragment_2 tab2 = new OnboardingFragment_2();
                    return tab2;
                case 2:
                    OnboardingFragment_3 tab3 = new OnboardingFragment_3();
                    return tab3;
            }
            return null;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}