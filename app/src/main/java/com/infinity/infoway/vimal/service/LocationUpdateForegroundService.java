package com.infinity.infoway.vimal.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Home;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.Request_GPS_Internet_Bgservice;
import com.infinity.infoway.vimal.api.request.Request_Insert_Location_Sync;
import com.infinity.infoway.vimal.api.response.Connection_on_off_notificationResponse;
import com.infinity.infoway.vimal.api.response.GPS_Internet_BgserviceResponse;
import com.infinity.infoway.vimal.api.response.InsertLocationSyncResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.model.InternetMasterBean;
import com.infinity.infoway.vimal.model.ServiceMasterBean;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;
import com.infinity.infoway.vimal.util.common.LocationUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationUpdateForegroundService extends Service {

    public LocationUpdateForegroundService() {
    }

    private Handler mHandler = new Handler();

    private static final String PACKAGE_NAME =
            "com.infinity.infoway.vimal.service.updatedata";

    private static final String TAG = LocationUpdateForegroundService.class.getSimpleName();

    private DBConnector dbConnector;

    /**
     * The name of the channel for notifications.
     */
    private static final String CHANNEL_ID = "channel_01";

    public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";

    public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";

    private final IBinder mBinder = new LocalBinder();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.600000
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60000;


    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final int NOTIFICATION_ID = 12345678;
    private static final int NOTIFICATION_ID_DUMMY = 52345670;

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    private NotificationManager mNotificationManager;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    public FusedLocationProviderClient mFusedLocationClient;

    /**
     * Callback for changes in location.
     */
    public LocationCallback mLocationCallback;

    public Handler mServiceHandler;

    /**
     * The current location.
     */
    public static Location mLocation;

    //


    private LocationManager manager;
    private boolean statusOfGPS;
    private IntentFilter ifilter;
    private Intent batteryStatus;
    private SimpleDateFormat sdf;
    private Date today;
    private String[] LocationArray;
    private String LocationAddress = "";
    private ConnectionDetector cd;
    File dir;


    // LocationData Sending

    private SharedPref getSharedPref;
    private List<GPSMasterBean> gpsMasterBeanList;
    private int loopCounter = 0;
    private String minLastUpdatedRecordId, maxLastUpdatedRecordId;
    private long Status;
    private ApiInterface apiService;//ApiClient_duplicate apiService_local;

   /* public static final String
            LOCK_NAME_STATIC = "com.infinity.infoway.davat.service.LocationUpdateForegroundService.Static";
    ;
    public static final String
            LOCK_NAME_LOCAL = "com.infinity.infoway.davat.service.LocationUpdateForegroundService.Local";*/

    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private ActivityManager activityManager;

    @Override
    public void onCreate() {

        try {
            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:mywakelogtagfj");
            wl.acquire();
        } catch (Exception ex) {
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                try {
                    if (locationResult.getLastLocation() != null && locationResult.getLastLocation().getLatitude() > 0) {
                        // onNewLocation(locationResult.getLastLocation());
                    }
                } catch (Exception ex) {
                }

            }

        };

        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        cd = new ConnectionDetector(this);
        getSharedPref = new SharedPref(this);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dbConnector = new DBConnector(this);


        createLocationRequest();

        /*
          Create log file  if not exiest
         */

        try {
            sdf_full_with_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
            dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (Exception e) {
            System.out.println("error in create file ");
        }

//        logFile = new File(dir, "E_track_location_log.txt");
        logFile = new File(dir, "vimal_location_log.txt");

        System.out.println("THIS IS PATH " + logFile.getAbsolutePath() + "");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification);
            /**edited : 4-nov-19 by pragna for making notification silent */
          /*  CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);*/
//            CharSequence name = getString(R.string.app_name);
            CharSequence name = getString(R.string.app_name);
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW);
            mChannel.enableVibration(true);
            mChannel.enableLights(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()

                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(soundUri, audioAttributes);


            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);

        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(intent!=null) {
        boolean startedFromNotification = false;
//        if (null != intent && null != intent.getAction()) {
        if (null != intent) {
            Log.i(TAG, "Service started intent not null!!!!!!!!!!!@@@@@@@@@@@");
            startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,
                    false);
        }
        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            removeLocationUpdates();
            stopSelf();
        }
        System.out.println("isTodayPunchINDone() && !isTodayPunchOutDone() " + isTodayPunchINDone() + " isTodayPunchOutDone " + isTodayPunchOutDone() + "");
        if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
            startTimer();
            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.P) {
                Toast.makeText(this, "Service started...!\nPlease Don't close app! ", Toast.LENGTH_LONG).show();
            }
        }
        /*https://stackoverflow.com/questions/53128552/oppo-vivo-app-kill-notification-not-coming-in-android-fcm?noredirect=1&lq=1*/
        //  System.out.println("this is null!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@");
        // Tells the system to not try to recreate the service after it has been killed.
        return Service.START_STICKY;
//        return Service.START_NOT_STICKY;
//        return Service.START_REDELIVER_INTENT;


        //  return 0;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "Task Removed");
        writeDataInLogFile("on task removed ", " on task removed ");
        super.onTaskRemoved(rootIntent);

        Log.i(TAG, "Task Removed 111");
        try {
//              Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
//              sendBroadcast(broadcastIntent);
           /* stoptimertask();
            removeLocationUpdates();*/

            // Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
            // sendBroadcast(broadcastIntent);
//            Toast.makeText(this, "DON'T CLOSE APP", Toast.LENGTH_SHORT).show();
            getLastLocation();
            Intent nextIntent = new Intent(this, Activity_Home.class);
            //   nextIntent.putExtra(EXTRA_TEXT, "Hello!");
            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                System.out.println("Activity_Home.FLAG_4_BACK_START_PG_AGAIN " + Activity_Home.FLAG_4_BACK_START_PG_AGAIN + "");
                if (Activity_Home.FLAG_4_BACK_START_PG_AGAIN) {
                    System.out.println("this must call");
//                      ProcessPhoenix.triggerRebirth(this, nextIntent);
                }


                writeDataInLogFile("Service onTaskRemoved  ", " TRIED TO RESTART SERVICE FROM ONDESTROY   ");
            } else {
                stoptimertask();
                removeLocationUpdates();
            }
        } catch (Exception e) {
            System.out.println("error in to onTaskRemoved :::::::::");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        // Log.i(TAG, "in onBind()");
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        //Log.i(TAG, "in onRebind()");
        stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Last client unbound from service");

        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && LocationUtil.requestingLocationUpdates(this)) {
            Log.i(TAG, "Starting foreground service onUnbind");

            // TODO(developer). If targeting O, use the following code.


          /*  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                mNotificationManager.startServiceInForeground(new Intent(this,
                        LocationUpdateForegroundService.class), NOTIFICATION_ID, getNotification());
            } else {
                startForeground(NOTIFICATION_ID, getNotification());
            }
*/
            try {
                System.out.println(" isTodayPunchINDone: ->    " + isTodayPunchINDone() + " isTodayPunchOutDone :->  " + isTodayPunchOutDone());
                if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                    System.out.println("from onunbing >>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
                    startForeground(NOTIFICATION_ID, getNotification());

                    /*this is not in OPPO HAVE TO TEST THIS!!!!!!!!!!!!!!!*/

                    Intent nextIntent = new Intent(this, Activity_Home.class);
                    //   nextIntent.putExtra(EXTRA_TEXT, "Hello!");
                    if (Activity_Home.FLAG_4_BACK_START_PG_AGAIN) {
                        //    ProcessPhoenix.triggerRebirth(this, nextIntent);
                    }
                }
            } catch (Exception e) {
                System.out.println("error in to onunbind!!!!!!!!!!!!!");
            }


        }
        return true; // Ensures onRebind() is called when a client re-binds.
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

    //https://stackoverflow.com/questions/19345008/need-code-example-on-how-to-run-an-android-service-forever-in-the-background-eve
  /*  @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(null);
        wl.release();
        stoptimertask();
        removeLocationUpdates();
        Log.i(TAG, "Service Destroyed");
        writeDataInLogFile("Service Destroyed  ", " Service Destroyed   ");
        Log.i(TAG, "WakeLock Destroyed");

        try {
//              Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
//              sendBroadcast(broadcastIntent);
            Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
            stoptimertask();
            writeDataInLogFile("Service Destroyed  ", " TRIED TO RESTART SERVICE FROM ONDESTROY   ");
        } catch (Exception e) {
            System.out.println("error in to destroy :::::::::");
        }
    }
*/

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     */

    public void requestLocationUpdates() {

        if (isTodayPunchINDone() && (!isTodayPunchOutDone())) {//have to check this before submit
            LocationUtil.setRequestingLocationUpdates(this, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent serviceIntent = new Intent(getApplicationContext(), LocationUpdateForegroundService.class);
                ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
                System.out.println("request location!!!!!!!!!!!!!!!!!!!!1111");
                startForeground(NOTIFICATION_ID, getNotification());

            } else {
                System.out.println("request location!!!!!!!!!!!!!!!!!!!!222222222222");
                startService(new Intent(getApplicationContext(), LocationUpdateForegroundService.class));
                startForeground(NOTIFICATION_ID, getNotification());
            }
        }
        try {
            System.out.println("TRYING TO FETCH LOCATION!!!!!!!!!!!!!!! ");
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
            LocationUtil.setRequestingLocationUpdates(this, false);
            //Log.e(TAG, "Lost location prequestLocationUpdatesermission. Could not request updates. " + unlikely);
        }
//        }
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     */
    public void removeLocationUpdates() {

        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            LocationUtil.setRequestingLocationUpdates(this, false);
            stopSelf();
            Log.e(TAG, "Removing location updates....");
        } catch (SecurityException unlikely) {
            LocationUtil.setRequestingLocationUpdates(this, true);
            //Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    private Notification getNotification() {
        Intent intent = new Intent(this, LocationUpdateForegroundService.class);
        CharSequence text = "";
        if (isGPSON() == 0 || !checkPermissions()) {
            text = "Start Location!";
        } else {
            text = LocationUtil.getLocationText(mLocation);
        }
        System.out.println("isGPSON@@ " + isGPSON() + "");
        System.out.println("checkPermissions@@@ " + checkPermissions() + "");
//        String text = LocationUtil.getLocationText(mLocation);
        // String add = GetAddress2(mLocation.getLatitude(), mLocation.getLongitude());
        /*if(add!=null&&!add.contentEquals(""))
        {
            text=add+"";
        }*/
//mLocation.getLatitude();
        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
       /* PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Activity_Splash.class), 0);*/
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Activity_Home.class), 0);
       /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.ic_punchin, getString(R.string.launch_activity),
                        activityPendingIntent)
                .addAction(R.drawable.ic_punchout, getString(R.string.remove_location_updates),
                        servicePendingIntent)
                .setContentText(text)
                .setContentTitle(LocationUtil.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_action_logo_small)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());*/
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.noti_small);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText(text)
                .setContentTitle(LocationUtil.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(activityPendingIntent)
                .setSmallIcon(R.drawable.noti_small)
                .setTicker(text)
                .setLargeIcon(largeIcon)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification))
                .setColor(Color.parseColor("#A7080B"))
                .setWhen(System.currentTimeMillis());

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        return builder.build();
    }

    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private Notification getNotification4LastUpdate(String txt, boolean warning, boolean IsNewNotification) {
        Intent intent = new Intent(this, LocationUpdateForegroundService.class);


        Bitmap largeIcon;
        // CharSequence text = LocationUtil.getLocationText(mLocation);

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Activity_Home.class), 0);

       /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.ic_punchin, getString(R.string.launch_activity),
                        activityPendingIntent)
                .addAction(R.drawable.ic_punchout, getString(R.string.remove_location_updates),
                        servicePendingIntent)
                .setContentText(text)
                .setContentTitle(LocationUtil.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_action_logo_small)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());*/
        NotificationCompat.Builder builder;
        if (warning) {
            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.wrong);

        } else if (IsNewNotification) {
            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.alarm);
        } else {
            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.correct);
        }

        builder = new NotificationCompat.Builder(this)
                .setContentText(txt)
                .setContentTitle(LocationUtil.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.drawable.noti_small)
                .setColor(Color.parseColor("#A7080B"))
                .setTicker(txt)
                .setShowWhen(true)
                //  .setContentIntent(activityPendingIntent)
                .setLargeIcon(largeIcon)
                .setWhen(System.currentTimeMillis());
      /*  PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), Activity_Home.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification  mNotification = new Notification();
        mNotification.tickerText = txt;
        mNotification.icon = R.drawable.noti;



        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotification.setLatestEventInfo(getApplicationContext(), "MusicPlayer",
                txt, pi);
        startForeground(NOTIFICATION_ID, mNotification);*/

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (warning) {
                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                builder.setLights(Color.RED, 3000, 3000);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            }
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        return builder.build();
    }

    private void getLastLocation() {
        try {
            mLocation = null;

            if (checkPermissions()) {
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    mLocation = task.getResult();
                                    System.out.println("this is new location !!!!!!!!!!!!! will affect local db");
                                    onNewLocation(mLocation);
                                    // Log.e(TAG, ""+mLocation.getLatitude());
                                } else {
                                    if (isGPSON() == 0 || !checkPermissions()) {
                                        /*startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please start Location22!!!", true, false));*/

                                        startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please start Location!!", true, false));
                                    } else {
                                        System.out.println("AL OK MUST GET LOCATION!!!!!!!");
                                        requestLocationUpdates();

                                    }
                                    Log.e(TAG, "Failed to get location.");
                                    mLocation = null;
                                    //    getLastLocation();
                                }
                            }
                        });
            } else {

                try {
                    startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please start Location!!!", true, false));
                    //  calcMinForLocationLatLngOff();

                    calcMinForGpsOff();
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // display toastkl;kl;
                            Toast.makeText(getApplicationContext(), "Please Start GPS!!", Toast.LENGTH_LONG).show();

                        }

                    });
                } catch (Exception e) {
                    System.out.println("this is error in to toast!!");
                }

            }
          /*  try {
                mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    mLocation = task.getResult();
                                    onNewLocation(mLocation);
                                    //Log.e(TAG, ""+mLocation.getLatitude());
                                } else {
                                    //Log.e(TAG, "Failed to get location.");
                                    mLocation = null;
                                    onNewLocation(mLocation);
                                }
                            }
                        });
            } catch (SecurityException unlikely) {
                Log.e(TAG, "Lost location permission." + unlikely);
                unlikely.printStackTrace();
            }*/

            /*14-aug Pragna */
           /* mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    if (checkPermissions()) {
                        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                        if (gpsTracker != null) {
                            if (gpsTracker.canGetLocation()) {


                                mLocation = gpsTracker.getLocation();

                                System.out.println("this is test lat!!!!!!!!!!!!***  " + mLocation.getLatitude() + "");
                                System.out.println("this is test long!!!!!!!!!!!!***  " + mLocation.getLongitude() + "");
                                System.out.println("this is test getAccuracy!!!!!!!!!!!!***  " + mLocation.getAccuracy() + "");
                                ;
                                onNewLocation(mLocation);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Start Location service ", Toast.LENGTH_LONG).show();
                        getNotification4LastUpdate("Please start Location!!!", true);
                        mLocation = null;
                    }
                }

            });*/

        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }

