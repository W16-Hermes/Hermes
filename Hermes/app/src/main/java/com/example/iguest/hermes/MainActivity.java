package com.example.iguest.hermes;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;


import com.parse.Parse;

public class MainActivity extends AppCompatActivity implements addRequest.DialogListener, request_fragment.RequestListener {

    private static final String TAG = "REQUEST_FEED";
    private FragmentManager manager;
    private FragmentTransaction ft;
    private request_detail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this);


//        ParseQuery<ParseObject> query = ParseQuery.getQuery("bubble_tea");

        manager = getFragmentManager();
        ft = manager.beginTransaction();
        ft.add(R.id.container, new request_fragment());
        ft.commit();


        FloatingActionButton compose = (FloatingActionButton) findViewById(R.id.fab);
        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Add request clicked");
                //ft.add(R.id.container, new addRequest());
                //ft.commit();
                new addRequest().show(getFragmentManager(), "dialog");
                /*Intent intent = new Intent(reading.this, lass);
                startActivity(intent);*/
            }
        });

        Button feed = (Button) findViewById(R.id.Feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "View Feed clicked");
                /*Intent intent = new Intent(reading.this, lass);
                startActivity(intent);*/
            }
        });

        Button request = (Button) findViewById(R.id.Request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "View Requests clicked");

                /*Intent intent = new Intent(reading.this, lass);
                startActivity(intent);*/
            }
        });
        Button deliver = (Button) findViewById(R.id.Delivery);
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "View Deliveries clicked");

                /*Intent intent = new Intent(reading.this, lass);
                startActivity(intent);*/
            }
        });
        Button leader = (Button) findViewById(R.id.Leader);
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "View Leaderboard clicked");

                /*Intent intent = new Intent(reading.this, lass);
                startActivity(intent);*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Initializes the Setting menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Listeners for when the setting menu is clicked
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onSelected(Request r) {
        detail = new request_detail();

        //Fills out each individual list item with more detailed data
        Bundle bundle = new Bundle();
        detail.setArguments(bundle);


        //Changes the fragments so the list_view is on the left and the details is on the right
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        Fragment a = getFragmentManager().findFragmentById(R.id.container);
        ft.replace(R.id.container, detail);
        ft.addToBackStack(null);
        ft.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
