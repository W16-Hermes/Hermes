package com.example.iguest.hermes;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDetailFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    private static final String TAG = "REQUEST_DETAIL";

    MapFragment mMapView;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int LOC_REQUEST_CODE = 1;
    private double rLatitude;
    private double rLongitude;
    private String rName;

    public RequestDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_request_detail, container, false);

        Button accept = (Button) rootView.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final String display = options.getString("displayName", "");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("screenName", display);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            ParseObject newEntry = new ParseObject("User");
                            newEntry.put("screenName", display);
                            newEntry.put("score", 0);
                            newEntry.saveInBackground();
                        }
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
                        query.include("userId");
                        query.include("restaurantId");

                    }
                });
                Toast.makeText(getActivity(), "Request Accepted", Toast.LENGTH_LONG).show();
            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
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
            rLatitude = bundle.getDouble("Latitude");
            rLongitude = bundle.getDouble("Longitude");
            rName = bundle.getString("restaurant");

            mMapView.getMapAsync(this);

            //mMapView.setRetainInstance(true);
            mMapView.setHasOptionsMenu(true);

            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(rLatitude, rLongitude)).title(rName);

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            googleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(rLatitude, rLongitude)).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }
        return rootView;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "here");
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Log.v(TAG, "Connecting");
        if(mGoogleApiClient != null) {
            Log.v(TAG, "Not Null");
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED ) {
                Log.v(TAG, "Permission Granted");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
                try {
                    Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (loc != null) {
                        MarkerOptions marker = new MarkerOptions().position(
                                new LatLng(loc.getLatitude(), loc.getLongitude())).title("My location");
                        marker.icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        googleMap.addMarker(marker);

                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(loc.getLatitude(), loc.getLongitude())).zoom(16).build();
                        googleMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }
                } catch(SecurityException e) {
                    Log.v(TAG, "Security Exception");
                }
            } else {
                Log.v(TAG, "Permission not Granted");
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
        googleMap.clear();
        MarkerOptions restaurant = new MarkerOptions().position(
                new LatLng(rLatitude, rLatitude)).title(rName);
        restaurant.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        googleMap.addMarker(restaurant);
        Log.v("GPS Tester", "Location Changed");
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(location.getLatitude(), location.getLongitude())).title("Current");
        googleMap.addMarker(marker);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onStart() {
        //Allows requests for location updates
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        //Stops requests for location updates
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
