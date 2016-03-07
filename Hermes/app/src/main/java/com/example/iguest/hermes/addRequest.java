package com.example.iguest.hermes;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class addRequest extends DialogFragment {
    DialogListener mListener;
    private static final String TAG = "Tracker Fragment";


    public addRequest() {
        // Required empty public constructor
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
        builder.setView(rootView)
                //Confirms the action
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Enters the data into Parse.com
                        ParseObject newEntry = new ParseObject("bubble_tea");
                       // final String name = ((EditText)rootView.findViewById(R.id.addName)).getText().toString();
                       // final String rating = ((EditText)rootView.findViewById(R.id.addRating)).getText().toString();
                       // final String location = ((EditText)rootView.findViewById(R.id.addLocation)).getText().toString();

                        //Catches if the user doesn't input an integer for rating
                        try {
                            //int stars = Integer.parseInt(rating);
                            //newEntry.put("name", name);
                            //newEntry.put("rating", stars);
                            //newEntry.put("location", location);
                            newEntry.saveInBackground();
                            mListener.onDialogPositiveClick(addRequest.this);
                        } catch(NumberFormatException e) {
                            Log.v(TAG, "error");
                            Context context = getActivity();
                            String text = "Entry Not Added";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                })
                        //Cancels the action
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(addRequest.this);
                    }
                });
        return builder.create();
    }
}