//        if (mLocation == null && isGPSON() == 0) {
//        if (mLocation == null || isGPSON(0.0,0.0) == 0) {
        if (mLocation == null && isGPSON() == 0) {
            int unreadNotification = 0;
            Log.e(TAG, "isGPSON Failed to 111");

            if (dbConnector != null) {
                //   if (mLocation!=null&&!mLocation.isFromMockProvider()) {
                batteryStatus = registerReceiver(null, ifilter);

                today = new Date();
                GPSMasterBean data = new GPSMasterBean();

                data.setGPS_Location_Name("unspecified");
                data.setGPS_Latitude("0.0");
                data.setGPS_Longitude("0.0");

                data.setGPS_Address("");
                data.setGPS_COUNTRY_FLAG("0");

                try {
                    data.setGPS_Battery_Percentage(String.valueOf(calculateBatteryPercentage()));
                } catch (Exception ex) {
                }

                if (cd != null) {
                    data.setGPS_Internet_Status(cd.isConnectingToInternet() ? "1" : "0");
                } else {
                    data.setGPS_Internet_Status("0");
                }

                data.setGPS_Status(String.valueOf(isGPSON(0.0, 0.0)));
                //   data.setGPS_Status("0");

                try {
                    data.setGPS_DateTime(sdf.format(today));
                } catch (Exception ex) {
                }

                try {
                    data.setGPS_Accuracy("");
                } catch (Exception ex) {
                }

                data.setGPS_Km_Travelled("0.0");
                data.setGPS_Is_Loc_Changed("0");

                // if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                /*16-aug Pragna*/


                try {
                    //   unreadNotification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId()).size();
                    // today = new Date();
                    /*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    String getLAST_DB_DATA_STORE_TIME = getSharedPref.getLAST_DB_DATA_STORE_TIME();
                    System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
                    System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + getLAST_DB_DATA_STORE_TIME + "");

                    Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(getLAST_DB_DATA_STORE_TIME);
                    Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
                    long diff = CURRENT.getTime() - OLD.getTime();
                    int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                    int hours = (int) (diff / (1000 * 60 * 60));
                    MIN_FOR_LOCAL_STORE = (int) (diff / (1000 * 60));
                    // SEC_FOR_LOCAL_STORE = (int) (diff / (1000));
                } catch (Exception E) {
                    System.out.println("error in to MIN CALCULATION ISSE  !!!!!!!!!!!!!!!!!!!!!");
                    E.printStackTrace();
                }
                System.out.println("THIS IS DIFFERENCE FOR DB@@@@@@@@@@@@@@@@242343 " + MIN_FOR_LOCAL_STORE + " MINIT_API_INTERVAL_IME_FROM_API " + MINIT_API_INTERVAL_IME_FROM_API + "");
//                if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                if (data != null) {
                    //if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
//                    if (MIN_FOR_LOCAL_STORE >= 2) {
                    if (MIN_FOR_LOCAL_STORE >= MINIT_API_INTERVAL_IME_FROM_API) {

//                        dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 1 ");
                        dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 1 ");



                       /* try {

                            System.out.println("this is notifications " + unreadNotification + "");
                            if (unreadNotification > 0) {
                                startForeground(NOTIFICATION_ID, getNotification4LastUpdate("New Notification!!", false,true));
                            }
                        }catch (Exception e)
                        {
                            System.out.println("this is data");
                        }*/


                        //}
                    } else {

                        System.out.println("WILL NOT ALLOW USER TO SAVE DATA1111111111111111 !!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                }
                if (cd != null && cd.isConnectingToInternet()) {
//                    sendInternetConnectionData();


                    writeDataInLogFile(" SENDING LOCATION 2 ", " this is from 22222222222222 ");
                    /*16-aug-19 pragna*/
                    try {
                        // today = new Date();
                        /*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*/
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String currentDateandTime = sdf.format(new Date());
                        String last_API_WAS_CALLED_TIME = getSharedPref.getLAST_API_CALL_DATE();
                        System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
                        System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + last_API_WAS_CALLED_TIME + "");

                        Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(last_API_WAS_CALLED_TIME);
                        Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
                        //  Date today = new Date();


                        long diff = CURRENT.getTime() - OLD.getTime();
                        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                        int hours = (int) (diff / (1000 * 60 * 60));
                        minutes = (int) (diff / (1000 * 60));
                        int seconds = (int) (diff / (1000));

                        System.out.println("THIS IS DIFFERNCE :::::::::::::::: " + minutes + "");
                        //   sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());


                    } catch (Exception E) {
                        System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                        E.printStackTrace();
                    }
//                    if (minutes >= 2) {
                    /*21-Aug-pragna to remove duplicate for error response*/
                    System.out.println("getSharedPref.GET_SHOULD_CALL_API()1111 " + getSharedPref.GET_SHOULD_CALL_API() + "");
                    System.out.println("minutes >= MINIT_API_INTERVAL_IME_FROM_API   " + minutes + " MINIT_API_INTERVAL_IME_FROM_API " + MINIT_API_INTERVAL_IME_FROM_API);
                    if (minutes >= MINIT_API_INTERVAL_IME_FROM_API && getSharedPref.GET_SHOULD_CALL_API()) {

                        sendingLocationData();
                    } else {
                        System.out.println("THIS IS REJECTED FROM LOCAL 2222 !!!!!!!!!!!!!!!!!!!!!!!");
                        writeDataInLogFile(" SENDING LOCATION 2 ", " THIS IS REJECTED FROM LOCAL2222 !!!!!!!!!!!!!!!!!!!!!!! ");


                    }

                }
                //20-june-19 pragna for storing internet is avail or not
                else {
                    try {
                        System.out.println("INTERNET NOT AVAILABLE 2222 ");
                        SimpleDateFormat sdf;
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        today = new Date();
                        InternetMasterBean bean = new InternetMasterBean();
//                        bean.setGPS_Internet_Status(0 + "");
//                        bean.setGPS_DateTime(sdf.format(today));

                        bean.set_Internet_Status(0 + "");
                        bean.setInternet_DateTime(sdf.format(today));
                        bean.setGPS_Accuracy("");
                        bean.setGPS_Status("");

                        dbConnector.addInterNetData(bean, getSharedPref.getRegisteredUserId() + "");

                        /*10-sept-19 pragna for service data*/
                       /* ServiceMasterBean serviceMasterBean = new ServiceMasterBean();
//                        bean.setGPS_Internet_Status(0 + "");
//                        bean.setGPS_DateTime(sdf.format(today));

                        serviceMasterBean.setService_Off_Time(sdf.format(today));
                        serviceMasterBean.setService_Type("1");

//getSharedPref.SET_LAST_INTERNET_STOPED_TIME();
                        getSharedPref.SET_LAST_INTERNET_STOPED_TIME(sdf.format(today) + "");
                        //  dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "",getSharedPref.GET_LAST_INTERNET_STOPED_TIME()+"");
                        dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "", sdf.format(today) + "");*/


                        //startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please restart Internet!", true, false));
                    } catch (Exception e) {
                        System.out.println("error in to data save :::::::::::");
                    }
                }
                /*}
                else {
                    System.out.println("please provide valid data!!!!!!!!!!!!!!!!!!");


                    startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Wrong Location!!", true));
                    try {
                        *//*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*//*
                        // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String currentDateandTime = sdf.format(new Date());


                        getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");
                        writeDataInLogFile("FAKE LOCATION on gps =0  ", "REJECTING FACK LOCATION on gps=0");
                    } catch (Exception E) {
                        System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                    }

                }*/
            }
        }
    }

    private void sendInternetConnectionData() {
        //pppppppppppp    dbConnector.getInternetMasterData(0 + "");
        //20-june-19 pragna for storing internet is avail or not
        System.out.println("NOW INTERNET GET AVAILABLE222::::: ");
        List<InternetMasterBean> data_not_avail_internet = dbConnector.getInternetMasterData(0 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain

        List<InternetMasterBean> data_avail_internet = dbConnector.getInternetMasterData(1 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain
        if (data_not_avail_internet != null) {
            if (data_not_avail_internet.size() > 0) {
                InternetMasterBean bean = data_not_avail_internet.get(0);
//                            System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
                System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getInternet_DateTime() + "");
                ///pppppppppppppppppppppppppppppppppppp    apiforInternet(0 + "", bean.getInternet_DateTime() + "");


                if (data_avail_internet != null) {
                    if (data_avail_internet.size() > 0) {
                        InternetMasterBean internetMasterBean = data_avail_internet.get(0);
//                            System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
                        System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + internetMasterBean.getInternet_DateTime() + "");

//apiforInternet()
                        sendServiceStatus(1);
                        apiforInternet(1 + "", internetMasterBean.getInternet_DateTime() + "", bean.getInternet_DateTime() + "", 1);


                    }
                }
            }
        }


    }

    private void onNewLocation(Location location) {

        mLocation = location;

        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        // Update notification content if running as a foreground service.
        if (serviceIsRunningInForeground(this)) {
            System.out.println("FROM SERVICE IS RUNNING>>>>>>>>>>>>> ");
            mNotificationManager.notify(NOTIFICATION_ID, getNotification());
        }


        if (dbConnector != null && mLocation != null) {

            if (!location.isFromMockProvider()) {
                //  int unreadNotification=0;
                System.out.println("String.valueOf(mLocation.getAccuracy():::::::>>>>> " + String.valueOf(mLocation.getAccuracy() + ""));
                System.out.println("MOCK LOACTION NOT NULL !!!!!!!!!!!!!!!!! " + location.isFromMockProvider() + "");
                System.out.println("GETTIME LOACTION !!!!!!!!!!!!!!!!! " + location.getTime() + "");
                System.out.println("GETTIME getSpeed !!!!!!!!!!!!!!!!! " + location.getSpeed() + "");
//                System.out.println("GETTIME LOACTION !!!!!!!!!!!!!!!!! " + location.getSpeedAccuracyMetersPerSecond() + "");
                batteryStatus = registerReceiver(null, ifilter);

                today = new Date();
                GPSMasterBean data = new GPSMasterBean();
                String address = "";
                data.setGPS_Location_Name("unspecified");
                if (mLocation != null) {
                    data.setGPS_Latitude(String.valueOf(mLocation.getLatitude()));
                    data.setGPS_Longitude(String.valueOf(mLocation.getLongitude()));
                    try {
                        address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude(), data);
                    }
                    catch (Exception e)
                    {
                        System.out.println("error in to address!!");
                    }
                }
                try {
                    // address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude(), data);
                    address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude(), data);

                    if (!TextUtils.isEmpty(address)) {
                        data.setGPS_Address(address);
                    } else {
                        address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude(), data);
                        data.setGPS_Address(address);
                        if (TextUtils.isEmpty(address)) {
                            data.setGPS_Address("");
                        }
                        //  data.setGPS_COUNTRY_NAME("");
                    }
                    if (TextUtils.isEmpty(data.getGPS_COUNTRY_NAME())) {
                        data.setGPS_COUNTRY_NAME("");
                    }
                } catch (Exception ex) {
                    data.setGPS_Address("");
                    data.setGPS_COUNTRY_NAME("");

                    try {
                        address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude(), data);
                        if (!TextUtils.isEmpty(address)) {
                            data.setGPS_Address(address);
                        } else {
                            data.setGPS_Address("");

                        }
                        if (TextUtils.isEmpty(data.getGPS_COUNTRY_NAME())) {
                            data.setGPS_COUNTRY_NAME("");
                        }
                    } catch (Exception ex1) {
                        data.setGPS_Address("");

                        if (TextUtils.isEmpty(data.getGPS_COUNTRY_NAME())) {
                            data.setGPS_COUNTRY_NAME("");
                        }

                    }
                }


                System.out.println("THIS IS COUNTY NAME::::::::::::::::::::::: " + data.getGPS_COUNTRY_NAME() + "");
                if (data.getGPS_COUNTRY_NAME().compareToIgnoreCase("INDIA") == 0) {
                    data.setGPS_COUNTRY_FLAG("1");
                } else {
                    data.setGPS_COUNTRY_FLAG("0");
                }
                try {
                    data.setGPS_Battery_Percentage(String.valueOf(calculateBatteryPercentage()));

                } catch (Exception ex) {
                }

                if (cd != null) {
                    data.setGPS_Internet_Status(cd.isConnectingToInternet() ? "1" : "0");
                } else {
                    data.setGPS_Internet_Status("0");
                }
