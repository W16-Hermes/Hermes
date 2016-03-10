package com.example.iguest.hermes;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class parseDisplayName {

    public parseDisplayName() {

    }

    public String convertToId(String name) {
        String result = "PCdHFcFlD0";
        ParseQuery query = ParseQuery.getQuery("User");
        query.whereEqualTo("screenName", name);
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            result = object.getObjectId();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
