package com.infinity.infoway.vimal.activity;

import android.app.Application;

import com.infinity.infoway.vimal.util.common.TypefaceUtil;

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Helvetica.ttf");


    }
}
