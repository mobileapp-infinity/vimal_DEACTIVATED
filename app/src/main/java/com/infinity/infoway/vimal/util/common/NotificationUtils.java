package com.infinity.infoway.vimal.util.common;

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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Home;
import com.infinity.infoway.vimal.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.infinity.infoway.vimal.config.Config.NOTIFICATION_ID;


public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private final Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent, String action_flag, String big_icon, String message_type) {
        showNotificationMessage(title, message, timeStamp, intent, null,action_flag,big_icon,message_type);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl, String action_flag, String big_icon, String message_type) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

      /*  final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");
*/
        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent,action_flag,big_icon,message_type);
                } else {
                    showSmallNotification(icon, title, message, timeStamp, action_flag,big_icon,message_type);
                }
            }
        } else {
            //showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            showSmallNotification(icon, title, message, timeStamp, action_flag,big_icon,message_type);
           // playNotificationSound();
        }
    }

    //, Uri alarmSound

    private void showSmallNotification(int icon, String title, String message, String timeStamp, String action_flag, String big_icon, String message_type) {

       /* NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();



        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);*/

        PendingIntent pendingYesIntent=null,pendingNoIntent=null;


        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder;
        if(big_icon!=null && big_icon.length()>0){
            Bitmap bitmap_big_icon = getBitmapFromURL(big_icon);
            mBuilder = new NotificationCompat.Builder(
                    mContext)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSound(soundUri)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setLargeIcon(bitmap_big_icon)
                    .setAutoCancel(true)
                    .setPriority(2)
                    .setTicker(title)
                    .setContentText(message);
        }else{
            mBuilder = new NotificationCompat.Builder(
                    mContext)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSound(soundUri)
                    .setPriority(2)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setAutoCancel(true)
                    .setTicker(title)
                    .setContentText(message);
        }



        /*NotificationCompat.InboxStyle bigPicStyle = new NotificationCompat.InboxStyle();
        bigPicStyle.addLine(Html.fromHtml(message));
        mBuilder.setStyle(bigPicStyle);*/

        // actvity define when click on notification
        Intent resultIntent = new Intent(mContext, Activity_Home.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext, NOTIFICATION_ID, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);



        NotificationManager mNotificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());





    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, String action_flag, String big_icon, String message_type) {


        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;

        if(big_icon!=null && big_icon.length()>0){

            Bitmap bitmap_big_icon = getBitmapFromURL(big_icon);

            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(soundUri)
                    .setStyle(bigPictureStyle)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                    .setPriority(2)
                    .setLargeIcon(bitmap_big_icon)
                    .setContentText(message)
                    .build();
        }else{
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(soundUri)
                    .setStyle(bigPictureStyle)
                    .setWhen(getTimeMilliSec(timeStamp))
                    .setPriority(2)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();
        }



        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);

    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
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

    // Playing notification sound
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

    /**
     * Method checks if the app is in background or not
     */
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

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }

    private static long getTimeMilliSec(String timeStamp) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
