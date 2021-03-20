package com.infinity.infoway.vimal.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;


import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Home;
import com.infinity.infoway.vimal.database.SharedPref;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class NotificationUtils {

    private static String TAG = com.infinity.infoway.vimal.service.NotificationUtils.class.getSimpleName();
    SharedPref getSharedPref;
    private final Context mContext;
    String eventId = "";

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        getSharedPref = new SharedPref(mContext);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl, String action_flag, String big_icon, String message_type) {
        // Check for empty push message
        System.out.println("TextUtils.isEmpty(message) showNotificationMessage " + message + "");
        System.out.println("TextUtils.isEmpty(message) showNotificationMessage imageUrl " + imageUrl + "");
        if (TextUtils.isEmpty(message))
            return;
        if (message.contains("$")) {
            eventId = message.split("\\$")[1];
        }

        // notification icon
//        final int icon = R.drawable.ic_action_logo_small;
        final int icon = R.drawable.noti_small;


//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        final PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        mContext,
//                        0,
//                        intent,
//                        PendingIntent.FLAG_CANCEL_CURRENT
//                );

//        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                mContext);

      /*  final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");
*/
        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
//                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent,action_flag,big_icon,message_type);
                    showSmallNotification(icon, title, message, timeStamp, action_flag, big_icon, message_type);

                } else {

                    showSmallNotification(icon, title, message, timeStamp, action_flag, big_icon, message_type);
                }
            }
        } else {
            //showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            System.out.println("this is small notification display ::::::::::::::::::::   ");
            showSmallNotification(icon, title, message, timeStamp, action_flag, big_icon, message_type);
            // playNotificationSound();
        }
    }

    //, Uri alarmSound
    public static final String NOTIFICATION_CHANNEL_ID_FOR_SERVICE_CLASS = "channel_01";
    public static final int NOTIFICATION_ID_FOR_SERVICE_CLASS_FOR_SERVICE_CLASS = 123; // Added by remish to make notification channel id same for all notification of notification service class

    // so we can remove at perticular time
//    private static final String CHANNEL_NAME = "kitch_notification_channel";
//    NotificationChannel channel;
    private void showSmallNotification(int icon, String title, String message, String timeStamp, String action_flag, String big_icon, String message_type) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        System.out.println("HELLO 11111111111111111111111");
        NotificationCompat.Builder mBuilder = null;
        if (big_icon != null && big_icon.length() > 0) {
            Bitmap bitmap_big_icon = getBitmapFromURL(big_icon);

            mBuilder = new NotificationCompat.Builder(mContext)
                    .setContentText(message)
                    .setContentTitle(title)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.noti_small)
                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setSmallIcon(R.drawable.ic_action_logo_small_u)
                    .setTicker(title)
                    .setLargeIcon(bitmap_big_icon)
                    .setWhen(System.currentTimeMillis());

            // Set the Channel ID for Android O.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID_FOR_SERVICE_CLASS); // Channel ID
            }
        } else {
            try {
                System.out.println(" 222222222222222222222222222222222222");

                //Added by remish for tasting purpose only please remove below pending intent line before live as per requirement or add condition as per requirement
/**24-10-2020 pragna for check normal notification of for public event*/
               // Intent intent = new Intent(mContext, Notification_Handle.class);
                Intent intent =new Intent();
//                if (message.contains("$")) {
//                    intent = new Intent(mContext, Notification_Handle.class);
//                }// Here pass your activity where you want to redirect.
//                else {
                    if (getSharedPref.getEMP_ID() != null && !getSharedPref.getEMP_ID().contentEquals("") && getSharedPref.getEMP_ID().compareToIgnoreCase("null") != 0 && !getSharedPref.getEMP_ID().contentEquals("0")) {
                        intent = new Intent(mContext, Activity_Home.class);

//                    else {
//                        intent = new Intent(mContext, Customer_Home_page.class);
//                    }
                        //}
                        // intent.putExtra(INTENT_CONS_PUBLIC_EVENT_ID, eventId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent publicEventPendingIntent = PendingIntent.getActivity(mContext, (int) (Math.random() * 100), intent, 0);

                        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.noti_small);
//                PendingIntent activityPendingIntent = PendingIntent.getActivity(mContext, 0,
//                        new Intent(mContext, com.infinity.kich.Activity.MainActivity.class), 0);
                        mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID_FOR_SERVICE_CLASS)
                                .setContentText(message + "")
                                .setContentTitle(title + "")
                                .setOngoing(true)
                                .setPriority(Notification.PRIORITY_HIGH)
//                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentIntent(publicEventPendingIntent)
                                .setSmallIcon(R.drawable.noti_small)
                                .setTicker(message)
                                .setDefaults(Notification.FLAG_AUTO_CANCEL)
                                .setLargeIcon(largeIcon)
                                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/" + R.raw.notification))
                                .setColor(Color.parseColor("#DF2227"))
                                .setWhen(System.currentTimeMillis());

                        // Set the Channel ID for Android O.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID_FOR_SERVICE_CLASS); // Channel ID
                        }
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

//        Intent resultIntent = new Intent(mContext, MainActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(
//                mContext, NOTIFICATION_ID_FOR_SERVICE_CLASS, resultIntent, 0);
//        mBuilder.setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mNotificationManager.createNotificationChannel(channel);
//        }

        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID_FOR_SERVICE_CLASS_FOR_SERVICE_CLASS, mBuilder.build());

    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            assert am != null;
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            assert am != null;
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
