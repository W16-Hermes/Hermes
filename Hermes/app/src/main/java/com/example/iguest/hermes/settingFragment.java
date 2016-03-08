package com.example.iguest.hermes;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by bruceng on 2/3/16.
 */
public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the settings from the pref.xml
        addPreferencesFromResource(R.xml.pref);
    }
}