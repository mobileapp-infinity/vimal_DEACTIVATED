package com.infinity.kich.Leave.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;


import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infinity.kich.Leave.Activity.MainActivity;
import com.infinity.kich.Leave.App.Config;
import com.infinity.kich.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    public static final int FLAG_UPDATE_CURRENT = 1;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    private com.infinity.kich.Leave.Service.NotificationUtils notificationUtils;
    public static final String NOTIF_CHANNEL_ID = "my_channel_02";


//    @Override
//    public void onNewToken(String mToken)
//    {
//        super.onNewToken(mToken);
//        System.out.println("mToken = " + mToken);
//        Log.e("TOKEN===",mToken);
//    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createNotifChannel(this);
        }


        Log.e(TAG, "From11: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)
        {
            System.out.println("remoteMessage ::::"+remoteMessage);
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            System.out.println("messege from firebase ::::::::::: "+remoteMessage.getData());

            try
            {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject json = new JSONObject(String.valueOf(remoteMessage.getData()));
                handleDataMessage(json);
            }
            catch (Exception e)
            {
                Log.e(TAG, "Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // for version over oreo
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotifChannel(MyFirebaseMessagingService myFirebaseMessagingService)
    {
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,
                "MyApp events", NotificationManager.IMPORTANCE_HIGH);
        // Configure the notification channel
        channel.setDescription("MyApp event controls");
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        Log.d(TAG, "createNotifChannel: created=" + NOTIF_CHANNEL_ID);

//        RemoteMessage remoteMessage1 = null;
//        sendNotification(remoteMessage1.getData());

    }
    final int icon = R.drawable.notify_launcher;
    private void handleNotification(String message, String title)
    {

        System.out.println("handleNotification call ****************************************");

        if (!com.infinity.kich.Leave.Service.NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            System.out.println("In handle notification>>>>>>>>>" + message);
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

// Attach the intent to a pending intent
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1410, intent, PendingIntent.FLAG_UPDATE_CURRENT);




        /*    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("type", "notification");
            intent.setAction("dummy_action_" + 1410);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                    intent, PendingIntent.FLAG_ONE_SHOT);*/

//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
//                    intent, PendingIntent.FLAG_UPDATE_CURRENT);


            // play notification sound
            com.infinity.kich.Leave.Service.NotificationUtils notificationUtils = new com.infinity.kich.Leave.Service.NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.logo)
                    .setSmallIcon(R.drawable.notify_launcher)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                    .setAutoCancel(true)
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId(NOTIF_CHANNEL_ID);

            Notification summaryNotification =
                    new NotificationCompat.Builder(getApplicationContext(),NOTIF_CHANNEL_ID)
                            .setContentTitle(title)
                            //set content text to support devices running API level < 24
                            .setContentText(message)
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.drawable.notify_launcher)
                            //build summary info into InboxStyle template
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine(title)
                                    .addLine(message)
                                    .setBigContentTitle(title)
                                    .setSummaryText(message))
                            //specify which group this notification belongs to
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                            .build();
            System.out.println("MessageArrived>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1410,notificationBuilder.getNotification());


        }
        else
            {
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");

//            Intent intent = new Intent(getApplicationContext(), CerificateActivity.class);


        /*    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("type", "notification");
            intent.setAction("dummy_action_" + 1410);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


            final PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
*/


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

// Attach the intent to a pending intent
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1410, intent, PendingIntent.FLAG_UPDATE_CURRENT);



          /*  Notification summaryNotification =
                    new NotificationCompat.Builder(getApplicationContext(),NOTIF_CHANNEL_ID )
                            .setContentTitle(title)
                            //set content text to support devices running API level < 24
                            .setContentText(message)
                            .setContentIntent(resultPendingIntent)
                            .setSmallIcon(R.drawable.launcher)

                            //build summary info into InboxStyle template
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine(title)
                                    .addLine(message)
                                    .setBigContentTitle(title)
                                    .setSummaryText(message))
                            //specify which group this notification belongs to
                            .setGroup(GROUP_KEY_WORK_EMAIL)
                            //set this notification as the summary for the group
                            .setGroupSummary(true)
                            .build();*/
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notify_launcher)
//                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .setGroupSummary(true)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                    .setAutoCancel(true)
                    .setChannelId(NOTIF_CHANNEL_ID);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1410, notificationBuilder.getNotification());
        }
    }

    private void handleDataMessage(JSONObject json)
    {
        Log.e(TAG, "push json: " + json.toString());

        try
        {
            // JSONObject data = json.getJSONObject("notification");
            //JSONObject data = json.getJSONObject("body");
            JSONObject data = json.getJSONObject(json.toString());
            String message = data.getString("body");
            String title = data.getString("title");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);

            if (!com.infinity.kich.Leave.Service.NotificationUtils.isAppIsInBackground(getApplicationContext()))
            {

                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


                final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + this.getPackageName() + "/raw/notification");

//                Intent intent = new Intent(getApplicationContext(), CerificateActivity.class);


              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("type", "notification");
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                        intent, PendingIntent.FLAG_ONE_SHOT);*/


