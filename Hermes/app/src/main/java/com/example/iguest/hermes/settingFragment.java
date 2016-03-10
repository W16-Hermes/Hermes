package com.example.iguest.hermes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Random;

/**
 * Created by bruceng on 2/3/16.
 */
public class settingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    private Random random = new Random();
    private String userid = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the settings from the pref.xml
        addPreferencesFromResource(R.xml.pref);
        onSharedPreferenceChanged(null, "");
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {
        // handle the preference change here
        if (key.equals("displayName")) {
            final String name = sharedPreferences.getString("displayName", " ");
            ParseQuery query = ParseQuery.getQuery("User");
            query.whereEqualTo("screenName", name);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        final ParseObject user = new ParseObject("User");
                        user.put("screenName", name);
                        user.put("score", 0);
                        String phone = " ";
                        for (int i = 0; i < 10; i++) {
                            phone = phone + random.nextInt(9);
                        }
                        user.put("phoneNumber", phone);
                        user.saveInBackground();
                    } else {
                        SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                        SharedPreferences.Editor prefEditor = options.edit();
                        prefEditor.putString("userId", object.getObjectId());
                        prefEditor.commit();
                    }
                    try {
                        ParseQuery query1 = ParseQuery.getQuery("User");
                        ParseObject editSaved = query1.getFirst();
                        SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                        SharedPreferences.Editor prefEditor = options.edit();
                        prefEditor.putString("userId", editSaved.getObjectId());
                        prefEditor.commit();
                    } catch (ParseException c) {
                        c.printStackTrace();
                    }
                }
            });
            final String display = sharedPreferences.getString("userId", "");
            Log.v("Name", "The First Check is " + display);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}