package com.example.iguest.hermes;

import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;


import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddRequestFragment.DialogListener, RequestFeedFragment.RequestListener {

    private static final String TAG = "REQUEST_FEED";
    private FragmentManager manager;
    private FragmentTransaction ft;
    private RequestDetailFragment detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.replace(R.id.container, new ViewPagerContainerFragment());
        ft.commit();

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
        detail.setArguments(bundle);

        manager = getSupportFragmentManager();

        ft = manager.beginTransaction();
        ft.replace(R.id.container, detail)
            .addToBackStack(null)
            .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
