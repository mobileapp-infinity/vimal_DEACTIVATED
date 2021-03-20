package com.infinity.infoway.vimal.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.infinity.infoway.vimal.database.SharedPref;

import java.util.Iterator;
import java.util.List;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    Context context;
    private SharedPref getSharedPref;
BroadcastReceiver broadcastReceiver;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        getSharedPref = new SharedPref(context);
      Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!  ");
      //  Log.e(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!  ");
    /*   try {
            System.out.println("is service running FIRST LINE !!!!!!!!!!!!!!!!!!!!!!!!!!! " + isServiceRunning(LocationUpdateForegroundService.class) + " ");
            if (!isServiceRunning(LocationUpdateForegroundService.class))
            {
                System.out.println("THIS WILL GO INSIDE ");
                if (isTodayPunchINDone() && !isTodayPunchOutDone()) {


                    if (serviceIsRunning(LocationUpdateForegroundService.class)) {
                        Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
                        context.stopService(lintent);
                    }









                    context.startService(new Intent(context, LocationUpdateForegroundService.class));

                    showOverLasysSettingsScreenCall();
                    System.out.println("is service running " + isServiceRunning(LocationUpdateForegroundService.class) + " ");
                } else {
                    try {

                    } catch (Exception e) {
                        System.out.println("this is exception on unregister reciver ");
                    }
                }
        }
            else{
                System.out.println("NO NEED TO START SERVICE AS IT ALREADY RUNNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (Exception e) {
            System.out.println("error in to bradcast reciver ");
            e.printStackTrace();
        }*/

        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!  ");
        try {
            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                context.startService(new Intent(context, LocationUpdateForegroundService_u.class));

                showOverLasysSettingsScreenCall();
                System.out.println("is service running "+isServiceRunning(LocationUpdateForegroundService_u.class)+" ");
            }
            else{
                try {

                }catch (Exception e)
                {
                    System.out.println("this is exception on unregister reciver ");
                }
            }
        } catch (Exception e) {
            System.out.println("error in to bradcast reciver ");
            e.printStackTrace();
        }
        ;
    }
    private boolean serviceIsRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void showOverLasysSettingsScreenCall() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (!isServiceRunningOverlay(OverLayTrackingService.class))) {
//            showOverlay();
//        } else {
//            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
//                launchMainService();
//            }
//
//        }

        if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunningOverlay(OverLayTrackingService.class))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showOverlay();
            } else {
                launchMainService();
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showOverlay() {

        if (Settings.canDrawOverlays(context)) {

            // Launch service right away - the user has already previously granted permission
            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                launchMainService();
            }

        } else {

            // Check that the user has granted permission, and prompt them if not
            checkDrawOverlayPermission();
        }
    }

    private void launchMainService() {

        Intent svc = new Intent(context, OverLayTrackingService.class);
        context.stopService(svc);
        context.startService(svc);


    }

    public final static int REQUEST_CODE = 10101;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {

        // Checks if app already has permission to draw overlays
        if (!Settings.canDrawOverlays(context)) {

            // If not, form up an Intent to launch the permission request
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));

            // Launch Intent, with the supplied request code
            //   context.  start(intent, REQUEST_CODE);
        }
    }

    private boolean isTodayPunchINDone() {
        if (TextUtils.isEmpty(getSharedPref.getUserPunchInDate())) {
            return false;
        } else {
            return true;
        }

    }


    public boolean isTodayPunchOutDone() {
        if (TextUtils.isEmpty(getSharedPref.getUserPunchOutDate())) {
            return false;
        } else {
            return true;
        }

    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(Integer.MAX_VALUE);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i
                    .next();

            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {

                if (runningServiceInfo.foreground) {
                    Log.e("runbrod", "service run in foreground");
                    serviceRunning = true;
                    break;
                    //service run in foreground

                }
            }
        }
        return serviceRunning;
    }


    private boolean isServiceRunningOverlay(Class<?> serviceClass) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(Integer.MAX_VALUE);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i
                    .next();

            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {
                serviceRunning = true;
                break;

            }
        }
        return serviceRunning;
    }

}

