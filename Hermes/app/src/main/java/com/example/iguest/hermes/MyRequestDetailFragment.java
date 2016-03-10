package com.example.iguest.hermes;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRequestDetailFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static final String TAG = "MY_REQUEST_DETAILS_FEED";
    MapFragment mMapView;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int LOC_REQUEST_CODE = 1;
    private Button editButton;


    public MyRequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_my_request_detail, container, false);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            TextView titleTextView = (TextView) rootView.findViewById(R.id.MyRequestDetailTitle);
            titleTextView.setText(bundle.getString("title"));

            TextView restaurantTextView = (TextView) rootView.findViewById(R.id.MyRequestDetailRestaurant);
            restaurantTextView.setText(bundle.getString("restaurant"));

            TextView descriptionTextView = (TextView) rootView.findViewById(R.id.MyRequestDetailDescription);
            descriptionTextView.setText(bundle.getString("description"));

            TextView statusTextView = (TextView) rootView.findViewById(R.id.MyRequestDetailStatus);
            statusTextView.setText(bundle.getString("status"));
            
            mMapView = (MapFragment) getChildFragmentManager().findFragmentById(R.id.MyRequestDetailsMap);
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

    private void UpdateRequestAction() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("a", "here");
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if(mGoogleApiClient != null) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED ) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
                try {
                    Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (loc != null) {
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(loc.getLatitude(), loc.getLongitude())).title(bundle.getString("restaurant"));
                        googleMap.addMarker(marker);
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(loc.getLatitude(), loc.getLongitude())).zoom(16).build();
                        googleMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }
                } catch(SecurityException e) {
                    Log.v("A", "Security Exception");
                }
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOC_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(location.getLatitude(), location.getLongitude())).title("Current");
        googleMap.addMarker(marker);
    }
}
