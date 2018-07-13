package com.arropa;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
