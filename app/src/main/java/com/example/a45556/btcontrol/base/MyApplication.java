package com.example.a45556.btcontrol.base;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.example.a45556.btcontrol.utils.PreferenceUtil.init(this);
    }
}