//error for this nu
                // if(mLocation.getLatitude()!=null&&mLocation.getLatitude()!=null) {
                try {
                    data.setGPS_Status(String.valueOf(isGPSON(mLocation.getLatitude(), mLocation.getLongitude())));
                } catch (Exception ex) {
                    data.setGPS_Status("0");
                }
                try {
                    data.setGPS_DateTime(sdf.format(today));
                } catch (Exception ex) {
                }

                try {
                    data.setGPS_Accuracy(String.valueOf(mLocation.getAccuracy()));
                    System.out.println("this is stored :accuracy :::::::::: " + data.getGPS_Accuracy() + "");
                } catch (Exception ex) {
                }

                //  if ((!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {
                if ((!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {


                    Location priviusLocation = new Location(LocationManager.GPS_PROVIDER);//provider name is unnecessary
                    priviusLocation.setLatitude(Double.parseDouble(getSharedPref.getPreviousLat()));//your coords of course
                    priviusLocation.setLongitude(Double.parseDouble(getSharedPref.getPreviousLong()));

                    Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
                    double distanceInMeters = 0;
                    double distanceInMeters_MANUAL = 0;
                    //  distanceInMeters = currentLocation.distanceTo(priviusLocation);

                    //  distanceInMeters = distanceManual(priviusLocation.getLatitude(), priviusLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude());
                 /*   if (mLocation != null) {
                        currentLocation.setLatitude(mLocation.getLatitude());
                        currentLocation.setLongitude(mLocation.getLongitude());

                        distanceInMeters = currentLocation.distanceTo(priviusLocation);
                    }*/

//                if (distanceInMeters > getSharedPref.getDefaultDistance()) { pragna ####################################  12-july
//                if (distanceInMeters > getSharedPref.getDefaultDistance() && mLocation.getAccuracy() < 100) {
                    /*     if (mLocation != null && mLocation.getLatitude() > 0 && distanceInMeters > getSharedPref.getDefaultDistance() && (mLocation.getAccuracy() < Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {*/
                    /*   if (mLocation != null && mLocation.getLatitude() > 0 && distanceInMeters >= getSharedPref.getDefaultDistance() && (mLocation.getAccuracy() <= Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {*/
                    if (mLocation != null && mLocation.getLatitude() > 0 &&  (mLocation.getAccuracy() <= Config.ACCURACY )) {

                        currentLocation.setLatitude(mLocation.getLatitude());
                        currentLocation.setLongitude(mLocation.getLongitude());
                        getSharedPref.SET_FIRSTTIME_GPS_OFF(false);
                        distanceInMeters_MANUAL = distanceManual(priviusLocation.getLatitude(), priviusLocation.getLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude());

                        distanceInMeters = currentLocation.distanceTo(priviusLocation);
                        if(distanceInMeters_MANUAL>distanceInMeters)
                        {
                            distanceInMeters=distanceInMeters_MANUAL;
                        }
//                if (distanceInMeters > getSharedPref.getDefaultDistance()) {
//                        if (mLocation.getLatitude() > 0) {
                        try {
                            if (distanceInMeters >= getSharedPref.getDefaultDistance() && (mLocation.getAccuracy() <= Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {
                                if (mLocation.getLatitude() > 0) {
                                    getSharedPref.setPreviousLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
                                    writeDataInLogFile("this set to previous ", String.valueOf(mLocation.getLatitude()) + "  getLongitude " + String.valueOf(mLocation.getLongitude()));
                                }
//                            data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
//                            data.setGPS_Is_Loc_Changed("1");
                                System.out.println("FIRST CONDITION " + data.getGPS_Is_Loc_Changed() + " ************************ ");
                                //    }
                                //  data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
                                double d = distanceInMeters / 1000;
//                            data.setGPS_Km_Travelled( distanceInMeters / 1000));
                                data.setGPS_Km_Travelled(d + "");
                                data.setGPS_Is_Loc_Changed("1");
                                System.out.println("FIRST CONDITION " + data.getGPS_Is_Loc_Changed() + " ************************ ");

                            }
                        }
                        catch (Exception e) {
                            writeDataInLogFile("error in to prev data set ", "");
                        }

                    } else {
                        /* writeDataInLogFile("ACCURACY ****** ", mLocation.getAccuracy() + "");*/
                        data.setGPS_Km_Travelled("0.0");
                        data.setGPS_Is_Loc_Changed("0");
                        System.out.println("SECOND CONDITION " + data.getGPS_Is_Loc_Changed() + " ************************ ");
//                    try {
//                        getLastLocation();
//                        writeDataInLogFile(" SECOND CONDITION getLatitude  ", " trying to call again hetlocation ");
//                    } catch (Exception e) {
//                        writeDataInLogFile(" SECOND CONDITION getLatitude  ", " failed to load location else  ");
//                    }


                    }
                } else {
                    data.setGPS_Km_Travelled("0.0");
                    data.setGPS_Is_Loc_Changed("0");
                    System.out.println("THIERD CONDITION " + data.getGPS_Is_Loc_Changed() + " ************************ ");
//
//                if (mLocation.getLatitude() > 0 && mLocation.getAccuracy() < 100) {

                    if (mLocation != null && mLocation.getLatitude() > 0 && mLocation.getAccuracy() < Config.ACCURACY) {
                        writeDataInLogFile("ACCURACY into else ****** ", mLocation.getAccuracy() + "");
//                if (mLocation.getLatitude() > 0) {
                        getSharedPref.setPreviousLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
                        writeDataInLogFile("this set to previous from else  ", String.valueOf(mLocation.getLatitude()) + "  getLongitude " + String.valueOf(mLocation.getLongitude()));
                    }
                }


//          Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();

            /*if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 2 ");


            } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 3 ");

            }*/
                /*16-aug Pragna*/
        /*    if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {*/


                try {
                    // today = new Date();
                    /*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    String getLAST_DB_DATA_STORE_TIME = getSharedPref.getLAST_DB_DATA_STORE_TIME();
                    System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
                    System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + getLAST_DB_DATA_STORE_TIME + "");

                    Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(getLAST_DB_DATA_STORE_TIME);
                    Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
                    long diff = CURRENT.getTime() - OLD.getTime();
                    int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                    int hours = (int) (diff / (1000 * 60 * 60));
                    MIN_FOR_LOCAL_STORE = (int) (diff / (1000 * 60));
                    // SEC_FOR_LOCAL_STORE = (int) (diff / (1000));
                } catch (Exception E) {
                    System.out.println("error in to MIN CALCULATION ISSE  !!!!!!!!!!!!!!!!!!!!!");
                    E.printStackTrace();
                }
                System.out.println("THIS IS DIFFERENCE FOR DB2222222@@@@@@@@@@@@@@@@ " + MIN_FOR_LOCAL_STORE + " MINIT_API_INTERVAL_IME_FROM_API " + MINIT_API_INTERVAL_IME_FROM_API + "");
                if (data != null) {
//                    if (MIN_FOR_LOCAL_STORE >= 2) {

                    if (MIN_FOR_LOCAL_STORE >= MINIT_API_INTERVAL_IME_FROM_API) {

                        dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 222 ");/*GPS PROPER WORKING*/










                        /*try {

                            System.out.println("this is notifications " + unreadNotification + "");
                            if (unreadNotification > 0) {
                                startForeground(NOTIFICATION_ID, getNotification4LastUpdate("New Notification!!", false,true));
                            }
                        }catch (Exception e)
                        {
                            System.out.println("this is data");
                        }*/


                    } else {

                        System.out.println("WILL NOT ALLOW USER TO SAVE DATA 22222222 !!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                }

           /* } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "", " LOCATION UPDATE FORGROUND 3 ");

            }*/
            } else {
                System.out.println("please provide valid data!!!!!!!!!!!!!!!!!!");


                startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Wrong Location!!", true, false));
                try {
                    /*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*/
                    // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());


                    getSharedPref.setLAST_DB_DATA_STORE_TIME(currentDateandTime + "");
                    writeDataInLogFile("FAKE LOCATION ", "REJECTING FACK LOCATION");
                } catch (Exception E) {
                    System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                }

            }
        }

        //Log.e("database counter===",""+dbConnector.countGPSData());

        //Log.e("countGPSData", ""+dbConnector.countGPSData());


        if (cd != null && cd.isConnectingToInternet()) {
            //20-june-19 pragna for storing internet is avail or not
//            System.out.println("NOW INTERNET GET AVAILABLE::::: ");
//            List<InternetMasterBean> data = dbConnector.getInternetMasterData(0 + "");//not available @ last and then delete all record from table is remain
//
//            List<InternetMasterBean> data_avail_internet = dbConnector.getInternetMasterData(1 + "");//not available @ last and then delete all record from table is remain
//            if (data != null) {
//                if (data.size() > 0) {
//                    InternetMasterBean bean = data.get(0);
//                    System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
//                }
//            }
//
//            if (data_avail_internet != null) {
//                if (data_avail_internet.size() > 0) {
//                    InternetMasterBean bean = data_avail_internet.get(0);
//                    System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
//                }
//            }
            //20-june-19 pragna for storing internet is avail or not
            System.out.println("NOW INTERNET GET AVAILABLE::::: ");
            List<InternetMasterBean> data_not_avail_internet = dbConnector.getInternetMasterData(0 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain

            List<InternetMasterBean> data_avail_internet = dbConnector.getInternetMasterData(1 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain
            if (data_not_avail_internet != null) {
                if (data_not_avail_internet.size() > 0) {
                    InternetMasterBean bean = data_not_avail_internet.get(0);
                    //   System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
                    System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getInternet_DateTime() + "");
                }
            }

            if (data_avail_internet != null) {
                if (data_avail_internet.size() > 0) {
                    InternetMasterBean bean = data_avail_internet.get(0);
                    //    System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + bean.getGPS_DateTime() + "");
                    System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + bean.getInternet_DateTime() + "");
                }
            }

            writeDataInLogFile(" SENDING LOCATION 1 ", " this is from 111111 ");
            /*16-aug-19 pragna*/
            try {
                // today = new Date();
                /*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());
                String last_API_WAS_CALLED_TIME = getSharedPref.getLAST_API_CALL_DATE();
                System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
                System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + last_API_WAS_CALLED_TIME + "");

                Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(last_API_WAS_CALLED_TIME);
                Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
                //  Date today = new Date();


                long diff = CURRENT.getTime() - OLD.getTime();
                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                int hours = (int) (diff / (1000 * 60 * 60));
                minutes = (int) (diff / (1000 * 60));
                int seconds = (int) (diff / (1000));

                System.out.println("THIS IS DIFFERNCE :::::::::::::::: " + minutes + "");
                //   sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());


            } catch (Exception E) {
                System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                E.printStackTrace();
            }
//            if (minutes >= 2) {
            /*21-Aug-pragna to remove duplicate for error response*/
            System.out.println("getSharedPref.GET_SHOULD_CALL_API()222 " + getSharedPref.GET_SHOULD_CALL_API() + "");
            System.out.println("minutes " + minutes + "");
            System.out.println("MINIT_API_INTERVAL_IME_FROM_API " + MINIT_API_INTERVAL_IME_FROM_API + "");
            if (minutes >= MINIT_API_INTERVAL_IME_FROM_API && getSharedPref.GET_SHOULD_CALL_API()) {

                sendingLocationData();
            } else {
                System.out.println("THIS IS REJECTED FROM LOCAL 222 !!!!!!!!!!!!!!!!!!!!!!!");
                writeDataInLogFile(" SENDING LOCATION 2 ", " THIS IS REJECTED FROM LOCAL22222 !!!!!!!!!!!!!!!!!!!!!!! ");
            }
        }
        //20-june-19 pragna for storing internet is avail or not
        else {
            try {
                System.out.println("INTERNET NOT AVAILABLE ");
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                today = new Date();
                InternetMasterBean bean = new InternetMasterBean();
//                bean.setGPS_Internet_Status(0 + "");
//                bean.setGPS_DateTime(sdf.format(today));
                bean.set_Internet_Status(0 + "");
                bean.setInternet_DateTime(sdf.format(today));
                bean.setGPS_Accuracy("");
                bean.setGPS_Status("");

                dbConnector.addInterNetData(bean, getSharedPref.getRegisteredUserId() + "");
                //   startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please restart Internet!", true, false));




                /*10-sept-19 pragna for service data*/
              /*  ServiceMasterBean serviceMasterBean = new ServiceMasterBean();
//                        bean.setGPS_Internet_Status(0 + "");
//                        bean.setGPS_DateTime(sdf.format(today));

                serviceMasterBean.setService_Off_Time(sdf.format(today));
                serviceMasterBean.setService_Type("1");

//getSharedPref.SET_LAST_INTERNET_STOPED_TIME();
                getSharedPref.SET_LAST_INTERNET_STOPED_TIME(sdf.format(today) + "");
//                dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "",
                dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "", getSharedPref.GET_LAST_INTERNET_STOPED_TIME() + "");*/


            } catch (Exception e) {
                System.out.println("error in to data save :::::::::::");
            }
        }
    }

    /**
     * Sets the location request parameters.
     */
    @SuppressLint("RestrictedApi")
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationUpdateForegroundService getService() {
            return LocationUpdateForegroundService.this;
        }
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    public String GetAddress(double lat, double lng, GPSMasterBean data) {
        LocationAddress = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                LocationArray = new String[1];
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                /*sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/
                if (TextUtils.isEmpty(address.getLocality())) {
                    LocationArray[0] = address.getLocality();
                } else {
                    LocationArray[0] = "";
                }


                LocationAddress = sb.toString();
                if (LocationAddress != null && LocationAddress.length() > 0 && LocationAddress.contains("null") || LocationAddress.contains("Null") || LocationAddress.contains("NULL")) {
                    if (LocationAddress.contains("null")) {
                        LocationAddress = LocationAddress.replace("null", "");
                    }
                    if (LocationAddress.contains("Null")) {
                        LocationAddress = LocationAddress.replace("Null", "");
                    }
                    if (LocationAddress.contains("NULL")) {
                        LocationAddress = LocationAddress.replace("NULL", "");
                    }
                }
                try {
                    System.out.println("in to try!!!!!!!!!!!!!!!!!");
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    String postalCode = addresses.get(0).getPostalCode();
                    String country = addresses.get(0).getCountryName();

                    if (LocationAddress.contentEquals("")) {
                        LocationAddress = city + "";
                        LocationAddress = LocationAddress + ", " + knownName + " ";
                        LocationAddress = LocationAddress + ", " + state + " ";

                        LocationAddress = LocationAddress + ", " + country + " ";

                    }
                    if (LocationAddress != null && LocationAddress.length() > 0 && LocationAddress.contains("null") || LocationAddress.contains("Null") || LocationAddress.contains("NULL")) {
                        if (LocationAddress.contains("null")) {
                            LocationAddress = LocationAddress.replace("null", "");
                        }
                        if (LocationAddress.contains("Null")) {
                            LocationAddress = LocationAddress.replace("Null", "");
                        }
                        if (LocationAddress.contains("NULL")) {
                            LocationAddress = LocationAddress.replace("NULL", "");
                        }
                    }


                } catch (Exception e) {

                }
                data.setGPS_COUNTRY_NAME(address.getCountryName() + "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocationAddress;
    }

    public String GetAddress2(double lat, double lng) {
        String address = "";
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            //data.setGPS_COUNTRY_NAME(addresses.get(0).getCountryName() + "");
            if (address == null || address.contentEquals("")) {
                if (city != null && !city.contentEquals("")) {
                    address = city + "";
                }
                if (state != null && !state.contentEquals("")) {
                    address = address + "," + state + "";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }


    private int calculateBatteryPercentage() {

        boolean present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        int battery_percentage = 0;
        if (present) {
            battery_percentage = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        }
        return battery_percentage;
    }

    private int isGPSON() {

        try {

            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (statusOfGPS) {
                return 1;

            } else {

                return 0;
            }
        } catch (Exception ex) {
            System.out.println("this is error in to catch GPS ONOFF1111");
            ex.printStackTrace();
            return 0;
        }

    }

    private int isGPSON(double lat, double lng) {

        try {
            System.out.println("this is location isGPSON ### " + lat + " lng " + lng + "");
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (statusOfGPS && checkPermissions()) {//gps on as well as given permission to use it
                if (lat <= 0 && lng <= 0) {//geting latlong

//                    calcMinForGpsOff();
                    calcMinForLocationLatLngOff();
                }


                return 1;

            } else {
               /* if (lat > 0 && lng >= 0) {//geting latlong

                    calcMinForGpsOff();
                }*/
                //else {
                // startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please start Location1!!!", true, false));
                //  calcMinForLocationLatLngOff();
                calcMinForGpsOff();
                //  }
                return 0;
            }
        } catch (Exception ex) {
            System.out.println("this is error in to catch GPS ONOFF");
            ex.printStackTrace();
            return 0;
        }

    }

    private void calcMinForLocationLatLngOff() {


        try {
            int MIN_FOR_APP_LAST_LOCATIONLATLNGOFF_DATETIME_FOR_NOTIFICATION = getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME();
            System.out.println("calcMinForLocationLatLngOff#### ");
            String getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2019-08-21 09:11:40
            String currentDateandTime = sdf.format(new Date());
            getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION = getSharedPref.getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION();
            System.out.println("calcMinForLocationLatLngOff## " + getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION + "");
            if (getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION == null || getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION.contentEquals(""))//blank or null
            {
                System.out.println("calcMinForLocationLatLngOff## " + " into if condition " + currentDateandTime + "");
                getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");
                // sendGPSRELATEDDATA("3");
                apiforInternet("", "-" + "", currentDateandTime + "", 3);

            } else if (getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION.length() > 10) {
                Date OLD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION);
                Date CURRENT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateandTime);
                long diff = CURRENT.getTime() - OLD.getTime();
                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                int hours = (int) (diff / (1000 * 60 * 60));
                MIN_FOR_APP_LAST_LOCATIONLATLNGOFF_DATETIME_FOR_NOTIFICATION = (int) (diff / (1000 * 60));
                System.out.println("calcMinForLocationLatLngOff## " + " into else if condition " + currentDateandTime + " and MIN_FOR_APP_LAST_LOCATIONLATLNGOFF_DATETIME_FOR_NOTIFICATION " + MIN_FOR_APP_LAST_LOCATIONLATLNGOFF_DATETIME_FOR_NOTIFICATION + "");
                if (MIN_FOR_APP_LAST_LOCATIONLATLNGOFF_DATETIME_FOR_NOTIFICATION >= getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME()) {

                    /*   getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");*/
                    System.out.println("calcMinForLocationLatLngOff## " + " into FINAL if condition and should call api" + currentDateandTime + "");
                    // sendGPSRELATEDDATA("3");
                    apiforInternet("", "-" + "", getAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION + "", 3);

                }
            } else {
                getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");
                System.out.println("calcMinForLocationLatLngOff## " + " into LAST ELSE condition " + currentDateandTime + "");
            }

        } catch (Exception e) {
            System.out.println("this is error in to setting ");
        }


    }

    private void calcMinForGpsOff() {

        try {
            int MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION = getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME();
            System.out.println("calcMinForGpsOff#### ");
            String getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2019-08-21 09:11:40
            String currentDateandTime = sdf.format(new Date());
            getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION = getSharedPref.getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION();
            System.out.println("calcMinForGpsOff## " + getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION + "");
            if (getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION == null || getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION.contentEquals(""))//blank or null
            {
                System.out.println("calcMinForGpsOff## " + " into if condition " + currentDateandTime + "");
                getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");
                // if (MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION >= getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME()) {
                apiforInternet("", "-" + "", currentDateandTime + "", 2);
                // }
            } else if (getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION.length() > 10) {
                Date OLD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION);
                Date CURRENT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentDateandTime);
                long diff = CURRENT.getTime() - OLD.getTime();
                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                int hours = (int) (diff / (1000 * 60 * 60));
                MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION = (int) (diff / (1000 * 60));
                System.out.println("calcMinForGpsOff## " + " into else condition " + currentDateandTime + " and MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION " + MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION + "");
                if (MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION >= getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME()) {

                    /*     getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");*/
                    System.out.println("calcMinForGpsOff## " + " into FINAL if condition " + currentDateandTime + "");
                    /*   sendGPSRELATEDDATA("2");*/
                    //    apiforInternet("", "-" + "", getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION + "", 2);
                    apiforInternet("", "-" + "", currentDateandTime + "", 2);


                } else {
                    System.out.println("calcMinForGpsOff## " + " into else222 condition  MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION" + MIN_FOR_APP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION + " and getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME( " + getSharedPref.getLOCATION_NOTIFICATION_INTERVAL_TIME() + "");
                }
            } else {
                getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(currentDateandTime + "");
                System.out.println("calcMinForGpsOff## " + " into LAST ELSE condition " + currentDateandTime + "");
            }

        } catch (Exception e) {
            System.out.println("this is error in to setting calcMinForGpsOff# ");
            e.printStackTrace();
        }
    }

    /*    private void sendGPSRELATEDDATA(String GPSORLATLONGSTATUS) {//2 gps off 3=>gps on but no latlong
     *//*dont forget to  getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(""); AFTER PROPER API CALL *//*
//    getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION("");
    }*/

    private void sendingLocationData() {
        /*16-aug Pragna to remove replicated data*/
        System.out.println("is this running!!!!!!!!!!!!");


        loopCounter = 0;

        gpsMasterBeanList = new ArrayList<>();
        System.out.println("this is called sendingLocationData");

        try {
            gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");
        } catch (Exception ex) {
            System.out.println("this is error in getting data ::::::::::::::: " + ex.getCause());
            ex.printStackTrace();
        }
        if (apiService == null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            //    apiService_local = ApiClient_duplicate.getClient().create(ApiClient_duplicate.class);
        }

        JSONArray jsonArray = new JSONArray();
        System.out.println("this is  getting data gpsMasterBeanList.size() ::::::::::::::: " + gpsMasterBeanList.size() + "");
        if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
            getSharedPref.SET_SHOULD_CALL_API(false);
            jsonArray = new JSONArray();
            for (GPSMasterBean data : gpsMasterBeanList) {

                loopCounter++;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("loc_name", data.getGPS_Location_Name());
                } catch (Exception ex) {
                }

                try {
                    jsonObject.put("loc_address", data.getGPS_Address());
                } catch (Exception ex) {
                }

//if(data.getGPS_Address().toString().contains("India")||)
                try {
                    jsonObject.put("loc_latitude", data.getGPS_Latitude());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_latitude", "0.0");
                    } catch (JSONException js) {
                    }
                }


                try {
                    jsonObject.put("loc_longitude", data.getGPS_Longitude());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_longitude", "0.0");
                    } catch (JSONException js) {
                    }
                }


                try {
                    jsonObject.put("loc_date_time", data.getGPS_DateTime());
                } catch (Exception ex) {
                }

                try {
                    jsonObject.put("loc_battery", data.getGPS_Battery_Percentage());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_battery", "0");
                    } catch (JSONException js) {
                    }
                }

                try {
                    jsonObject.put("loc_gps_status", data.getGPS_Status());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_gps_status", "0");
                    } catch (JSONException js) {
                    }
                }

                try {
                    jsonObject.put("loc_network_status", data.getGPS_Internet_Status());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_network_status", "0");
                    } catch (JSONException js) {
                    }
                }

                System.out.println("data json request accuracy::::::   " + data.getGPS_Accuracy() + "");
                try {
//                    jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? data.getGPS_Accuracy() : "0");
//                    jsonObject.put("loc_accuracy", data.getGPS_Accuracy() + "");
                    jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? "0" : data.getGPS_Accuracy() + "");
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_accuracy", "0");
                        System.out.println("data json innter acuuracy set 0::::::::: ");
                    } catch (JSONException js) {
                    }
                }

                try {
                    jsonObject.put("km_traveled", data.getGPS_Km_Travelled() + "");
                } catch (Exception ex) {
                    try {
                        jsonObject.put("km_traveled", "0");
                    } catch (JSONException js) {
                    }
                }
                /*25-july Pragna*/

