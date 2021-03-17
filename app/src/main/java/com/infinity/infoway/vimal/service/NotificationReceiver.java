package com.infinity.infoway.vimal.service;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import androidx.legacy.content.WakefulBroadcastReceiver;
import android.util.Log;

public class NotificationReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        playNotificationSound(context);


        Intent service = new Intent(context, NotificationReceiver.class);

        // Start the service, keeping the device awake while it is launching.
        Log.i("SimpleWakefulReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);
    }

    public void playNotificationSound(Context context)
    {
        try
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}