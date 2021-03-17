package com.infinity.infoway.vimal.config;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.infinity.infoway.vimal.fragment.Background_Service;


public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        startService(new Intent(this, Background_Service.class));
       // TypefaceUtil.overrideFont(getApplicationContext(), "serif", "fonts/Helvetica.ttf");

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override

            public void uncaughtException(Thread thread, Throwable ex)
            {

                handleUncaughtException(thread, ex);



            }

        });
    }


    public void handleUncaughtException (Thread thread, Throwable e)

    {

        String stackTrace = Log.getStackTraceString(e);

        String message = e.getMessage();





        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");

        intent.putExtra (Intent.EXTRA_EMAIL, new String[] {"pragnabhatt.iipl@gmail.com"});

        intent.putExtra (Intent.EXTRA_SUBJECT, "Vimal Crash log file");

        intent.putExtra (Intent.EXTRA_TEXT, stackTrace);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application

        startActivity(intent);

    }
}