//                try {
//                    jsonObject.put("is_proper", data.getGPS_COUNTRY_FLAG() + "");
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("is_proper", "0");
//                    } catch (JSONException js) {
//                    }
//                }
                try {
                    System.out.println("THI IS REQUEST ************************* " + data.getGPS_Is_Loc_Changed() + "");
                    jsonObject.put("is_loc_changed", data.getGPS_Is_Loc_Changed());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("is_loc_changed", "0");
                    } catch (JSONException js) {
                    }
                }
                try {
                    jsonObject.put("is_proper", data.getGPS_COUNTRY_FLAG() + "");
                } catch (Exception ex) {
                    try {
                        jsonObject.put("is_proper", "0");
                    } catch (JSONException js) {
                    }
                }
                jsonArray.put(jsonObject);


                if (loopCounter == 1) {
                    minLastUpdatedRecordId = data.getGPS_Master_Id();
                }
                if (loopCounter == gpsMasterBeanList.size()) {
                    maxLastUpdatedRecordId = data.getGPS_Master_Id();
                }


            }

            //Log.e("jsonArray",""+jsonArray.toString());


            getSharedPref = new SharedPref(getApplicationContext());

//            sendInternetConnectionData();
            Request_Insert_Location_Sync dataRequest = new Request_Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
                    getSharedPref.getAppAndroidId(),
                    String.valueOf(getSharedPref.getRegisteredId()),
                    getSharedPref.getRegisteredUserId(),
                    Config.ACCESS_KEY,
                    getSharedPref.getCompanyId(),
                    getSharedPref.getBranchId(),
                    getSharedPref.getUserPhone(),
                    jsonArray.toString());


            Log.e("dataRequest", "" + dataRequest);
            System.out.println("this is data request " + dataRequest + "");
            writeDataInLogFile("dataRequest FROM  LocationUpdateForegroundService >>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ", dataRequest + "");
            Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(dataRequest);



         /*   Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
                    getSharedPref.getAppAndroidId(),
                    String.valueOf(getSharedPref.getRegisteredId()),
                    getSharedPref.getRegisteredUserId(),
                    Config.ACCESS_KEY,
                    getSharedPref.getUserPhone(),
                    jsonArray.toString());*/


            call.enqueue(new Callback<InsertLocationSyncResponse>() {
                @Override
                public void onResponse(Call<InsertLocationSyncResponse> call, Response<InsertLocationSyncResponse> response) {
                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                        if (response.body().getFLAG() == 1) {
                            getSharedPref.SET_SHOULD_CALL_API(true);
                            Log.e("Succ 111", response.body().getMSG());
//                            boolean digitsOnly = TextUtils.isDigitsOnly(minLastUpdatedRecordId);
//                            boolean digitsOnly1 = TextUtils.isDigitsOnly(maxLastUpdatedRecordId);
//                            if (digitsOnly && digitsOnly1) {
                            try {
                                dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId), getSharedPref.getRegisteredUserId() + "");

                                sendServiceStatus(2);
                                sendServiceStatus(3);

                            } catch (Exception e) {
                                System.out.println("error in to delete");
                                writeDataInLogFile("dataRequest error in to delete " + " ::::: ", "");
                            }
                         /*   int lastrecord = 0;
//                            lastrecord = Integer.parseInt(maxLastUpdatedRecordId);
                            lastrecord = gpsMasterBeanList.size() - 1;
                            System.out.println("CHECK >>>>>>>>>>>>>>>> " + gpsMasterBeanList.get(lastrecord).getGPS_Latitude() + "");
                            if (gpsMasterBeanList.get(lastrecord).getGPS_Latitude().contentEquals("0.0") && gpsMasterBeanList.get(lastrecord).getGPS_Longitude().contentEquals("0.0")) {
                                startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Please start Location!!!", false));

                            } else {*/
                            startForeground(NOTIFICATION_ID, getNotification4LastUpdate("Location Updated!", false, false));


                            try {
                                int unreadNotification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId()).size();
                                System.out.println("this is notifications " + unreadNotification + "");
                                if (unreadNotification > 0) {
                                    startForeground(NOTIFICATION_ID, getNotification4LastUpdate("New Notification!!", false, true));
                                }
                            } catch (Exception e) {
                                System.out.println("this is data");
                            }

                            //    }

                            //  startForeground(NOTIFICATION_ID, getNotification4LastUpdate(""));
                            try {
                                /*https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android*/
                                // today = new Date();
//                                    sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                                String currentDateandTime = sdf.format(new Date());


                                getSharedPref.SET_SHOULD_CALL_API(true);

                                getSharedPref.setLAST_API_CALL_TIME(currentDateandTime + "");
                                writeDataInLogFile("dataRequest LocationUpdateForegroundService >>>>>>>>>>>>>>>>>>>>>>>>>>>>> success:::  " + response.body().getFLAG() + " ::::  ", response.body().getMSG() + "");
                            } catch (Exception E) {
                                System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                                getSharedPref.SET_SHOULD_CALL_API(true);
                            }


                            //20-june-19 pragna for storing internet is avail or not

                            try {
//                                    System.out.println("INTERNET BECOME AVAILABLE DELETING ALL OLD DATA  ");
                                System.out.println("::::::INTERNET IS AVAILABLE:::: ");
                                /////////////////ppppppppppppp        dbConnector.deleteInternetData();

                                SimpleDateFormat sdf;
                                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                today = new Date();
                                InternetMasterBean bean = new InternetMasterBean();
//                                bean.setGPS_Internet_Status(1 + "");
//                                bean.setGPS_DateTime(sdf.format(today));

                                bean.set_Internet_Status(1 + "");
                                bean.setInternet_DateTime(sdf.format(today));
                                bean.setGPS_Accuracy("");
                                bean.setGPS_Status("");

                                dbConnector.addInterNetData(bean, getSharedPref.getRegisteredUserId() + "");
                                sendInternetConnectionData();
                                getSharedPref.SET_SHOULD_CALL_API(true);

                                /*10-sept-19 pragna for service data*/
                               /* ServiceMasterBean serviceMasterBean = new ServiceMasterBean();
//                        bean.setGPS_Internet_Status(0 + "");
//                        bean.setGPS_DateTime(sdf.format(today));

                                serviceMasterBean.setService_On_Time(sdf.format(today)+"");
                                serviceMasterBean.setService_Type("1");

                                // dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "",sdf.format(today) + "");
                                dbConnector.addServiceData(serviceMasterBean, getSharedPref.getRegisteredUserId() + "", sdf.format(today) + "");
*/
                            } catch (Exception e) {
                                getSharedPref.SET_SHOULD_CALL_API(true);
                                System.out.println("error in to data save3333333333333 :::::::::::");
                                e.printStackTrace();
                            }


                        } else {

                            getSharedPref.SET_SHOULD_CALL_API(true);
                            Log.e("Succ 444", response.body().getMSG());
                            writeDataInLogFile("dataRequest LocationUpdateForegroundService>>>>>>>>>>>>>>>>>>>>>>>>>>>>> success with flag is not 1 NOT DELETING " + " ::::: ", response.body().getMSG() + "");


                        }
                    } else {
                        getSharedPref.SET_SHOULD_CALL_API(true);
                        Log.e("Erorr 222", response.toString());
                        writeDataInLogFile("dataRequest LocationUpdateForegroundService >>>>>>>>>>>>>>>>>>>>>>>>>>>>> with error NOT DELETING " + " ::::: ", response.toString() + "");

                    }
                }

                @Override
                public void onFailure(Call<InsertLocationSyncResponse> call, Throwable t) {
                    getSharedPref.SET_SHOULD_CALL_API(true);
                    Log.e("Erorr 333", t.getMessage().toString());
                    writeDataInLogFile("dataRequest LocationUpdateForegroundService>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Erorr onFailure  " + " ::::: ", t.getMessage().toString() + "");
                }
            });



            /*LazyDataConnection GetSources_B2CTask = new LazyDataConnection("insert_location_shrink");
            GetSources_B2CTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "insert_location_shrink", getAPI.insert_location_shrink(
                    getSharedPref.getAppVersionCode(),
                    getSharedPref.getAppAndroidId(),
                    getSharedPref.getRegisteredId(),
                    0,
                    getSharedPref.getUserPhone(),
                    jsonArray.toString()
            ));*/
        } else {
            getSharedPref.SET_SHOULD_CALL_API(true);
        }

    }

    private Timer timer;
    public int counter = 0;
    private TimerTask timerTask;
    int minutes = 2;
    int MIN_FOR_LOCAL_STORE = 2;
    public int MINIT_API_INTERVAL_IME_FROM_API = 2;

    public void startTimer() {

        try {
            System.out.println("startTimer isTodayPunchINDone() && !isTodayPunchOutDone() " + isTodayPunchINDone() + " isTodayPunchOutDone " + isTodayPunchOutDone() + "");

            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                timer = new Timer();
                timerTask = new TimerTask() {
                    public void run() {

                        Log.e("Count", "=========  " + (counter++));
                        System.out.println("Check for location permission");
                        System.out.println("getLastLocation isTodayPunchINDone() && !isTodayPunchOutDone() " + isTodayPunchINDone() + " isTodayPunchOutDone " + isTodayPunchOutDone() + "");
                        //  if(checkPermissions()) {
                        if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
                            System.out.println("getLastLocation isTodayPunchINDone() && !isTodayPunchOutDone() " + isTodayPunchINDone() + " isTodayPunchOutDone " + isTodayPunchOutDone() + "");
                            getLastLocation();
                            // }
                            // else{
                            //   System.out.println("NO PERMISION!!!!!!!!!!!!!!!!");
                            //}
                            try {
                                MINIT_API_INTERVAL_IME_FROM_API = getSharedPref.getAPP_LOCATION_INTERVAL_TIME();
                            } catch (Exception e) {

                            }
                        } else {
                            try {
                                stoptimertask();
                                removeLocationUpdates();
                            } catch (Exception e) {
                                System.out.println("error in to location stop!!!");
                            }
                        }

                   /* try {
                        // today = new Date();
                        *//*https://stackoverflow.com/questions/21285161/android-difference-between-two-dates*//*
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String currentDateandTime = sdf.format(new Date());
                        String last_API_WAS_CALLED_TIME = getSharedPref.getLAST_API_CALL_DATE();
                        System.out.println("THIS CURRENT TIME >>>>>>>>>>>>>>>>>>>>>    " + currentDateandTime + "");
                        System.out.println("THIS WAS LAST TIME >>>>>>>>>>>>>>>>>>>>>    " + last_API_WAS_CALLED_TIME + "");

                        Date OLD = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(last_API_WAS_CALLED_TIME);
                        Date CURRENT = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(currentDateandTime);
                        //  Date today = new Date();


                        long diff = CURRENT.getTime() - OLD.getTime();
                        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                        int hours = (int) (diff / (1000 * 60 * 60));
                        minutes = (int) (diff / (1000 * 60));
                        int seconds = (int) (diff / (1000));

                        System.out.println("THIS IS DIFFERNCE :::::::::::::::: " + minutes + "");
                        //   sdf_full = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault());


                    } catch (Exception E) {
                        System.out.println("error in to data save !!!!!!!!!!!!!!!!!!!!!");
                        E.printStackTrace();
                    }
                    if (minutes >= 2) {
                        getLastLocation();
                    }
                    else {
                        System.out.println("THIS IS REJECTED FROM LOCAL !!!!!!!!!!!!!!!!!!!!!!!");
                    }*/
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // display toast

                            }

                        });
                    }
                };
