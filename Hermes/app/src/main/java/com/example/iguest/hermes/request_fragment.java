package com.example.iguest.hermes;


import android.os.Bundle;
import android.app.Fragment;
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        Log.v("a", object.toString());
                        /*String screenName = object.getString("screenName");
                        String phone = object.getString("phoneNumber");
                        int score = object.getInt("score");
                        User a = new User(screenName, phone, score);*/

                        String user = object.getString("userId");
                        final String restaurantID = object.getString("restaurantId");
                        ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                        String descript = object.getString("description");
                        final Request a = new Request(user,deliveryLocation, restaurantID, descript);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
                        query.orderByDescending("createdAt").setLimit(200);
                        query.findInBackground(new FindCallback<ParseObject>() {

                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null) {
                                    for (ParseObject object : objects) {
                                        Log.v("a", object.getString("objectId"));
                                        if (object.getString("objectId") == restaurantID) {
                                            //Log.v("a", object.getString("Name"));
                                            a.setRestaurantName(object.getString("Name"));
                                            break;
                                        }
                                    }
                                    adapter.add(a);
                                }
                            }
                        });
                    }
                }
            }
        });


        return rootView;
        //return inflater.inflate(R.layout.fragment_request_fragment, container, false);
    }

}
