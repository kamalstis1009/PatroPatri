package com.patropatri.app.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patropatri.app.R;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.WebSocketClient;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
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

        //-----------------------------------------------| Bottom navigation menu
        mNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(new ActionHandler());

        //-----------------------------------------------| StompClient Connect
        StompClient mClient = WebSocketClient.getInstance().getStompClient();

        // Subscribe broadcast endpoint to receive response by RX Java
        mClient.topic(ConstantKey.BROADCAST_RESPONSE).subscribe(new Consumer<StompMessage>() {
            @Override
            public void accept(StompMessage stompMessage) throws Exception {
                JSONObject jsonObject = new JSONObject(stompMessage.getPayload());
                Log.d(TAG, "Receive: " + stompMessage.getPayload());
                Log.d(TAG, "Receive Res: " + jsonObject.getString("response"));
                sendNotification("Title", jsonObject.getString("response"));
            }
        });

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

    private void sendNotification(String title, String body) {
        //https://developer.android.com/guide/topics/ui/notifiers/notifications#ManageChannels
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_baseline_radio_button_checked_24)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
            notificationBuilder.setLargeIcon(icon);
        }*/

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,"Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        manager.notify(0, notificationBuilder.build());
    }
}