//            timer.schedule(timerTask, 6000, 180000); //750000 tooooo late
//                timer.schedule(timerTask, 1000, 30000); //750000
                timer.schedule(timerTask, 1500, 40000); //750000
            }
            // timer.schedule(timerTask, 6000, 90000); //750000
        } catch (Exception ex) {
        }
        //900000

    }

    public void stoptimertask() {
        if (timer != null) {
            // Log.e("timer","destroy");
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            // Log.e("timerTask","destroy");
            timerTask.cancel();
            timerTask = null;
        }

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    File logFile;
    SimpleDateFormat sdf_full_with_time;

    public void writeDataInLogFile(String title, String text) {
        try {
            //BufferedWriter for performance, true to set append to file flag

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.newLine();
            if (sdf_full_with_time != null && today != null) {
                buf.append("***********  " + title + " " + sdf_full_with_time.format(today) + " IS STARTED ***********  ");
            } else {
                buf.append("***********  " + title + " " + " IISUE IN GETTING TIME" + " IS STARTED ***********  ");
            }
            buf.newLine();
            buf.append(text);
            buf.newLine();
            buf.append("***********  " + title + " " + " IS OVER *********** ");
            buf.newLine();
            buf.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void apiforInternet(final String avail_or_not, String start_time_date, final String was_stop_time_date, final int TYPE_INTERNET_GPS_LOC) {
        System.out.println("api for internet "+getSharedPref.GET_SHOULD_CALL_CON_API()+"");
        if (getSharedPref.GET_SHOULD_CALL_CON_API()) {

            writeDataInLogFile("INTERNET API ^^^ ",getSharedPref.GET_SHOULD_CALL_CON_API()+"");
//        Call<Connection_on_off_notificationResponse> call = apiService.Sconnection_on_off_notification(String.valueOf(getSharedPref.getAppVersionCode()),
//                getSharedPref.getAppAndroidId(),
//                String.valueOf(getSharedPref.getRegisteredId()),
//                String.valueOf(getSharedPref.getRegisteredUserId()),
//                Config.ACCESS_KEY,
//                getSharedPref.getCompanyId(), avail_or_not, start_time_date);

     /*   Call<Connection_on_off_notificationResponse> call = apiService.Sconnection_on_off_notification(String.valueOf(getSharedPref.getAppVersionCode()),
                getSharedPref.getAppAndroidId(),
                String.valueOf(getSharedPref.getRegisteredId()),
                String.valueOf(getSharedPref.getRegisteredUserId()),
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId(), was_stop_time_date, start_time_date); */
            /**25-jan-2020-pragna for changed API
             * Meaning of type

             1 internet connection off and on
             2 GPS is off
             3 GPS is on but location not retrieve*/
            System.out.println("vCODE " + String.valueOf(getSharedPref.getAppVersionCode() + ""));
            System.out.println("androidID  " + getSharedPref.getAppAndroidId() + "");
            System.out.println("RegID  " + String.valueOf(getSharedPref.getRegisteredId()));
            System.out.println("uid " + String.valueOf(getSharedPref.getRegisteredUserId()) + "");
            System.out.println("key " + Config.ACCESS_KEY + "");
            System.out.println("compID " + getSharedPref.getCompanyId() + "");
            System.out.println("start_time_date " + start_time_date + "");
            System.out.println("was_stop_time_date " + was_stop_time_date + "");
            System.out.println("TYPE_INTERNET_GPS_LOC " + TYPE_INTERNET_GPS_LOC + "");
            int ii = Integer.parseInt(getSharedPref.getRegisteredUserId() + "");
     /*   Call<Connection_on_off_notificationResponse> call = apiService.Sconnection_on_off_notification(String.valueOf(getSharedPref.getAppVersionCode()+""),
                getSharedPref.getAppAndroidId()+"",
                String.valueOf(getSharedPref.getRegisteredId()+""),
                String.valueOf(getSharedPref.getRegisteredUserId()+""),
                Config.ACCESS_KEY+"",
                getSharedPref.getCompanyId()+"", was_stop_time_date+"", start_time_date+"", TYPE_INTERNET_GPS_LOC);*/
            if (apiService == null) {
                apiService = ApiClient.getClient().create(ApiInterface.class);
            }
            getSharedPref.SET_SHOULD_CALL_CON_API(false);
            Call<Connection_on_off_notificationResponse> call = apiService.Sconnection_on_off_notification(getSharedPref.getAppVersionCode(),
                    getSharedPref.getAppAndroidId(),
                    getSharedPref.getRegisteredId(),
                    ii,
                    Config.ACCESS_KEY + "",
                    getSharedPref.getCompanyId() + "", was_stop_time_date + "", start_time_date + "", TYPE_INTERNET_GPS_LOC);
            System.out.println("THIS IS APPI FOR INTERNET CALLED avail_or_not " + avail_or_not + "  TYPE_INTERNET_GPS_LOC  " + TYPE_INTERNET_GPS_LOC + "");
            System.out.println("call::::::::::: " + call.toString() + "");
            call.enqueue(new Callback<Connection_on_off_notificationResponse>() {
                @Override
                public void onResponse(Call<Connection_on_off_notificationResponse> call, Response<Connection_on_off_notificationResponse> response) {
                    try {
                        System.out.println("internet resonse  " + response.toString() + "");
                        writeDataInLogFile("INTERNET API ",response.toString()+"");

                    /*{
  "TOTAL": 1,
  "MESSAGE": "Record Found",
  "RECORDS": [
    {
      "FLAG": 1,
      "MSG": "Saved Sucessfully"
    }
  ]
}*/
                        if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                            System.out.println("response.body().gettOTAL() " + response.body().gettOTAL() + "");
                            System.out.println("response.body().getRECORDS().get(0).getStatus() " + response.body().getRECORDS().get(0).getStatus() + "");
                            if (response.body().gettOTAL() > 0) {
                                if (response.body().getRECORDS().get(0).getStatus().contentEquals("1")) {//treated as flag

//                                dbConnector.deleteInternetData("0" + "", getSharedPref.getRegisteredUserId() + "");
//                                dbConnector.deleteInternetData(avail_or_not + "", getSharedPref.getRegisteredUserId() + "");
                                    if (TYPE_INTERNET_GPS_LOC == 1) {
                                        System.out.println("this is delete for avail_or_not " + avail_or_not + "");
                                        try {
                                            //  dbConnector.deleteInternetData("0" + "", getSharedPref.getRegisteredUserId() + "");
//                                    dbConnector.deleteInternetData(avail_or_not + "", getSharedPref.getRegisteredUserId() + "");
                                            dbConnector.deleteInternetData(getSharedPref.getRegisteredUserId() + "");
                                            getSharedPref.SET_SHOULD_CALL_CON_API(true);

                                        } catch (Exception e) {
                                            System.out.println("error due to delete");
                                        }
                                    } else if (TYPE_INTERNET_GPS_LOC == 2) {
                                        System.out.println("TAG222222222 ");
//                                    getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION("");
                                        getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(was_stop_time_date + "");
                                        getSharedPref.setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(was_stop_time_date + "");
                                        getSharedPref.SET_SHOULD_CALL_CON_API(true);
                                        writeDataInLogFile(" Connection_on_off_notificationResponse TIME SET ", "setAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION " + was_stop_time_date + "");

                                        //  getSharedPref. getAPP_LAST_GPS_OFF_DATETIME_FOR_NOTIFICATION(was_stop_time_date);
                                    } else if (TYPE_INTERNET_GPS_LOC == 3) {
                                        System.out.println("TAG333333 ");
//                                    getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION("");
                                        getSharedPref.SETAPP_LAST_LOCATION_LATLNGOFF_DATETIME_FOR_NOTIFICATION(was_stop_time_date + "");
                                        getSharedPref.SET_SHOULD_CALL_CON_API(true);
                                    }
                                }
                            }
                        }
                        writeDataInLogFile(" Connection_on_off_notificationResponse ", response.toString() + "");

                    } catch (Exception e) {
                        System.out.println("isue in to Connection_on_off_notificationResponse response");
                        getSharedPref.SET_SHOULD_CALL_CON_API(true);
                    }
                }

                @Override
                public void onFailure(Call<Connection_on_off_notificationResponse> call, Throwable t) {
                    writeDataInLogFile(" Connection_on_off_notificationResponse ", t.getMessage() + "");

                    System.out.println("THIS  IS FAILLED NET API ");
                    getSharedPref.SET_SHOULD_CALL_CON_API(true);
                    t.printStackTrace();
                }
            });
//        String Url = "http://dev.ierp/API/SFSheetal/connection_on_off_notification?app_version="+
//                String.valueOf(getSharedPref.getAppVersionCode())+"&android_id="+getSharedPref.getAppAndroidId()+"&device_id="+String.valueOf(getSharedPref.getRegisteredId())+"&user_id=" +String.valueOf(getSharedPref.getRegisteredUserId())+"&key="+Config.ACCESS_KEY+"&comp_id="+getSharedPref.getCompanyId()
//                +"&net_flag="+avail_or_not+"&stor_time="+time_date+"";
//        System.out.println("this is api called.....................");
//        String Url = Config.MAIN_URL+"connection_on_off_notification?app_version="+
//                String.valueOf(getSharedPref.getAppVersionCode())+"&android_id="+getSharedPref.getAppAndroidId()+"&device_id="+String.valueOf(getSharedPref.getRegisteredId())+"&user_id=" +String.valueOf(getSharedPref.getRegisteredUserId())+"&key="+Config.ACCESS_KEY+"&comp_id="+getSharedPref.getCompanyId() +"&net_flag="+avail_or_not+"&stor_time="+time_date+"";
//        Url=Url.replace(" ","%20");
//        System.out.println("this is url :::::::: "+Url+"");
//        StringRequest req = new StringRequest(Request.Method.POST, Url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println("this is response "+response+"");
//
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("this is error in net api ");
//            }
//        });
//
//        queue.add(req);
        }
    }

    String sendServiceStatusminLastUpdatedRecordId = "";
    String sendServiceStatusmaxLastUpdatedRecordId = "";

    public void sendServiceStatus(final int type) {

        if (dbConnector != null && getSharedPref != null) {

            String jsonString = "";
            int loopCounter1 = 0;
            final int ii = Integer.parseInt(getSharedPref.getRegisteredUserId() + "");
            JSONArray jsonArray = new JSONArray();
            jsonArray = new JSONArray();
            if (type != 1) {
                List<ServiceMasterBean> service_data = dbConnector.getserviceMasterData(type, getSharedPref.getRegisteredUserId() + "");

                if (service_data != null) {
                    if (service_data.size() > 0) {


                        System.out.println("in to sendServiceStatus " + type + "");
                        if (apiService == null) {
                            apiService = ApiClient.getClient().create(ApiInterface.class);
                        }

                        jsonArray = new JSONArray();


                        for (ServiceMasterBean data : service_data) {
                            loopCounter1++;

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("off_time", data.getService_Off_Time());
                            } catch (Exception ex) {
                            }
                            try {
                                jsonObject.put("on_time", data.getService_On_Time());
                            } catch (Exception ex) {
                            }
                            jsonArray.put(jsonObject);

                            if (loopCounter1 == 1) {
                                sendServiceStatusminLastUpdatedRecordId = data.getService_Master_Id();
                            }
                            if (loopCounter1 == service_data.size()) {
                                sendServiceStatusmaxLastUpdatedRecordId = data.getService_Master_Id();
                            }
                        }

                   /* Call<Connection_on_off_notificationResponse> call = apiService.SGPS_Internet_Bgservice(getSharedPref.getAppVersionCode(),
                            getSharedPref.getAppAndroidId(),
                            getSharedPref.getRegisteredId(),
                            ii,
                            Config.ACCESS_KEY + "",
                            getSharedPref.getCompanyId() + "", type, jsonArray.toString() + "");
                    Log.e("dataRequest", " SGPS_Internet_Bgservice  " + call.request());*/
                        //   writeDataInLogFile("SERVICE FROM  LocationUpdateForegroundService >>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ", call.toString() + "");
                    }
                }
            }
            if (type == 1)/*for internet*/ {
                List<InternetMasterBean> data_not_avail_internet = dbConnector.getInternetMasterData(0 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain
                List<InternetMasterBean> data_avail_internet = dbConnector.getInternetMasterData(1 + "", getSharedPref.getRegisteredUserId() + "");//not available @ last and then delete all record from table is remain

                if (data_not_avail_internet != null) {
                    if (data_not_avail_internet.size() > 0) {
                        InternetMasterBean bean = data_not_avail_internet.get(0);//
                        System.out.println("FROM THIS TIME INTERNET WAS NOT AVAILABLE :::::::::::  " + bean.getInternet_DateTime() + "");


                        if (data_avail_internet != null) {
                            if (data_avail_internet.size() > 0) {
                                InternetMasterBean internetMasterBean = data_avail_internet.get(0);
//
                                System.out.println("FROM THIS TIME INTERNET WAS IS  AVAILABLE :::::::::::  " + internetMasterBean.getInternet_DateTime() + "");
                                jsonArray = new JSONArray();
                                // for (ServiceMasterBean data : service_data) {
                                loopCounter1++;

                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("off_time", bean.getInternet_DateTime());
                                } catch (Exception ex) {
                                }
                                try {
                                    jsonObject.put("on_time", internetMasterBean.getInternet_DateTime() + "");
                                } catch (Exception ex) {
                                }
                                jsonArray.put(jsonObject);

                                          /*  if (loopCounter1 == 1) {
                                                sendServiceStatusminLastUpdatedRecordId = data.getService_Master_Id();
                                            }
                                            if (loopCounter1 == gpsMasterBeanList.size()) {
                                                sendServiceStatusmaxLastUpdatedRecordId = data.getService_Master_Id();
                                            }*/
                                // }


//apiforInternet()

                                //   apiforInternet(1 + "", internetMasterBean.getInternet_DateTime() + "", bean.getInternet_DateTime() + "", 1);


                            }
                        }
                    }
                }


            }
            System.out.println("jsonArray.toString() " + jsonArray.toString());

                   /* Call<Connection_on_off_notificationResponse> call = apiService.SGPS_Internet_Bgservice(getSharedPref.getAppVersionCode(),
                            getSharedPref.getAppAndroidId(),
                            getSharedPref.getRegisteredId(),
                            ii,
                            Config.ACCESS_KEY + "",
                            getSharedPref.getCompanyId() + "", type, jsonArray.toString() + "");
                    Log.e("dataRequest", " SGPS_Internet_Bgservice  " + call.request());


call.enqueue(new Callback<Connection_on_off_notificationResponse>() {
    @Override
    public void onResponse(Call<Connection_on_off_notificationResponse> call, Response<Connection_on_off_notificationResponse> response) {
        System.out.println("response 1"+response+"");
    }

    @Override
    public void onFailure(Call<Connection_on_off_notificationResponse> call, Throwable t) {
        System.out.println("error1 ");
        t.printStackTrace();

    }
});*/
            if (jsonArray.toString() != null && jsonArray.toString().length() > 4) {
                Request_GPS_Internet_Bgservice datarequest = new Request_GPS_Internet_Bgservice(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), Config.ACCESS_KEY,
                        getSharedPref.getCompanyId(), type + "", jsonArray.toString());
                Call<GPS_Internet_BgserviceResponse> call1 = apiService.GPS_Internet_Bgservice(datarequest);
                System.out.println("datarequestdatarequest " + datarequest + "");
                writeDataInLogFile("GPS_Internet_Bgservice FROM  LocationUpdateForegroundService >>>>>>>>>>>>>>>>>>>>>>>>>>>>>   ", datarequest + "");
                call1.enqueue(new Callback<GPS_Internet_BgserviceResponse>() {
                    @Override
                    public void onResponse(Call<GPS_Internet_BgserviceResponse> call, Response<GPS_Internet_BgserviceResponse> response) {
                        System.out.println("Response " + response + "");
                        if (response.body().getFLG() > 0) {
                              /* if(type==1)
                                {

                                }*/
                            dbConnector.deleteServiceData(type, sendServiceStatusminLastUpdatedRecordId, sendServiceStatusmaxLastUpdatedRecordId + "", ii + "");

                        }


                    }

                    @Override
                    public void onFailure(Call<GPS_Internet_BgserviceResponse> call, Throwable t) {
                        System.out.println("error ");
                        t.printStackTrace();
                    }
                });
            } else {
                System.out.println("SKIPPED for " + type + "");
            }


        }
    }
/*
    public static double distanceManual(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
*/

    public static double distanceManual(double lat1, double lng1, double lat2, double lng2) {
        System.out.println("PRINT!!!!!!!!!!!!!!!!! "+lat1 +" lng1 : "+lng1+"  lat2 : "+lat2+" lng2 : "+lng2+"" );
        double dist=0.0;
        //if(lat1==lat2&&lng1==lat2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        dist = (double) (earthRadius * c);
        System.out.println("THIS MUST PRINT !!!!!!!!!!! " + dist + "");
        // }
        return dist;
    }
}
