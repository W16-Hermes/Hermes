package com.example.iguest.hermes;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.GetCallback;
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
public class UserPopup extends DialogFragment {
    PopupListener mListener;

    public UserPopup() {
        // Required empty public constructor
    }

    public interface PopupListener {
        public void onDialogPositive(DialogFragment dialog);
        public void onDialogNegative(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PopupListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_popup, container, false);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Creates the new popup dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.fragment_add_request, null);
        AlertDialog.Builder builder1 = builder.setView(rootView)
                //Confirms the action
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText foundName = (EditText) rootView.findViewById(R.id.generatedName);
                        final String name = foundName.getText().toString();
                        if (name.length() == 0) {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                            query.orderByDescending("createdAt").setLimit(1);
                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, com.parse.ParseException e) {
                                    String userid = object.getObjectId();
                                    SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    SharedPreferences.Editor prefEditor = options.edit();
                                    prefEditor.putString("displayName", name);
                                    prefEditor.putString("userId", userid);
                                    prefEditor.commit();
                                    mListener.onDialogPositive(UserPopup.this);

                                }
                            });
                        }
                    }
                })

                //Cancels the action
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegative(UserPopup.this);
                    }
                });
        return builder.create();
    }

}
