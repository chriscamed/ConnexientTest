package com.medios.connexienttest.model.application;

import android.app.Application;

/**
 * Created by Camilo on 7/15/17.
 */

public class App extends Application {
    private static App instance;
    public static App get() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
