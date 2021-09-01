package com.patropatri.app.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patropatri.app.R;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.WebSocketClient;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.AccountFragment;
import com.patropatri.app.views.fragments.HomeFragment;
import com.patropatri.app.views.fragments.InboxFragment;
import com.patropatri.app.views.fragments.NotificationFragment;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private UserWithToken mUser;
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
        mUser = SharedPefManager.getInstance(this).getUser();

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

        //-----------------------------------------------| StompClient Connect
        StompClient mClient = WebSocketClient.getInstance().getStompClient();

        if (mUser != null) {
            mClient.topic("/user/placeholder/queue/specific-user".replace("placeholder", mUser.getAuth().getId() + "")).subscribe(new Consumer<StompMessage>() {
                @Override
                public void accept(StompMessage message) throws Exception {
                    JSONObject json = new JSONObject(message.getPayload());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.d(TAG, json.toString());
                                Log.d(TAG, json.getString("title"));
                                Log.d(TAG, json.getString("type"));
                                Log.d(TAG, json.getString("senderId"));
                                Log.d(TAG, json.getString("receiverId"));
                                //bundle.putString("title", json.getString("title"));
                                //bundle.putString("type", json.getString("type"));
                                //bundle.putLong("senderId", json.getLong("senderId"));
                                //bundle.putLong("receiverId", json.getLong("receiverId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

        //-----------------------------------------------| Bottom navigation menu
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(new ActionHandler());

        //-----------------------------------------------| Get intent data from NotificationService
        if( getIntent().getExtras() != null) {
            if (getIntent().hasExtra("DATA")) {
                String data = getIntent().getStringExtra("DATA");
                Log.d(TAG, data);
            }
        }

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
                case R.id.action_notification:
                    if (mUser != null && mUser.getUser() != null) {
                        Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new NotificationFragment());
                    }
                    return true;
                case R.id.action_inbox:
                    if (mUser != null && mUser.getUser() != null) {
                        Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new InboxFragment());
                    }
                    return true;
                case R.id.action_account:
                    Utility.getInstance().onReplaceFragment(getSupportFragmentManager(), new AccountFragment());
                    return true;
            }
            return false;
        }
    }



}