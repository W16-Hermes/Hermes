package com.example.iguest.hermes;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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
    private static final String TAG = "Setting_Fragment";

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
            final ParseQuery query = ParseQuery.getQuery("User");
            query.whereEqualTo("screenName", name);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(final ParseObject object, ParseException e) {
                    if (object == null) {
                        final ParseObject user = new ParseObject("User");
                        user.put("screenName", name);
                        user.put("score", 0);
                        String phone = " ";
                        for (int i = 0; i < 10; i++) {
                            phone = phone + random.nextInt(9);
                        }
                        user.put("phoneNumber", phone);
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                SharedPreferences.Editor prefEditor = options.edit();
                                prefEditor.putString("userId", user.getObjectId());
                                prefEditor.commit();
                            }
                        });
                    } else {
                        SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                        SharedPreferences.Editor prefEditor = options.edit();
                        prefEditor.putString("userId", object.getObjectId());
                        prefEditor.commit();
                    }
                }
            });
            Log.v(TAG, "UserID is: " + sharedPreferences.getString("userId", "asdasd"));
            Log.v(TAG, "ScreenName is: " + sharedPreferences.getString("displayName", "asdasd"));
            Toast.makeText(getActivity(), "Display Name Changed", Toast.LENGTH_LONG).show();
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