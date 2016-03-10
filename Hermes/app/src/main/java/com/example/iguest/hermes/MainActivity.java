package com.example.iguest.hermes;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.Parse;

public class MainActivity extends AppCompatActivity implements
        AddRequestFragment.DialogListener,
        RequestFeedFragment.RequestListener,
        MyRequestsFragment.MyRequestListener {

    private static final String TAG = "MAIN_ACTIVITY";
    private FragmentManager manager;
    private FragmentTransaction ft;
    private RequestDetailFragment detail;
    private MyRequestDetailFragment myDetail;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Request Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("My Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("My Deliveries"));
        tabLayout.addTab(tabLayout.newTab().setText("Leader Board"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Parse.initialize(this);
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
        //Listeners for when the Setting menu is clicked
        switch (item.getItemId()) {
            case R.id.menu_item1:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
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
        detail = new RequestDetailFragment();

        //Fills out each individual list item with more detailed data
        Bundle bundle = new Bundle();
        bundle.putString("description", r.getDescription());
        bundle.putString("status", r.getStatus());
        bundle.putDouble("Latitude", r.getDeliveryLocation().getLatitude());
        bundle.putDouble("Longitude", r.getDeliveryLocation().getLongitude());
        bundle.putString("deliveryLocation", r.getDeliveryLocation().toString());
        bundle.putString("user", r.getUserId());
        bundle.putString("restaurant", r.getRestaurantName());
        bundle.putString("title", r.toString());
        detail.setArguments(bundle);

        manager = getFragmentManager();

        ft = manager.beginTransaction();
        ft.replace(R.id.container, detail)
            .addToBackStack(null)
            .commit();

        ConfigureToolbar();
    }

    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            super.onBackPressed();
        }
    }

    private void ConfigureToolbar() {
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Log.v(TAG, "Back button pressed");
            }
        });
    }

    @Override
    public void onMyRequestSelected(Request r) {
        myDetail = new MyRequestDetailFragment();

        //Fills out each individual list item with more detailed data
        Bundle bundle = new Bundle();
        bundle.putString("description", r.getDescription());
        bundle.putString("status", r.getStatus());
        bundle.putDouble("Latitude", r.getDeliveryLocation().getLatitude());
        bundle.putDouble("Longitude", r.getDeliveryLocation().getLongitude());
        bundle.putString("deliveryLocation", r.getDeliveryLocation().toString());
        bundle.putString("user", r.getUserId());
        bundle.putString("restaurant", r.getRestaurantName());
        bundle.putString("title", r.toString());
        myDetail.setArguments(bundle);

        manager = getFragmentManager();

        ft = manager.beginTransaction();
        ft.replace(R.id.container, myDetail)
                .addToBackStack(null)
                .commit();

        ConfigureToolbar();
    }
}
