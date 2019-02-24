package com.myapplication;

import android.app.Application;
import android.content.Context;

public class YasmaApplication extends Application{

    public static YasmaApplication instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
