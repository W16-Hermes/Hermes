package com.example.iguest.hermes;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDetailFragment extends Fragment {
    MapFragment mMapView;
    private GoogleMap googleMap;

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

            mMapView = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume();// needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            googleMap = mMapView.getMap();
            // latitude and longitude
            double latitude = bundle.getDouble("Latitude");
            double longitude = bundle.getDouble("Longitude");

            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude, longitude)).title(bundle.getString("restaurant"));

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            googleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
