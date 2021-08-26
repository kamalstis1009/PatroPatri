package com.patropatri.app.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.patropatri.app.R;
import com.patropatri.app.repositories.WebSocketClient;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.views.activities.HomeActivity;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";

    public NotificationService() {
        super(NotificationService.class.getSimpleName()); //OR super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            //-----------------------------------------------| StompClient Connect
            StompClient mClient = WebSocketClient.getInstance().getStompClient();

            // Subscribe broadcast endpoint to receive response by RX Java
            mClient.topic(ConstantKey.BROADCAST_RESPONSE).subscribe(new Consumer<StompMessage>() {
                @Override
                public void accept(StompMessage stompMessage) throws Exception {
                    JSONObject jsonObject = new JSONObject(stompMessage.getPayload());
                    Log.d(TAG, "Receive: " + stompMessage.getPayload());
                    Log.d(TAG, "Receive Res: " + jsonObject.getString("response"));
                    sendNotification("Title", jsonObject.getString("response"), null);
                }
            });
        }
    }

    private void sendNotification(String title, String body, Bundle data) {
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.putExtras(data);
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
