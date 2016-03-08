package com.example.iguest.hermes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    public static final String KEY_MAX_DISTANCE = "maxDistance";
    public static final String KEY_DISPLAY_NAME = "displayName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Loads the Preference Fragment
        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        settingFragment setFragment = new settingFragment();
        mFragmentTransaction.replace(android.R.id.content, setFragment);
        mFragmentTransaction.commit();
    }
}
