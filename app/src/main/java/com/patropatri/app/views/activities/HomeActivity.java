package com.patropatri.app.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patropatri.app.R;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private MyNetworkReceiver mNetworkReceiver;
    public static BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //-----------------------------------------------| Check network and location
        mNetworkReceiver = new MyNetworkReceiver(this);
        //mLocationReceiver = new MyLocationReceiver(this);

        //-----------------------------------------------| Session
        //mUser = SharedPefManager.getInstance(this).getUser();
        //mPermissions = SharedPefManager.getInstance(this).getPermission();

        //-----------------------------------------------| To Display Navigation Bar
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                //getSupportActionBar().setDisplayShowTitleEnabled(false); //Remove title
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
            //toolbar.getBackground().setAlpha(200);
        }*/

        //-----------------------------------------------| Bottom navigation menu
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(new ActionHandler());

        //-----------------------------------------------| Home fragment
        Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ActionHandler implements BottomNavigationView.OnNavigationItemSelectedListener {
        //-----------------------------------------------| Bottom navigation menu listener
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new HomeFragment());
                    return true;
                case R.id.action_question:
                    //Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new HomeFragment());
                    return true;
                case R.id.action_review:
                    //Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new HomeFragment());
                    return true;
            }
            return false;
        }
    }
}