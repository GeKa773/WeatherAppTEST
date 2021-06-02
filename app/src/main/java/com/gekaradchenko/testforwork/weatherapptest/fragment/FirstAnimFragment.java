package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gekaradchenko.testforwork.weatherapptest.R;


public class FirstAnimFragment extends Fragment {
    private ImageView imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8,
            imageView9, imageView10, imageView11, imageView12, imageView13;

    private Animation animation1, animation2, animation3;
    private ConstraintLayout firstFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_anim, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        imageView4 = view.findViewById(R.id.imageView4);
        imageView5 = view.findViewById(R.id.imageView5);
        imageView6 = view.findViewById(R.id.imageView6);
        imageView7 = view.findViewById(R.id.imageView7);
        imageView8 = view.findViewById(R.id.imageView8);
        imageView9 = view.findViewById(R.id.imageView9);
        imageView10 = view.findViewById(R.id.imageView10);
        imageView11 = view.findViewById(R.id.imageView11);
        imageView12 = view.findViewById(R.id.imageView12);
        imageView13 = view.findViewById(R.id.imageView13);
        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.anim1);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.anim2);
        animation3 = AnimationUtils.loadAnimation(getContext(), R.anim.anim3);
        imageView2.startAnimation(animation1);
        imageView5.startAnimation(animation1);
        imageView8.startAnimation(animation1);
        imageView10.startAnimation(animation1);
        imageView13.startAnimation(animation1);
        imageView3.startAnimation(animation2);
        imageView6.startAnimation(animation2);
        imageView12.startAnimation(animation2);
        imageView4.startAnimation(animation3);
        imageView7.startAnimation(animation3);
        imageView9.startAnimation(animation3);
        imageView11.startAnimation(animation3);
        firstFragment = view.findViewById(R.id.firstFragment);
        firstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_firstAnimFragment_to_secondTextFragment);
            }
        });
    }
}