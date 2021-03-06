package com.example.iguest.hermes;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment {

    private ArrayAdapter adapter;
    private RequestFeedFragment.RequestListener callback;

    public interface DeliverListener{
        void onDeliverSelected(Request r);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_delivery, container, false);

        ArrayList<User> list = new ArrayList<>();
        adapter = new ArrayAdapter<User>(getActivity(), R.layout.list_item, list);
        AdapterView listView = (AdapterView) rootView.findViewById(R.id.requestList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request entry = (Request) parent.getItemAtPosition(position);
                Log.i("Request_Fragment", "selected: " + entry);

                //swap the fragments to show the detail
                ((DeliverListener) getActivity()).onDeliverSelected(entry);
            }
        });

        GetMyDeliveries();

        return rootView;
    }

    // Needs to change query to account for my deliverer of requests
    private void GetMyDeliveries() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.include("userId");
        SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String display = options.getString("displayName", " ");
        final String id = options.getString("userId", "");
        query.whereNotEqualTo("status", "Delivered");
        query.include("userId");
        query.include("delivererId");
        query.include("restaurantId");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null) {
                    for (ParseObject object : objects) {
                        if (object.getParseObject("delivererId") != null) {
                            String user = object.getParseObject("userId").getString("screenName");
                            String deliver = object.getParseObject("delivererId").getString("screenName");
                            String restaurant = object.getParseObject("restaurantId").getString("Name");
                            ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                            String descript = object.getString("description");
                            Request request = new Request(user, deliveryLocation, restaurant, descript, "");
                            request.setRequestID(object.getObjectId());
                            request.setStatus(object.getString("status"));
                            if (deliver.equals(display)) {
                                adapter.add(request);
                            }
                        }
                    }
                }
            }
        });
    }

}
