package com.eleganzit.cgp.utils;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app

        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
