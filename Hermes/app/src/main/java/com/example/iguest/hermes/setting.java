package com.example.iguest.hermes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class setting extends AppCompatActivity {

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
