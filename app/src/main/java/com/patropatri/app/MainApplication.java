package com.patropatri.app;

import android.app.Application;
import android.content.Intent;

import com.patropatri.app.services.NotificationService;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //startService(new Intent(this, NotificationService.class));
    }
}
