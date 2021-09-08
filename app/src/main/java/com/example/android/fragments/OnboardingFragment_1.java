package com.example.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

public class OnboardingFragment_1 extends Fragment {
    LottieAnimationView lottieAnimationView;
    TextView textView;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_onboarding_1, container, false );
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //Found skip text and made it clickable using onClickListener
        //Then in overrided onClick method set intent so that it can directly jump to the MainActivity
        textView = root.findViewById(R.id.skip);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Used so that it can delay starting time of animation in onBoarding screen just by few milisecond
        lottieAnimationView = root.findViewById(R.id.lottieAnywhere);
        lottieAnimationView.animate().setDuration(0);
        return root;
    }

}
