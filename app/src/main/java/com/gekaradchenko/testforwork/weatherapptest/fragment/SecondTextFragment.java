package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gekaradchenko.testforwork.weatherapptest.MainActivity;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.WeatherActivity;

public class SecondTextFragment extends Fragment {
    private ImageView imageView;
    private TextView titleTextView, textTextView, androidTeamTextView;
    private Button button;
    private MainActivity mainActivity;
    private int count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        count = 0;

        NavController navController = Navigation.findNavController(view);
        imageView = view.findViewById(R.id.secondFragmentImageView);
        titleTextView = view.findViewById(R.id.secondFragmentTitleTextView);
        textTextView = view.findViewById(R.id.secondFragmentTextTextView);
        androidTeamTextView = view.findViewById(R.id.secondAndroidTextView);
        androidTeamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                Button dialogPrivateButton = dialog.findViewById(R.id.dialogPrivateButton);
                Button dialogTermButton = dialog.findViewById(R.id.dialogTermButton);
                dialogPrivateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(), "Private policy", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                dialogTermButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Term of using", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        button = view.findViewById(R.id.secondFragmentButton);
        setView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ++count;
                setView();
                if (count > 2) {

                    startActivity(new Intent(getContext(), WeatherActivity.class));
                }
            }
        });
    }

    private void setView() {
        switch (count) {
            case 0: {
                imageView.setImageResource(R.drawable.clounds);

                textTextView.setText(getString(R.string.second_fragment_text_1));
                titleTextView.setText("Quick");
                button.setText(getString(R.string.next_tip));
                break;
            }
            case 1: {
                imageView.setImageResource(R.drawable.sun_and_clouds);

                androidTeamTextView.setVisibility(View.INVISIBLE);
                textTextView.setText(getString(R.string.second_fragment_text_2));
                titleTextView.setText("Stable");
                button.setText(getString(R.string.next_tip));
                break;
            }
            case 2: {
                imageView.setImageResource(R.drawable.sun);
                androidTeamTextView.setVisibility(View.VISIBLE);
                androidTeamTextView.setGravity(Gravity.RIGHT);

                textTextView.setText(getString(R.string.second_fragment_text_3));
                titleTextView.setText("Beautifull");
                button.setText(getString(R.string.go_to_the_app));
                break;
            }
        }
    }
}