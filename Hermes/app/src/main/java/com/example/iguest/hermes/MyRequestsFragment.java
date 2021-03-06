package com.example.iguest.hermes;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
public class MyRequestsFragment extends Fragment {
    private static final String TAG = "MY_REQUEST_FRAGMENT";
    private ArrayAdapter adapter;

    public interface MyRequestListener{
        void onMyRequestSelected(Request r);
    }

    public MyRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_my_requests, container, false);

        ArrayList<User> list = new ArrayList<>();
        adapter = new ArrayAdapter<User>(getActivity(), R.layout.list_item, list);
        AdapterView listView = (AdapterView) rootView.findViewById(R.id.requestList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request entry = (Request) parent.getItemAtPosition(position);
                Log.i("My_Requests_Fragment", "selected: " + entry);

                //swap the fragments to show the detail
                ((MyRequestsFragment.MyRequestListener) getActivity()).onMyRequestSelected(entry);
            }
        });

        GetMyRequests();

        return rootView;
    }

    private void GetMyRequests() {
        Log.v(TAG, "In here");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.include("userId");
        query.include("restaurantId");
        SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String display = options.getString("displayName", " ");
        final String id = options.getString("userId", " ");
        Log.v(TAG, "Display name is:" + display);
        Log.v(TAG, "User ID is:" + id);
        query.include("userId");
        query.include("delivererId");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null) {
                    Log.v(TAG, "Found requests for you");
                    for (ParseObject object : objects) {
                        String delivererName = "No one yet";
                        if (object.getParseObject("delivererId") != null) {
                            delivererName = object.getParseObject("delivererId").getString("screenName");
                        }
                        Log.v(TAG, "Deliverer is: " + delivererName);
                        Log.v(TAG, "In query loop");
                        Log.v(TAG, object.get("userId").toString());
                        String user = object.getParseObject("userId").getString("screenName");
                        String restaurant = object.getParseObject("restaurantId").getString("Name");
                        ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                        String descript = object.getString("description");
                        Request request = new Request(display, deliveryLocation, restaurant, descript, delivererName);
                        request.setRequestID(object.getObjectId());
                        request.setStatus(object.getString("status"));
                        if(user.equals(display)) {
                            adapter.add(request);
                        }
                    }
                } else {
                    Log.v(TAG, "No requests for you");
                }
            }
        });
    }

}
