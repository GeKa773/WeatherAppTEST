package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.Unit;

import java.util.Locale;


public class SettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private Switch nightModeSwitch;
    private Switch showAlertsSwitch, veryHotSwitch, snowSwitch, hotSwitch;
    private TextView showAlertsTextView, veryHotTextView, snowTextView, hotTextView;
    private TextView languageTextView;

    private Button tempButton, humidButton, windButton, dateButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences nightSharedPreferences;
    private SharedPreferences.Editor nightEditor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // loadLocal();
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        switchCheck();
        NavController navController = Navigation.findNavController(view);

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nightEditor.putBoolean(getString(R.string.shared_night_mode), isChecked);
                nightEditor.apply();
                // записать в shared bool и в низ isNightMode
                Unit.isNightMode(nightSharedPreferences.getBoolean(getString(R.string.shared_night_mode), false));
            }
        });

        showAlertsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchCheck();
            }
        });

        languageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_settingFragment_to_languageSettingFragment);
            }
        });


    }

//    private void isNightMode(boolean isChecked) {
//        if (isChecked) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }

    private void switchCheck() {
        if (showAlertsSwitch.isChecked()) {
            veryHotSwitch.setVisibility(View.VISIBLE);
            snowSwitch.setVisibility(View.VISIBLE);
            hotSwitch.setVisibility(View.VISIBLE);

            veryHotTextView.setVisibility(View.VISIBLE);
            snowTextView.setVisibility(View.VISIBLE);
            hotTextView.setVisibility(View.VISIBLE);
        } else {

            veryHotSwitch.setChecked(false);
            snowSwitch.setChecked(false);
            hotSwitch.setChecked(false);

            veryHotSwitch.setVisibility(View.GONE);
            snowSwitch.setVisibility(View.GONE);
            hotSwitch.setVisibility(View.GONE);

            veryHotTextView.setVisibility(View.GONE);
            snowTextView.setVisibility(View.GONE);
            hotTextView.setVisibility(View.GONE);

        }
    }


    private void initView(View view) {
        nightModeSwitch = view.findViewById(R.id.nightModeSettingSwitch);


        showAlertsSwitch = view.findViewById(R.id.settingSwitch1);
        veryHotSwitch = view.findViewById(R.id.settingSwitch2);
        snowSwitch = view.findViewById(R.id.settingSwitch3);
        hotSwitch = view.findViewById(R.id.settingSwitch4);

        showAlertsTextView = view.findViewById(R.id.settingFoSwitchTextView1);
        veryHotTextView = view.findViewById(R.id.settingFoSwitchTextView2);
        snowTextView = view.findViewById(R.id.settingFoSwitchTextView3);
        hotTextView = view.findViewById(R.id.settingFoSwitchTextView4);

        languageTextView = view.findViewById(R.id.languageSettingTextView);

        tempButton = view.findViewById(R.id.tempSettingButton);
        humidButton = view.findViewById(R.id.humidSettingButton);
        windButton = view.findViewById(R.id.windSettingButton);
        dateButton = view.findViewById(R.id.dateSettingButton);

        tempButton.setEnabled(false);
        humidButton.setEnabled(false);
        windButton.setEnabled(false);
        dateButton.setEnabled(false);

        veryHotSwitch.setOnCheckedChangeListener(this);
        snowSwitch.setOnCheckedChangeListener(this);
        hotSwitch.setOnCheckedChangeListener(this);

        nightSharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.shared_night), Context.MODE_PRIVATE);
        nightEditor = this.getActivity().getSharedPreferences(getString(R.string.shared_night), Context.MODE_PRIVATE).edit();

        nightModeSwitch.setChecked(nightSharedPreferences.getBoolean(getString(R.string.shared_night_mode), false));
        Unit.isNightMode(nightSharedPreferences.getBoolean(getString(R.string.shared_night_mode), false));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buttonEnable();
    }

    private void buttonEnable() {
        if (veryHotSwitch.isChecked()) {
            tempButton.setEnabled(true);
        } else tempButton.setEnabled(false);

        if (snowSwitch.isChecked()) {
            humidButton.setEnabled(true);
        } else humidButton.setEnabled(false);

        if (hotSwitch.isChecked()) {
            windButton.setEnabled(true);
            dateButton.setEnabled(true);
        } else {
            windButton.setEnabled(false);
            dateButton.setEnabled(false);
        }
    }

//    private void setLocate(String language) {
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration configuration = new Configuration();
//        configuration.locale = locale;
//        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
//    }
//
//    private void loadLocal() {
//        sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.language_text_view), Context.MODE_PRIVATE);
//        String language = sharedPreferences.getString(getString(R.string.shared_language), "en");
//        setLocate(language);
//    }
}