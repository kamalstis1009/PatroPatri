package com.patropatri.app.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.patropatri.app.R;
import com.patropatri.app.repositories.WebSocketClient;
import com.patropatri.app.views.activities.HomeActivity;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class NotificationService extends Service {

    private static final String TAG = "NotificationService";
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final	String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static final	String ACTION_PAUSE = "ACTION_PAUSE";
    public static final	String ACTION_PLAY = "ACTION_PLAY";

    public NotificationService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        startForegroundService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        //startForegroundService();
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        Log.d(TAG, "STOP");
                        break;
                    case ACTION_PLAY:
                        Log.d(TAG, "PLAY");
                        break;
                    case ACTION_PAUSE:
                        Log.d(TAG, "PAUSE");
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private void startForegroundService() {
        //-----------------------------------------------| StompClient Connect
        StompClient mClient = WebSocketClient.getInstance().getStompClient();

        // Subscribe broadcast endpoint to receive response by RX Java
        mClient.topic("/topic").subscribe(new Consumer<StompMessage>() {
            @Override
            public void accept(StompMessage stompMessage) throws Exception {
                JSONObject jsonObject = new JSONObject(stompMessage.getPayload());
                Log.d(TAG, "Receive: " + stompMessage.getPayload());
                Log.d(TAG, "Receive Res: " + jsonObject.getString("response"));

                Bundle bundle = new Bundle();
                bundle.putString("DATA", jsonObject.getString("response"));
                sendNotification("Title", jsonObject.getString("response"), bundle);
            }
        });
    }

    private void sendNotification(String title, String body, Bundle data) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(data);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

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
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

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
