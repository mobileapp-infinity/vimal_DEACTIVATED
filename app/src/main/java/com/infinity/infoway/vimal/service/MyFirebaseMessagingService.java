package com.infinity.infoway.vimal.service;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infinity.infoway.vimal.activity.Activity_Home;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.util.common.NotificationUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private DBConnector dbHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.e("REMOTE MESSAGE ", remoteMessage.getNotification() + "");
            handleNotification(remoteMessage.getNotification().getBody());


//            handleNotification(remoteMessage.getNotification().getBody());
        }


        if (remoteMessage.getData().size() > 0) {

            try {
                System.out.println("remoteMessage:  " + remoteMessage.getData().toString());
//                String s="{\n" +
//                        "\t\"attach_file\": \"\",\n" +
//                        "\t\"icon\": \"\",\n" +
//                        "\t\"name\": \"DESC\",\n" +
//                        "\t\"timestamp\": \"2019-07-18 03:01:11\",\n" +
//                        "\t\"title\": \"thisistitle\",\n" +
//                        "\t\"big_icon\": \"\"\n" +
////                        "}";
//                String s="{\n" +
//                        "\t\"attach_file\": \"https://loremflickr.com/cache/resized/65535_40672624793_4f9b033270_z_640_360_nofilter.jpg\",\n" +
//                        "\t\"icon\": \"\",\n" +
//                        "\t\"name\": \"DESC\",\n" +
//                        "\t\"timestamp\": \"2019-07-18 03:01:11\",\n" +
//                        "\t\"title\": \"thisistitle\",\n" +
//                        "\t\"big_icon\": \"https://via.placeholder.com/300/09f/fff.png%20C/O%20https://placeholder.com/\"\n" +
//                        "}";
                // JSONObject json = new JSONObject(remoteMessage.getData().toString());
                //    JSONObject json = new JSONObject(s+"");
                //  handleDataMessage(json);

//                attach_file = remoteMessage.getData().get("attach_file");
//                String icon = "";
//                icon = remoteMessage.getData().get("icon");
//                String name = "";
//                name = remoteMessage.getData().get("name");
//                String timestamp = "";
//                timestamp = remoteMessage.getData().get("timestamp");
//                String title = "";
//                title = remoteMessage.getData().get("title");
//                String big_icon = "";
//                big_icon = remoteMessage.getData().get("big_icon");
                //   String today_date = simpleDateFormat.format(new Date());

                handleDataMessage( remoteMessage);
//            } catch (JSONException e) {
//                Log.e(TAG, "JSON Exception: " + e.getMessage());
//                e.printStackTrace();
//            }
            }
            catch (Exception e) {
                Log.e(TAG, "Exception22222222: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            getSharedPref = new SharedPref(getApplicationContext());
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            if (notificationUtils != null) {
                notificationUtils.playNotificationSound();
            }
        } else {
            // If the app is in background, firebase itself handles the notification
            System.out.println("THIS  IS APP IN BACKGROUND!!!!!!!!!!!!!!!!!!!!!");
            Log.e(" background", message+"");
        }
    }

    //    private void handleDataMessage(JSONObject json) {
    private void handleDataMessage(RemoteMessage remoteMessage) {


        try {

            //JSONObject data = json.getJSONObject("data");
//            String imageUrl = "", title = "", message = "", timestamp = "", action_flag = "0", big_icon = "", msg_type = "", expiry = "",attach_file="";
            String imageUrl = "", title = "", message = "", timestamp = "", action_flag = "", big_icon = "", msg_type = "", expiry = "",attach_file="";
            try {
//                if (json.has("icon") && json.getString("icon") != null && json.getString("icon").length() > 0) {
//                    imageUrl = json.getString("icon");
//                }
//                if (json.has("title") && json.getString("title") != null && json.getString("title").length() > 0) {
//                    title = json.getString("title");
//                }
//                if (json.has("name") && json.getString("name") != null && json.getString("name").length() > 0) {
//                    message = json.getString("name");
//                }
//                if (json.has("timestamp") && json.getString("timestamp") != null && json.getString("timestamp").length() > 0) {
//                    timestamp = json.getString("timestamp");
//                }
//                if (json.has("action_flag") && json.getString("action_flag") != null && json.getString("action_flag").length() > 0) {
//                    action_flag = json.getString("action_flag");
//                }
//                if (json.has("big_icon") && json.getString("big_icon") != null && json.getString("big_icon").length() > 0) {
//                    big_icon = json.getString("big_icon");
//                }
//                if (json.has("msg_type") && json.getString("msg_type") != null && json.getString("msg_type").length() > 0) {
//                    msg_type = json.getString("msg_type");
//                }
//                if (json.has("expiry") && json.getString("expiry") != null && json.getString("expiry").length() > 0) {
//                    expiry = json.getString("expiry");
//                }if (json.has("attach_file") && json.getString("attach_file") != null && json.getString("attach_file").length() > 0) {
//                    attach_file = json.getString("attach_file");
//                }

                attach_file = remoteMessage.getData().get("attach_file");
                //    String icon = "";
                imageUrl = remoteMessage.getData().get("icon");
                //   imageUrl = remoteMessage.getData().get("big_icon");

                message = remoteMessage.getData().get("name");

                timestamp = remoteMessage.getData().get("timestamp");

                title = remoteMessage.getData().get("title");

                big_icon = remoteMessage.getData().get("big_icon");


            } catch (Exception ignored) {
                System.out.println("issue in read tags  ");
            }

            try {
                getSharedPref = new SharedPref(getApplicationContext());
                DBConnector dbConnector = new DBConnector(getApplicationContext());
                dbConnector.deleteRecentNotification();
                boolean
                        flag = dbConnector.insertDataNotificationMaster(title, message, imageUrl, timestamp, expiry,attach_file,big_icon,getSharedPref.getRegisteredUserId());
                dbConnector.close();
            }
            catch (Exception e)
            {
                System.out.println("this is error in noti table insert ");
                e.printStackTrace();
            }

            // boolean isBackground = data.getBoolean("icon");

            /*try{
                imageUrl = "https://api.androidhive.info//images//minion.jpg";
            }catch (Exception ex){}*/


//            String timestamp = data.getString("name");
            // JSONObject payload = data.getJSONObject("name");


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                /*Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();*/
                getSharedPref = new SharedPref(getApplicationContext());

                Intent resultIntent = new Intent(getApplicationContext(), Activity_Home.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    System.out.println("this is imageurl NOT isAppIsInBackground  imageUrl>>>>>>>>>>>>>>>>>   "+imageUrl+"");
                    System.out.println("PRINT ALL PARMS  ");
                    System.out.println("title  "+title+"");
                    System.out.println("message  "+message+"");
                    System.out.println("timestamp  "+timestamp+"");
                    System.out.println("action_flag  "+action_flag+"");
                    System.out.println("big_icon  "+big_icon+"");
                    System.out.println("expiry  "+expiry+"");
                    System.out.println("attach_file  "+attach_file+"");
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent, action_flag, big_icon, msg_type, expiry,attach_file);

                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent, action_flag, big_icon, msg_type, expiry,attach_file);
                } else {
                    // image is present, show notification with image
                    System.out.println("this is title  NOT isAppIsInBackground "+title+"");
                    System.out.println("this is message "+message+"");
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl, action_flag, big_icon, msg_type, expiry,attach_file);
                }


            } else {

//                Bitmap bitmap=getBitmapfromUrl(imageUrl);
//                sendNotification(title,bitmap,message);

                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), Activity_Home.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    System.out.println("this is imageurl>>>>>>>>>>>>>>>>>   "+imageUrl+"");
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent, action_flag, big_icon, msg_type, expiry,attach_file);
                } else {
                    // image is present, show notification with image
                    System.out.println("this is title "+title+"");
                    System.out.println("this is message "+message+"");
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl, action_flag, big_icon, msg_type, expiry,attach_file);
                }

               /* try{
                    if(imageUrl!=null && imageUrl.length()>0){
                        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                        Bitmap bitmap = notificationUtils.getBitmapFromURL(imageUrl);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        dbHelper=new DBConnector(MyFirebaseMessagingService.this);
                        dbHelper.insertDataNotificationMaster(title,message,byteArray,timestamp,expiry,msg_type);
                        //dbHelper.close();
                    }else{
                        dbHelper=new DBConnector(MyFirebaseMessagingService.this);
                        byte[] byteArray = new byte[1];
                        dbHelper.insertDataNotificationMaster(title,message,byteArray,timestamp,expiry,msg_type);
                       // dbHelper.close();
                    }
                }catch (Exception ex){}*/
            }
        } catch (Exception e) {
            //Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
    private SharedPref getSharedPref;
    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, String action_flag, String big_icon, String message_type, String expiry,String attach_file) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, action_flag, big_icon, message_type,attach_file);

        byte[] empty = new byte[1];

        //================================================