//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
//                        intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

// Attach the intent to a pending intent
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1410, intent, PendingIntent.FLAG_UPDATE_CURRENT);



                // play notification sound
                com.infinity.kich.Leave.Service.NotificationUtils notificationUtils = new com.infinity.kich.Leave.Service.NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

              /*  Notification summaryNotification =
                        new NotificationCompat.Builder(getApplicationContext(),NOTIF_CHANNEL_ID )
                                .setContentTitle(title)
                                //set content text to support devices running API level < 24
                                .setContentText(message)
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.launcher)
                                //build summary info into InboxStyle template
                                .setStyle(new NotificationCompat.InboxStyle()
                                        .addLine(title)
                                        .addLine(message)
                                        .setBigContentTitle(title)
                                        .setSummaryText(message))
                                //specify which group this notification belongs to
                                .setGroup(GROUP_KEY_WORK_EMAIL)
                                //set this notification as the summary for the group
                                .setGroupSummary(true)
                                .build();*/
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                        NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.logo)
                        .setSmallIcon(R.drawable.notify_launcher)
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .setGroupSummary(true)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setChannelId(NOTIF_CHANNEL_ID);
                System.out.println("MessageArrived>>>");
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1410,notificationBuilder.getNotification());

                // play notification sound
              /*  NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();*/
            }
            else
                {

                final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + this.getPackageName() + "/raw/notification");
                // app is in background, show the notification in notification tray
               /* Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                resultIntent.setAction("dummy_action_" + 1410);
                final PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);*/

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

// Attach the intent to a pending intent
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1410, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                        NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.logo)
                        .setSmallIcon(R.drawable.notify_launcher)
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        .setGroupSummary(true)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                        .setAutoCancel(true)
                        .setChannelId(NOTIF_CHANNEL_ID);


               /* Notification summaryNotification =
                        new NotificationCompat.Builder(getApplicationContext(),NOTIF_CHANNEL_ID )
                                .setContentTitle(title)
                                //set content text to support devices running API level < 24
                                .setContentText(message)
                                .setContentIntent(resultPendingIntent)

                                .setSmallIcon(R.drawable.launcher)
                                //build summary info into InboxStyle template
                                .setStyle(new NotificationCompat.InboxStyle()
                                        .addLine(title)
                                        .addLine(message)
                                        .setBigContentTitle(title)
                                        .setSummaryText(message))
                                //specify which group this notification belongs to
                                .setGroup(GROUP_KEY_WORK_EMAIL)
                                //set this notification as the summary for the group
                                .setGroupSummary(true)
                                .build();*/

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1410,notificationBuilder.getNotification());

                // check for image attachment
                /*if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }*/
            }
        }
        catch (JSONException e)
        {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent)
    {
        notificationUtils = new com.infinity.kich.Leave.Service.NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl)
    {
        notificationUtils = new com.infinity.kich.Leave.Service.NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


}
