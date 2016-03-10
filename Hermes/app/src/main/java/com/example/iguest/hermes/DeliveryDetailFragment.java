package com.example.iguest.hermes;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryDetailFragment extends Fragment {

    private TextView titleTextView;
    private TextView statusTextView;
    private TextView restaurantTextView;
    private TextView userTextView;
    private TextView descriptionTextView;
    private Button pickedUp;
    private Button delivered;
    private Button cancel;

    public DeliveryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_delivery_detail, container, false);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            InitializeFields(rootView);
            SetFieldValues(bundle);

            pickedUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery query = new ParseQuery("Request");
                    query.getInBackground(bundle.getString("id"), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (object != null) {
                                object.put("status", "Picked Up");
                                object.saveInBackground();
                            }
                        }
                    });
                    Toast.makeText(getActivity(), "Status changed to Picked Up", Toast.LENGTH_LONG).show();
                }
            });

            delivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ParseQuery query = new ParseQuery("Request");
                    query.include("userId");
                    query.getInBackground(bundle.getString("id"), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (object != null) {
                                Log.v("b", "here");
                                object.put("status", "Delivered");
                                int count = object.getParseObject("userId").getInt("score");
                                object.getParseObject("userId").put("score", count + 1);
                                object.saveInBackground();
                            }
                        }
                    });
                    Toast.makeText(getActivity(), "Request Delivered", Toast.LENGTH_LONG).show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery query = new ParseQuery("Request");
                    query.getInBackground(bundle.getString("id"), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (object != null) {
                                Log.v("c", "here");
                                object.remove("delivererId");
                                object.put("status", "Pending");
                                object.saveInBackground();
                            }
                        }
                    });
                    Toast.makeText(getActivity(), "Request Canceled", Toast.LENGTH_LONG).show();
                }
            });
        }
        return rootView;
    }


    private void InitializeFields(View rootView) {
        titleTextView = (TextView) rootView.findViewById(R.id.DeliverDetailTitle);
        statusTextView = (TextView) rootView.findViewById(R.id.DeliverDetailStatus);
        restaurantTextView = (TextView) rootView.findViewById(R.id.DeliverDetailRestaurant);
        userTextView = (TextView) rootView.findViewById(R.id.DeliverDetailUser);
        descriptionTextView = (TextView) rootView.findViewById(R.id.DeliverDetailDescription);

        pickedUp = (Button) rootView.findViewById(R.id.picked);
        delivered = (Button) rootView.findViewById(R.id.deliver);
        cancel = (Button) rootView.findViewById(R.id.cancel);
    }

    private void SetFieldValues(Bundle bundle) {
        titleTextView.setText(bundle.getString("title"));
        statusTextView.setText(bundle.getString("status"));
        restaurantTextView.setText(bundle.getString("restaurant"));
        userTextView.setText(bundle.getString("user"));
        descriptionTextView.setText(bundle.getString("description"));
    }
}