//        DBConnector dbConnector = new DBConnector(context);
//        dbConnector.deleteRecentNotification();
//        try {
//            boolean flag = dbConnector.insertDataNotificationMaster(title, message, "", timeStamp, expiry,attach_file,big_icon,getSharedPref.getRegisteredUserId());
//            dbConnector.close();
//        }
//        catch (Exception e)
//        {
//            System.out.println("this is error in noti table ");
//        }
        //   Log.e("result",""+flag);
        //    Log.e("data insert",""+flag);

    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl, String action_flag, String big_icon, String message_type, String expiry,String attach_file) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl, action_flag, big_icon, message_type);

        try {
            if (imageUrl != null && imageUrl.length() > 0) {
                       /* Bitmap bitmap = notificationUtils.getBitmapFromURL(imageUrl);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();*/
                DBConnector dbConnector = new DBConnector(context);
                dbConnector.deleteRecentNotification();
                //   boolean flag = dbConnector.insertDataNotificationMaster(title, message, imageUrl, timeStamp, expiry, attach_file,big_icon,getSharedPref.getRegisteredUserId()+"");
                dbConnector.close();

//                        Log.e("result",""+flag);
                //dbHelper.close();
            } else {
//                        byte[] byteArray = new byte[1];
                DBConnector dbConnector = new DBConnector(context);
                dbConnector.deleteRecentNotification();
                //   boolean flag = dbConnector.insertDataNotificationMaster(title, message, " ", timeStamp, expiry,attach_file,big_icon,getSharedPref.getRegisteredUserId()+"");
                dbConnector.close();

//                        Log.e("result",""+flag);
                // dbHelper.close();
            }
        } catch (Exception ignored) {
        }

    }


}
