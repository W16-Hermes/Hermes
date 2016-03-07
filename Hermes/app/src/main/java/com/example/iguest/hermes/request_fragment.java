package com.example.iguest.hermes;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class request_fragment extends Fragment {
    private ArrayAdapter adapter;
    private RequestListener callback;
    private boolean test = false;

    public interface RequestListener{
        public void onSelected(Request r);
        public void moveSummary();
    }


    public request_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_request_fragment, container, false);

        ArrayList<User>  list = new ArrayList<>();
        adapter = new ArrayAdapter<User>(getActivity(), R.layout.list_item, list);
        AdapterView listView = (AdapterView) rootView.findViewById(R.id.requestList);
        listView.setAdapter(adapter);

        if (test) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
            query.include("userId");
            query.include("restaurantId");
            query.orderByDescending("createdAt").setLimit(200);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : objects) {
                            String user = object.getParseObject("userId").getString("screenName");
                            String restaurant = object.getParseObject("restaurantId").getString("Name");
                            ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                            String descript = object.getString("description");
                            Request request = new Request(user, deliveryLocation, restaurant, descript);

                            adapter.add(request);
                        }
                    }
                }
            });
        } else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
            query.include("userId");
            query.include("restaurantId");
            SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final String display = options.getString("displayName", null);
            Log.v("a", display);
            //query.whereEqualTo("screenName", display);
            query.orderByDescending("createdAt").setLimit(200);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject object : objects) {
                            String user = object.getParseObject("userId").getString("screenName");
                            String restaurant = object.getParseObject("restaurantId").getString("Name");
                            ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                            String descript = object.getString("description");
                            Request request = new Request(user, deliveryLocation, restaurant, descript);
                            if (user.equals(display)) {
                                adapter.add(request);
                            }

                        }
                    }
                }
            });
        }


        return rootView;
        //return inflater.inflate(R.layout.fragment_request_fragment, container, false);
    }

}
