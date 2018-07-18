package com.arropa;

import android.app.Application;

import com.arropa.sharedpreference.PreferenceManger;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManger.initPreference(getApplicationContext());

        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/DroidSans.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
