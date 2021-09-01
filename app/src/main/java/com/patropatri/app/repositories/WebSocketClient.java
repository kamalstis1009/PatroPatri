package com.patropatri.app.repositories;

import android.util.Log;

import com.patropatri.app.utils.ConstantKey;

import org.json.JSONException;
import org.json.JSONObject;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class WebSocketClient {

    private String TAG = this.getClass().getSimpleName();
    private static WebSocketClient mInstance;

    //Singleton design pattern
    public static synchronized WebSocketClient getInstance() {
        if (mInstance == null) {
            mInstance = new WebSocketClient();
        }
        return mInstance;
    }

    // Start connecting to server
    public StompClient getStompClient() {
        StompClient mClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, ConstantKey.SOCKET_SERVER_URL);
        mClient.connect();
        lifecycle(mClient); //Connection callback
        return mClient;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "CheckResult"})
    public void lifecycle(StompClient stompClient) {
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
            }
        });
    }

    public void sendMessage(StompClient client, String title, String type, Long senderId, Long receiverId) {
        JSONObject json = new JSONObject();
        try {
            json.put("title", title);
            json.put("type", type);
            json.put("senderId", senderId);
            json.put("receiverId", receiverId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send("/app/specific-user", json.toString()).subscribe();
    }

}
