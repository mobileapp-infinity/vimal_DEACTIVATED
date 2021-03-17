package com.infinity.infoway.vimal.fragment;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class Background_Service extends Service
{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(1,new Notification());
        onTaskRemoved(intent);


        // do your jobs here
        //Toast.makeText(getApplicationContext(),"this is service",Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
           startForegroundService(restartServiceIntent);


        }
        else
            {
            startService(restartServiceIntent);

        }

        super.onTaskRemoved(rootIntent);



//        startService(restartServiceIntent);

    }

}
