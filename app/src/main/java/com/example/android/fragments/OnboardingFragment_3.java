package com.example.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnboardingFragment_3 extends Fragment {

    FloatingActionButton floatingActionButton;
    LottieAnimationView lottieAnimationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_3, container, false );

        lottieAnimationView = root.findViewById(R.id.lottieWorld);
        lottieAnimationView.animate().setDuration(0);

        //A button is created so that after 3rd onBoarding screen we can go to the main screen
        floatingActionButton = root.findViewById(R.id.next_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
