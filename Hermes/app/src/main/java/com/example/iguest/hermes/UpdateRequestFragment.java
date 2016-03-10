package com.example.iguest.hermes;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateRequestFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "MAIN_ACTIVITY";
    private String selectedRestaurant = "";

    private TextView titleView;
    private TextView statusView;
    private Spinner restaurantSpinner;
    private EditText descriptionView;

    private Button updateButton;
    private Button deleteButton;

    public interface UpdateRequestListener{
        void onEditButtonSelected (Request r);
    }

    public UpdateRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_update_request, container, false);
        InitializeFields(rootView);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Update button clicked");
                String descriptionText = descriptionView.getText().toString();
                if(descriptionText.length() == 0) {
                    Toast.makeText(getActivity(), "Restaurant cannot be null", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Delete button clicked");
            }
        });

        return rootView;
    }

    private void InitializeFields(View rootView) {
        titleView = (TextView) rootView.findViewById(R.id.UpdateRequestTitle);
        statusView = (TextView) rootView.findViewById(R.id.UpdateRequestStatus);

        descriptionView = (EditText) rootView.findViewById(R.id.UpdateRequestDescription);

        restaurantSpinner = (Spinner) rootView.findViewById(R.id.UpdateRequestRestaurant);
        InitializeSpinner();

        updateButton = (Button) rootView.findViewById(R.id.UpdateButton);
        deleteButton = (Button) rootView.findViewById(R.id.DeleteButton);
    }

    private void InitializeSpinner() {
        final List<String> categories = new ArrayList<String>();
        final Map<String, String> restaurantFinder = new HashMap<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        String name = object.getString("Name");
                        String oID = object.getObjectId();
                        categories.add(name);
                        restaurantFinder.put(name, oID);
                    }
                }
            }
        });
        categories.add("Please Select Restaurant");
        restaurantFinder.put("Please Select Restaurant", "JQAK4hwojI");

        restaurantSpinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        restaurantSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedRestaurant = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedRestaurant = parent.getItemAtPosition(0).toString();
    }
}
