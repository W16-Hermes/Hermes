package com.example.iguest.hermes;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDetailFragment extends Fragment {


    public RequestDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_request_detail, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            TextView titleTextView = (TextView) rootView.findViewById(R.id.RequestDetailTitle);
            titleTextView.setText(bundle.getString("title"));

            TextView userTextView = (TextView) rootView.findViewById(R.id.RequestDetailUser);
            userTextView.setText(bundle.getString("user"));

            TextView restaurantTextView = (TextView) rootView.findViewById(R.id.RequestDetailRestaurant);
            restaurantTextView.setText(bundle.getString("restaurant"));

            TextView descriptionTextview = (TextView) rootView.findViewById(R.id.RequestDetailDescription);
            descriptionTextview.setText(bundle.getString("description"));

            TextView statusTextView = (TextView) rootView.findViewById(R.id.RequestDetailStatus);
            statusTextView.setText(bundle.getString("status"));

            TextView deliveryLocationTextView = (TextView) rootView.findViewById(R.id.RequestDetailDeliveryLocation);
            deliveryLocationTextView.setText(bundle.getString("deliveryLocation"));
        }

        return rootView;
    }

}
