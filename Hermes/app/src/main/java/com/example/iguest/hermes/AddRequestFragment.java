package com.example.iguest.hermes;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.crypto.spec.DESedeKeySpec;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRequestFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    DialogListener mListener;
    private static final String TAG = "Tracker Fragment";
    private String selectedRestaurant = "";
    private String[] restaurants = new String[11];
    private Random random = new Random();


    public AddRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        selectedRestaurant = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedRestaurant = parent.getItemAtPosition(0).toString();
    }

    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recording, container, false);
        return rootView;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Creates the new popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.fragment_add_request, null);
        final List<String> categories = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");
        query.orderByDescending("createdAt").setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        String name = object.getString("Name");
                        categories.add(name);
                    }
                }
            }
        });

        categories.add("Please Select Restaurant");

        restaurants[0] = "GR472AUMZ1";
        restaurants[1] = "8889Evoy5m";
        restaurants[2] = "JQAK4hwojI";
        restaurants[3] = "8Im3Zbmd2P";
        restaurants[4] = "C9oBlivLgp";
        restaurants[5] = "b8QdEL8uMK";
        restaurants[6] = "wz8o2l5Iec";
        restaurants[7] = "fyW48URAVy";
        restaurants[8] = "jx8E4mMCE4";
        restaurants[9] = "hh1Qz3nQmr";
        restaurants[10] = "C6WJm1AsKV";


        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

                builder.setView(rootView)
                        //Confirms the action
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                final String userId = options.getString("userId", "");

                                //Enters the data into Parse.com
                                ParseObject newEntry = new ParseObject("Request");
                                newEntry.put("userId", ParseObject.createWithoutData("User", userId));
                                newEntry.put("deliveryLocation", new ParseGeoPoint(47.65722, -122.31561));
                                newEntry.put("status", "Pending");
                                newEntry.put("restaurantId", ParseObject.createWithoutData("Restaurants", restaurants[random.nextInt(11)]));
                                String description = ((EditText) rootView.findViewById(R.id.reqDescription)).getText().toString();
                                newEntry.put("description", description);
                                newEntry.saveInBackground();
                            }
                        })
                                //Cancels the action
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onDialogNegativeClick(AddRequestFragment.this);
                            }
                        });
        return builder.create();
    }
}
