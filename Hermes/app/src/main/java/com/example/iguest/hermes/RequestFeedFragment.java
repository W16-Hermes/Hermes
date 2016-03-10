package com.example.iguest.hermes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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


public class RequestFeedFragment extends Fragment {
    private static final String TAG = "REQUEST_FEED";
    private ArrayAdapter adapter;

    public interface RequestListener{
        void onSelected(Request r);
    }

    public RequestFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_request_feed, container, false);

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
                ((RequestListener) getActivity()).onSelected(entry);
            }
        });

        final FloatingActionButton compose = (FloatingActionButton) rootView.findViewById(R.id.fab);
        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Add request clicked");
                new AddRequestFragment().show(getActivity().getFragmentManager(), "dialog");
            }
        });

        GetAllRequests();

        return rootView;
    }

    private void GetAllRequests() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.include("userId");
        query.include("restaurantId");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        String user;
                        if(object.getParseObject("userId") != null) {
                            Log.v(TAG, "Object is not null");
                            user = object.getParseObject("userId").getString("screenName");
                        } else {
                            Log.v(TAG, "Object is null");
                            user = "Ci4axeN0LI";
                        }
                        String restaurant = object.getParseObject("restaurantId").getString("Name");
                        ParseGeoPoint deliveryLocation = object.getParseGeoPoint("deliveryLocation");
                        String descript = object.getString("description");
                        String status = object.getString("status");
                        Request request = new Request(user, deliveryLocation, restaurant, descript);
                        request.setRequestID(object.getObjectId());
                        request.setStatus(object.getString("status"));
                        if (status.equals("Pending")) {
                            adapter.add(request);
                        }
                    }
                }
            }
        });
    }

}
