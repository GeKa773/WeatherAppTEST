package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.R;

import java.util.Locale;


public class LanguageSettingFragment extends Fragment implements View.OnClickListener {
    private TextView englishTextView, russianTextView;
    private SharedPreferences.Editor editor;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        englishTextView = view.findViewById(R.id.englishLanguageTextView);
        russianTextView = view.findViewById(R.id.russianLanguageTextView);

        editor = this.getActivity().getSharedPreferences(getString(R.string.shared_language), Context.MODE_PRIVATE).edit();


        navController = Navigation.findNavController(view);
        englishTextView.setOnClickListener(this);
        russianTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.englishLanguageTextView: {
                editor.putString(getString(R.string.shared_language), "en");
                editor.apply();
                break;
            }
            case R.id.russianLanguageTextView: {
                editor.putString(getString(R.string.shared_language), "ru");
                editor.apply();
                break;
            }

        }
        loadLocal();
        navController.navigate(R.id.action_languageSettingFragment_to_settingFragment);
    }

    private void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
    }

    private void loadLocal() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.shared_language), Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(getString(R.string.shared_language), "en");
        setLocate(language);
    }
}