package com.infinity.infoway.vimal.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.infinity.infoway.vimal.BuildConfig;
import com.infinity.infoway.vimal.HR.Activity_Attendance_Management;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.add__news_or_notification.activity.ViewNewsOrNotificationListActivity;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.Request_Insert_Location_Sync;
import com.infinity.infoway.vimal.api.response.GetAllSyncCityResponse;
import com.infinity.infoway.vimal.api.response.InsertLocationSyncResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.fragment.Background_Service;
import com.infinity.infoway.vimal.kich_expense.Expense.Expense_Listing;
import com.infinity.infoway.vimal.kich_leave_module.Leave.Pojo.LoginPojo;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.model.NotificationBean;
import com.infinity.infoway.vimal.service.AfterBootrBroadcastReceiver;
import com.infinity.infoway.vimal.service.JobScheduledService;
import com.infinity.infoway.vimal.service.LocationUpdateForegroundService_u;
import com.infinity.infoway.vimal.service.MyService;
import com.infinity.infoway.vimal.service.OverLayTrackingService;
import com.infinity.infoway.vimal.service.SensorRestarterBroadcastReceiver;
import com.infinity.infoway.vimal.util.common.DialogUtils;
import com.infinity.infoway.vimal.util.common.NotificationUtils;
import com.infinity.infoway.vimal.util.common.URLS;
import com.judemanutd.autostarter.AutoStartPermissionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Activity_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    static Activity_Home homeActivity;
    private LinearLayout linear_order_history, linear_expense_tracker, linear_out_standing_dash_board, linear_complaint, linear_attedance,
            linear_retailer, linear_sync_offline, linear_my_schedule, linear_Suspecting;
    private Intent intent;
    private FloatingActionButton floatingActionButton;
    private TextView txt_title_sync_offline, txt_title_order, txt_title_outstanding, txt_title_complaint, txt_title_expense_management, txt_title_attendance_management, txt_title_retailer_management, txt_title_my_schedule, txt_title_Suspecting;
    private SweetAlertDialog dialogSuccess;
    private SharedPref getSharedPref;
    private DrawerLayout drawer;
    public static boolean FLAG_4_BACK_START_PG_AGAIN = true;

    /**
     * Tour\nPlanning
     */
    private TextView mTxtTitleTourPlanning;
    private LinearLayout mLinearTourPlanning;
    /**
     * Retailer \nManagement
     */
    private TextView mTxtTitleNewRetailer;
    private LinearLayout mLinearNewRetailer;
    /*14-aug-19 pragna for punch-in out location*/
//    private final String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA};
//    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
//    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STATE = 100;

    /**
     * This method is use to getting HomeActivity object from anywhere
     *
     * @return main activity object
     */
    public static Activity_Home getHomeActivity() {

        return homeActivity;
    }

    // A reference to the service used to get location updates.
    private LocationUpdateForegroundService_u mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;
    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private ApiInterface apiService;

    private Snackbar addOrderSnackBar;
    private AlertDialog alertDialog;
    private Handler mHandler = new Handler();


    private BroadcastReceiver mRegistrationBroadcastReceiver;

    TextView tvNotificationCounter;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                LocationUpdateForegroundService_u.LocalBinder binder = (LocationUpdateForegroundService_u.LocalBinder) service;
                mService = binder.getService();
                mBound = true;

                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    mService.requestLocationUpdates();
                    // scheduleJob();
                }

            } catch (Exception e) {
                System.out.println("ERROR IN SERVICE ");
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("THIS IS SERVICE DISCONNECTED!!!!!!!!!!!!!!!!!!");
            mService = null;
            mBound = false;
        }
    };

    private LocationManager manager;
    private boolean statusOfGPS;
    private ProgressDialog progDialog;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final long INTERVAL = 1000 * 60;
    private static final long FASTEST_INTERVAL = 1000 * 30;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private TextView txt_user_name, txt_app_version;

    private int loopCounter = 0;
    private List<GPSMasterBean> gpsMasterBeanList;
    private String minLastUpdatedRecordId, maxLastUpdatedRecordId;
    private DBConnector dbConnector;
    private SimpleDateFormat sdf_full;
    RelativeLayout rvNotificaitonContainer;
    private List<GetAllSyncCityResponse.RECORD> listSyncCity;
    Boolean autoZoneEnabled = false;
    RequestQueue queue;

    private LinearLayout linear_leave_application;
    private LinearLayout llViewNewsOrNotification;


    @RequiresPermission("android.permission.WRITE_SETTINGS")
    public String setSettingsAutomaticDateTimeIfNeeded() {
        System.out.println("this is is autotime method");
        String data = "";
        String timeSettings = Settings.Global.getString(
                this.getContentResolver(),
                Settings.Global.AUTO_TIME);
        String timeZoneSettings = Settings.Global.getString(
                this.getContentResolver(),
                Settings.Global.AUTO_TIME_ZONE);


        if (timeSettings.contentEquals("0") || timeZoneSettings.contentEquals("0")) {
            data = "0";
           /* android.provider.Settings.Global.putString(
                    this.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME, "1");*/
        }
        System.out.println("timeSettings timeSettings timeSettings " + timeSettings + "");
        System.out.println("timeZoneSettings timeZoneSettings timeZoneSettings " + timeZoneSettings + "");
        return data;

    }

    public static double distanceManual(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (float) (earthRadius * c);

        return dist;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("");
        homeActivity = this;
        /*pragna ################################ */

        tvNotificationCounter = (TextView) findViewById(R.id.tvNotificationCounter);
        rvNotificaitonContainer = (RelativeLayout) findViewById(R.id.rvNotificaitonContainer);
        rvNotificaitonContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Home.this, NotificationListActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
//  Double  distanceInMeters = distanceManual(22.2470321, 70.7971997, 22.2470321, 70.7971997);
        /*Double  distanceInMeters = distanceManual(20.3033459, 75.653999, 20.3033459, 75.652982);*/
        Double distanceInMeters = distanceManual(20.3033459, 75.653999, 20.3033459, 75.652982);

        final Intent in = new Intent(Activity_Home.this, Background_Service.class);
        startService(in);
        System.out.println("DISTANCE!!!!!!!!!!!!!!!!!!!!!!! " + distanceInMeters + "");
        getSharedPref = new SharedPref(Activity_Home.this);
       /* Settings.Global.putInt(
                getContentResolver(),
                Settings.Global.AUTO_TIME_ZONE,
                autoZoneEnabled ? 1 : 0);
        System.out.println("AUTOZONEENBLE      "+autoZoneEnabled+"");*/
        String TIME_AUTO = "1";
        try {
            TIME_AUTO = setSettingsAutomaticDateTimeIfNeeded();
        } catch (Exception e) {
            System.out.println("ERROR IN TO DATETIME ");
        }
        if (TIME_AUTO.contentEquals("0")) {

            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 920);

        }
        txt_user_name = navigationView.getHeaderView(0).findViewById(R.id.txt_user_name);
        if (!TextUtils.isEmpty(getSharedPref.getUserName())) {
            txt_user_name.setText(getSharedPref.getUserName());
        }

        if (!TextUtils.isEmpty(getSharedPref.getCompanyName())) {
            this.setTitle(getSharedPref.getCompanyName());
        }


        txt_app_version = navigationView.getHeaderView(0).findViewById(R.id.txt_app_version);

        if (getSharedPref.getAppVersionCode() > 0) {
            txt_app_version.setText("App Version : " + String.valueOf(getSharedPref.getAppVersionCode()) + " (" + getSharedPref.getAppVerName() + " )");
        }

        initControls();
        Location PREV = new Location(LocationManager.GPS_PROVIDER);
        final Intent intent = new Intent(Activity_Home.this, Background_Service.class);
        startService(intent);
        Location CUR = new Location(LocationManager.GPS_PROVIDER);
        PREV.setLatitude(20.3033459);
        PREV.setLongitude(75.653999);
        CUR.setLatitude(20.3033459);
        CUR.setLongitude(75.652982);

        double t = CUR.distanceTo(PREV);

        System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!!! " + t + "");
        double mit = CALCULATEDISTANCE(PREV, CUR);
        if (Activity_Splash.SHOULD_CLEAR_LOG_FILES) {
            Activity_Splash.SHOULD_CLEAR_LOG_FILES = false;
            try {
                File dir;
                File logFile, logFORINSERTONLYFile;
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "document_download");
                logFile = new File(dir, "vimal_location_log.txt");
//                logFile = new File(dir, "E_track_location_log.txt");
//                logFORINSERTONLYFile = new File(dir, "E_track_location_insert_log.txt");
                logFORINSERTONLYFile = new File(dir, "vimal_location_insert_log.txt");
                // File fdelete = new File(uri.getPath());
                if (logFile.exists()) {
                    if (logFile.delete()) {
                        System.out.println("file Deleted :");
                    } else {
                        System.out.println("file not Deleted :");
                    }
                }
                if (logFORINSERTONLYFile.exists()) {
                    if (logFORINSERTONLYFile.delete()) {
                        System.out.println("file Deleted logFORINSERTONLYFile:");
                    } else {
                        System.out.println("file not Deleted logFORINSERTONLYFile:");
                    }
                }
            } catch (Exception e) {
                System.out.println("error in to log file delete!!!!!!!!!!!!!!");
            }
        }

        //start Service
        //  startService();
        /*14-aug pragna*/
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(Activity_Home.this, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(Activity_Home.this, RunTimePerMissions, MY_PERMISSIONS_REQUEST_READ_WRITE_STATE);
            } else {
                //checkVersionInfoApiCall();
            }
        } else {
            //checkVersionInfoApiCall();
        }*/
        /*Pragna ##################*/
        Intent ll24 = new Intent(Activity_Home.this, SensorRestarterBroadcastReceiver.class);
        PendingIntent recurringLl24 = PendingIntent.getBroadcast(Activity_Home.this, 0, ll24, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ll24.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        myReceiver = new AfterBootrBroadcastReceiver();





        /*20-aug-19 Pragna*/

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
                    System.out.println("THIS IS MESSAGEaa " + message + "");
                }
            }
        };

        displayFirebaseRegId();


        int unreadNotification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId()).size();
        System.out.println("this is notifications " + unreadNotification + "");
        if (unreadNotification > 0) {
            tvNotificationCounter.setVisibility(View.VISIBLE);
            tvNotificationCounter.setText("" + unreadNotification);


            /*20-aug-19 Pragna to display notification as popup started */
            String Notificationmsg = "";
            String NOTIFICATION_FINAL_STR = "";
            String Date = "";

            try {

//                List<NotificationBean> all_notification = dbConnector.getNotificationData(getSharedPref.getRegisteredUserId()+"");
                List<NotificationBean> all_notification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId() + "");
                for (int i = 0; i < unreadNotification; i++) {
                   /* Date = Date+""+all_notification.get(i).getDate_time() + "\n";
                    Notificationmsg = Notificationmsg+""+all_notification.get(i).getTitle() + "\n\n";*/

                    Date = "<b>" + all_notification.get(i).TimeStamp + "</b><br>";
                    Notificationmsg = all_notification.get(i).Title + "<br><br>";
                    NOTIFICATION_FINAL_STR = NOTIFICATION_FINAL_STR + "" + Date + "" + Notificationmsg + "";
                    System.out.println("Date >>>>>  " + Date + "");
                    System.out.println("Notificationmsg >>>>> " + Notificationmsg + "");
                    System.out.println("NOTIFICATION_FINAL_STR  >>>>> " + NOTIFICATION_FINAL_STR + "");
                }
                //  NOTIFICATION_FINAL_STR = Date + "" + Notificationmsg + "";
                System.out.println("NOTIFICATION_FINAL_STR 2nd >>>>> " + NOTIFICATION_FINAL_STR + "");
                if (DISPLAY) {
                    showNotificationDialog(true, NOTIFICATION_FINAL_STR + "");
                }
            } catch (Exception e) {
                System.out.println("ERROR IN DISPLAY NOTIFICATION ");
                e.printStackTrace();
            }
        }
        /*20-aug-19  Pragna to display notification as popup over*/






        /*https://developer.android.com/guide/topics/permissions/default-handlers*/


      /*  Intent setSmsAppIntent =
                new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        setSmsAppIntent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                getPackageName());
        startActivityForResult(setSmsAppIntent, 852);*/

        //  alarms.setRepeating(AlarmManager.RTC_WAKEUP, first_log.getTime(), AlarmManager.INTERVAL_HOUR, recurringLl24); // Log repetition


        try {
            AutoStartPermissionHelper.getInstance().getAutoStartPermission(Activity_Home.this);
            System.out.println("AUTO STARTUP");
        } catch (Exception e) {
            System.out.println("ERROR !!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }


        alarms.setRepeating(AlarmManager.RTC_WAKEUP, 10000, AlarmManager.INTERVAL_HOUR, recurringLl24); // Log repetition
//        GPSMasterBean data = new GPSMasterBean();
//        GetAddress(23.2481019, 72.4855805, data);
//        GetAddress2(23.2481019, 72.4855805, data);
//        System.out.println("************************************NOW CHANGED*************************************");
//        GetAddress(23.2625191, 72.4708205, data);
//        GetAddress2(23.2625191, 72.4708205, data);
    }

    private double CALCULATEDISTANCE(Location prev, Location cur) {
        double dis = 0;
        dis = prev.distanceTo(cur);
        System.out.println("this is 1111111111111 " + dis + "");
        float[] dist = new float[2];
        dis = cur.distanceTo(prev);
        System.out.println("this is 2222222222 " + dis + "");

        Location.distanceBetween(prev.getLatitude(), prev.getLongitude(), cur.getLatitude(), cur.getLongitude(), dist);
        System.out.println("this is 33333333333333 " + dist[0] + "");

        return dis;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initControls() {

        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


        dbConnector = new DBConnector(Activity_Home.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        linear_order_history = findViewById(R.id.linear_order_history);
        linear_expense_tracker = findViewById(R.id.linear_expense_tracker);
        linear_out_standing_dash_board = findViewById(R.id.linear_out_standing_dash_board);
        linear_complaint = findViewById(R.id.linear_complaint);
        linear_attedance = findViewById(R.id.linear_attedance);
        linear_retailer = findViewById(R.id.linear_retailer);
        linear_sync_offline = findViewById(R.id.linear_sync_offline);
        linear_my_schedule = findViewById(R.id.linear_my_schedule);

        txt_title_sync_offline = findViewById(R.id.txt_title_sync_offline);
        txt_title_order = findViewById(R.id.txt_title_order);
        txt_title_outstanding = findViewById(R.id.txt_title_outstanding);
        txt_title_complaint = findViewById(R.id.txt_title_complaint);
        txt_title_expense_management = findViewById(R.id.txt_title_expense_management);
        txt_title_attendance_management = findViewById(R.id.txt_title_attendance_management);
        txt_title_retailer_management = findViewById(R.id.txt_title_retailer_management);
        txt_title_my_schedule = findViewById(R.id.txt_title_my_schedule);
        txt_title_my_schedule = findViewById(R.id.txt_title_my_schedule);
        txt_title_Suspecting = findViewById(R.id.txt_title_Suspecting);
        linear_Suspecting = findViewById(R.id.linear_Suspecting);

        floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setVisibility(View.GONE);

        linear_order_history.setOnClickListener(this);
        linear_expense_tracker.setOnClickListener(this);
        linear_out_standing_dash_board.setOnClickListener(this);
        linear_complaint.setOnClickListener(this);
        linear_attedance.setOnClickListener(this);
        linear_retailer.setOnClickListener(this);
        linear_sync_offline.setOnClickListener(this);
        linear_my_schedule.setOnClickListener(this);
        linear_Suspecting.setOnClickListener(this);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);


//        if (!statusOfGPS) {
//            if (progDialog != null && !progDialog.isShowing()) {
//                progDialog.dismiss();
//            }
//            progDialog = new ProgressDialog(Activity_Home.this);
//            progDialog.setIndeterminate(true);
//            progDialog.setMessage(getResources().getString(R.string.title_app_config));
//            progDialog.setCancelable(false);
//            progDialog.show();
//        }

        //getPassiveLocation();

        //getCellLocation();

        //scheduleSync(Activity_Home.this);

        //scheduleJob();

    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Snackbar bar = Snackbar.make(drawer, getResources().getString(R.string.tap_back_again_to_exit), Snackbar.LENGTH_LONG);
            bar.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                  /*  if (mService != null) {
                        mService.requestLocationUpdates();
                    }*/

                }
            }, 2000);

        } else {
            super.onBackPressed();
        }
    }

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            if (isTodayPunchINDone()) {
                if (!isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    try {
                        dialogSuccess = new SweetAlertDialog(Activity_Home.this, SweetAlertDialog.WARNING_TYPE);
                        dialogSuccess.setContentText(getResources().getString(R.string.are_sure_want_logout));
                        dialogSuccess.setCancelable(true);
                        dialogSuccess.show();

                        dialogSuccess.setConfirmText(getResources().getString(R.string.title_yes));

                        dialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.setCancelable(false);
                                sDialog
                                        .setContentText(getResources().getString(R.string.title_logout_from_app))
                                        .setConfirmText(getResources().getString(R.string.title_ok))

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                dialogSuccess.dismissWithAnimation();
                                                dialogSuccess.cancel();
                                                getSharedPref.setIsLogin(false);
                                                getSharedPref.setLoginUserName("");
                                                getSharedPref.setLoginLoginUserPassword("");

                                                getSharedPref.setUserPunchInDate("");
                                                getSharedPref.setUserPunchOutDate("");
                                                getSharedPref.setCompanyName("");
                                                getSharedPref.setCompanyId("0");
                                                getSharedPref.setBranchId("0");
                                                getSharedPref.setLastUpdatedSyncCityDate("");
                                                getSharedPref.setLastUpdatedSyncRetailerDate("");


                                                Intent intent = new Intent(Activity_Home.this, Activity_Login.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                sDialog.showCancelButton(false);

                            }
                        });

                        dialogSuccess.setCancelText(getResources().getString(R.string.title_no));
                        dialogSuccess.showCancelButton(true);

                    } catch (Exception ignored) {
                    }
                }
            } else {
                try {
                    dialogSuccess = new SweetAlertDialog(Activity_Home.this, SweetAlertDialog.WARNING_TYPE);
                    dialogSuccess.setContentText(getResources().getString(R.string.are_sure_want_logout));
                    dialogSuccess.setCancelable(true);
                    dialogSuccess.show();

                    dialogSuccess.setConfirmText(getResources().getString(R.string.title_yes));

                    dialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.setCancelable(false);
                            sDialog
                                    .setContentText(getResources().getString(R.string.title_logout_from_app))
                                    .setConfirmText(getResources().getString(R.string.title_ok))

                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            dialogSuccess.dismissWithAnimation();
                                            dialogSuccess.cancel();
                                            getSharedPref.setIsLogin(false);
                                            getSharedPref.setLoginUserName("");
                                            getSharedPref.setLoginLoginUserPassword("");

                                            getSharedPref.setUserPunchInDate("");
                                            getSharedPref.setUserPunchOutDate("");
                                            getSharedPref.setCompanyName("");
                                            getSharedPref.setCompanyId("0");
                                            getSharedPref.setBranchId("0");
                                            getSharedPref.setLastUpdatedSyncCityDate("");
                                            getSharedPref.setLastUpdatedSyncRetailerDate("");

                                            Intent intent = new Intent(Activity_Home.this, Activity_Login.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            sDialog.showCancelButton(false);

                        }
                    });

                    dialogSuccess.setCancelText(getResources().getString(R.string.title_no));
                    dialogSuccess.showCancelButton(true);

                } catch (Exception ignored) {
                }
            }


            return true;
        } else if (id == R.id.action_update) {
            try {
                dialogSuccess = new SweetAlertDialog(Activity_Home.this, SweetAlertDialog.SUCCESS_TYPE);
                dialogSuccess.setContentText(getResources().getString(R.string.you_are_using_latest));
                dialogSuccess.setCancelable(true);
                dialogSuccess.show();

                Button confirm_button = dialogSuccess.findViewById(R.id.confirm_button);
                confirm_button.setText(R.string.title_ok);

                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSuccess.dismissWithAnimation();
                        dialogSuccess.cancel();
                    }
                });
            } catch (Exception ignored) {
            }
            return true;
        } else if (id == R.id.action_exit) {
            try {
                dialogSuccess = new SweetAlertDialog(Activity_Home.this, SweetAlertDialog.WARNING_TYPE);
                dialogSuccess.setContentText(getResources().getString(R.string.are_sure_want_exit));
                dialogSuccess.setCancelable(true);
                dialogSuccess.show();

                dialogSuccess.setConfirmText(getResources().getString(R.string.title_yes));

                dialogSuccess.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.setCancelable(false);
                        sDialog
                                .setContentText(getResources().getString(R.string.title_exit_from_app))
                                .setConfirmText(getResources().getString(R.string.title_ok))

                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();
                                        finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.showCancelButton(false);

                    }
                });

                dialogSuccess.setCancelText(getResources().getString(R.string.title_no));
                dialogSuccess.showCancelButton(true);

            } catch (Exception ignored) {
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            linear_order_history.performClick();
        } else if (id == R.id.nav_outstanding) {
            linear_out_standing_dash_board.performClick();
        } else if (id == R.id.nav_expense) {
            linear_expense_tracker.performClick();
        } else if (id == R.id.nav_collection) {
            linear_complaint.performClick();
        } else if (id == R.id.nav_attendance) {
            linear_attedance.performClick();
        } else if (id == R.id.nav_add_retailer) {
            linear_retailer.performClick();
        } else if (id == R.id.nav_sync_data) {
            linear_sync_offline.performClick();
        } else if (id == R.id.nav_my_schedule) {
            linear_my_schedule.performClick();
        } else if (id == R.id.nav_feedback) {
            linear_complaint.performClick();
        } else if (id == R.id.nav_Suspecting) {
            linear_Suspecting.performClick();
        } else if (id == R.id.nav_Tour_planning) {
            mLinearTourPlanning.performClick();
        } else if (id == R.id.nav_new_retailer) {
            mLinearNewRetailer.performClick();
        } else if (id == R.id.nav_leave_application) {
            linear_leave_application.performClick();
        } else if (id == R.id.nav_view_news_noti) {
            llViewNewsOrNotification.performClick();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linear_expense_tracker:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
//                    intent = new Intent(Activity_Home.this, Activity_Expense_Management.class);//changed as below
                    intent = new Intent(Activity_Home.this, Expense_Listing.class);
                    intent.putExtra("title_screen", txt_title_expense_management.getText().toString().trim());
                    startActivity(intent);
                }
                break;

            case R.id.linear_complaint:
                /*if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                }else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                }else{
                    intent= new Intent(Activity_Home.this, Activity_Complaint.class);
                    intent.putExtra("title_screen",txt_title_complaint.getText().toString().trim());
                    startActivity(intent);
                }*/
                break;
            case R.id.linear_attedance:

                FLAG_4_BACK_START_PG_AGAIN = false;

                intent = new Intent(Activity_Home.this, Activity_Attendance_Management.class);
                intent.putExtra("title_screen", txt_title_attendance_management.getText().toString().trim());
                startActivity(intent);
                break;

            case R.id.linear_sync_offline:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
                    intent = new Intent(Activity_Home.this, Activity_Sync_Data.class);
                    intent.putExtra("title_screen", txt_title_sync_offline.getText().toString().trim());
                    startActivity(intent);
                }
                break;
            case R.id.linear_my_schedule:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
                    intent = new Intent(Activity_Home.this, Activity_Add_Call.class);
                    intent.putExtra("title_screen", txt_title_my_schedule.getText().toString().trim());
                    startActivity(intent);
                }
                break;
            case R.id.linear_Suspecting:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
                    intent = new Intent(Activity_Home.this, Suspecting_Main.class);
                    intent.putExtra("title_screen", txt_title_Suspecting.getText().toString().trim());
                    startActivity(intent);
                }
                break;
            case R.id.linear_Tour_planning:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
                    intent = new Intent(Activity_Home.this, Activity_MySchedule.class);
                    intent.putExtra("title_screen", mTxtTitleTourPlanning.getText().toString().trim());
                    startActivity(intent);
                }
                break;
            case R.id.linear_new_retailer:
                if (!isTodayPunchINDone()) {
                    showAttendanceScreen();
                } else if (isTodayPunchOutDone()) {
                    showPunchOutDialog();
                } else {
                    FLAG_4_BACK_START_PG_AGAIN = false;
//                intent = new Intent(Activity_Home.this, Davat_Suspecting_Entry.class);
                    intent = new Intent(Activity_Home.this, Activity_Add_Customer.class);
                    intent.putExtra("title_screen", mTxtTitleNewRetailer.getText().toString().trim());
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunning(LocationUpdateForegroundService_u.class))) {
            try {
                mService = new LocationUpdateForegroundService_u();
                bindService(new Intent(Activity_Home.this, LocationUpdateForegroundService_u.class), mServiceConnection,
                        Context.BIND_AUTO_CREATE);

            } catch (Exception ex) {
            }

        }

        /*img_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    mService.requestLocationUpdates();
                }
            }
        });*/

       /* img_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mService.removeLocationUpdates();
            }
        });*/


        //


        // Restore the state of the buttons when the activity (re)launches.


        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.



       /* bindService(new Intent(this, LocationUpdateForegroundService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);*/
    }

    public static boolean WAS_GPS_OFF = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onResume() {
        super.onResume();


        if (isGooglePlayServicesAvailable()) {
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!statusOfGPS) {
                showGPSDialog();
                WAS_GPS_OFF = true;
                // buildGoogleApiClient();
                // createLocationRequest();
            } else {


                dismissAppConfigDialog();
                if (getSharedPref.GET_SHOULD_CALL_API()) {
                    syncOfflineLocationData();
                }

            }

            if (statusOfGPS && WAS_GPS_OFF) {
                WAS_GPS_OFF = false;
                System.out.println("ISSUE IN GPS!!!!!!!!!!!!");
                try {
                    try {

                        mService = new LocationUpdateForegroundService_u();
                        bindService(new Intent(Activity_Home.this, LocationUpdateForegroundService_u.class), mServiceConnection,
                                Context.BIND_AUTO_CREATE);

                    } catch (Exception ex) {
                        System.out.println("ERROR IN TO RETRY LOC IN HOME!!!!!!!!!!!!!!");
                    }
                } catch (Exception e) {
                    System.out.println("error in to e!!!!!!! ");
                }

//        GPSTracker tracker=new GPSTracker(Activity_Home.this);
        /*if(tracker.canGetLocation())
        {
          System.out.println("!!!!!!!!!!!!! "+    tracker.getLatitude()+"  "+
          tracker.getLatitude()+"");
        }*/
            }

        } else {
            dismissAppConfigDialog();
            if (getSharedPref.GET_SHOULD_CALL_API()) {
                syncOfflineLocationData();
            }
        }

       /* LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        Location location = intent.getParcelableExtra(SpeedometerService.EXTRA_LOCATION);
                        if (location != null) {

                        }
                    }
                }, new IntentFilter(SpeedometerService.ACTION_BROADCAST)
        );*/
        /*20-Aug-19 Pragna for push notification*/
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());


        int unreadNotification = dbConnector.getNotificationDataUnread(getSharedPref.getRegisteredUserId()).size();
        System.out.println("this is notifications " + unreadNotification + "");
        if (unreadNotification > 0) {
            tvNotificationCounter.setVisibility(View.VISIBLE);
            tvNotificationCounter.setText("" + unreadNotification);


            /*16-oct Pragna to display notification as popup started */
            String Notificationmsg = "";
            String NOTIFICATION_FINAL_STR = "";
            String Date = "";
        }
    }


    private void showGPSDialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Activity_Home.this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device, enable location service or gps")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialog = alertDialogBuilder.create();
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog.show();
            } else if (alertDialog != null) {
                alertDialog.show();
            }
        } catch (Exception ex) {
        }

    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {

            Snackbar.make(
                    drawer,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(Activity_Home.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    }).show();
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(Activity_Home.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                //Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                // mService.requestLocationUpdates();
            } else {
                // Permission denied.

                Snackbar.make(
                        drawer,
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
       /* if (requestCode == MY_PERMISSIONS_REQUEST_READ_WRITE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 5 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
               // checkVersionInfoApiCall();
            } else {
                alertAlert(getResources().getString(R.string.permissions_has_not_grant));
            }
        }*/
    }

    private void alertAlert(String msg) {
        new android.app.AlertDialog.Builder(Activity_Home.this)
                .setTitle(getResources().getString(R.string.permission_request))
                .setMessage(msg + " So Reopen the app and grant permission for well uses of app")
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(Activity_Home.this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception ex) {
        }
    }

    protected void createLocationRequest() {

        try {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setSmallestDisplacement(20);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                //you're tea or coffee drinker?
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            dismissAppConfigDialog();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(Activity_Home.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            dismissAppConfigDialog();
                            break;
                    }
                }
            });

        } catch (Exception ex) {
        }

    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            dismissAppConfigDialog();
        } else if (requestCode == REQUEST_CODE) {

            // Double-check that the user granted it, and didn't just dismiss the request
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {

                    // Launch the service
                    launchMainService();
                } else {
                    Toast.makeText(this, "Sorry. Can't draw overlays without permission...", Toast.LENGTH_SHORT).show();
                }
            } else {
                launchMainService();
            }
        } else {
            dismissAppConfigDialog();
        }
    }


    private void dismissAppConfigDialog() {
        try {
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
            }
        } catch (Exception ex) {
        }
    }

    private void getPassiveLocation() {


        LocationManager locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

        // get high accuracy provider
        LocationProvider high =
                locMgr.getProvider(locMgr.getBestProvider(createFineCriteria(), true));


        // using high accuracy provider... to listen for updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locMgr.getLastKnownLocation(high.getName());
        if (location != null) {
            //Log.e("high name latitude==>",""+location.getLatitude());
        }

        List<String> providers = locMgr.getProviders(true);

        String locationProvider = "";

        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER; // 首选网络定位
            location = locMgr.getLastKnownLocation(locationProvider);

            if (location != null) {
                //Log.e("provider===>",""+locationProvider);
                Log.e("lat network==>", "" + location.getLatitude());
                Log.e("lng network==>", "" + location.getLongitude());
            }
        }

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
            location = locMgr.getLastKnownLocation(locationProvider);

            if (location != null) {
                //Log.e("provider===>",""+locationProvider);
                Log.e("lat gps==>", "" + location.getLatitude());
                Log.e("lng gps==>", "" + location.getLongitude());
            }
        }

        locationProvider = LocationManager.PASSIVE_PROVIDER;
        location = locMgr.getLastKnownLocation(locationProvider);

        if (location != null) {
            // Log.e("provider===>",""+locationProvider);
            Log.e("lat passive==>", "" + location.getLatitude());
            Log.e("lng passive==>", "" + location.getLongitude());
        }



        /*locMgr.requestLocationUpdates(high.getName(), 0, 0f,
                new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // do something here to save this new location
                        if(location!=null){
                            Log.e("lat lng",""+location.getLatitude());
                        }

                    }

                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    public void onProviderEnabled(String s) {
                        // try switching to a different provider
                    }

                    public void onProviderDisabled(String s) {
                        // try switching to a different provider
                    }
                });*/
    }


    private void displaySnackBar(String message) {
        try {
            if (addOrderSnackBar != null && addOrderSnackBar.isShown()) {
                addOrderSnackBar.dismiss();
            }
            addOrderSnackBar = Snackbar.make(drawer, message, Snackbar.LENGTH_LONG);
            addOrderSnackBar.show();
        } catch (Exception ex) {
        }
    }

    /**
     * this criteria needs high accuracy, high power, and cost
     */
    public static Criteria createFineCriteria() {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        return c;
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

    private void showAttendanceScreen() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Activity_Home.this, R.style.AlertDialog);
            alertDialogBuilder
                    .setMessage("Today you have not punch in")
                    .setCancelable(false)
                    .setPositiveButton("Do Punch In",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent intent = new Intent(Activity_Home.this, Activity_Attendance_Management.class);
                                    intent.putExtra("title_screen", getResources().getString(R.string.title_attendance_management_menu));
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    );
            alertDialog = alertDialogBuilder.create();
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog.show();
            } else if (alertDialog != null) {
                alertDialog.show();
            }
        } catch (Exception ex) {
        }
    }

    private void showPunchOutDialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Activity_Home.this, R.style.AlertDialog);
            alertDialogBuilder
                    .setMessage("Today you have not punch out so you can't logout !!!")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    if (alertDialog != null && alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                    }
                                    Intent intent = new Intent(Activity_Home.this, Activity_Attendance_Management.class);
                                    intent.putExtra("title_screen", getResources().getString(R.string.title_attendance_management_menu));
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    );
            alertDialog = alertDialogBuilder.create();
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog.show();
            } else if (alertDialog != null) {
                alertDialog.show();
            }
        } catch (Exception ex) {
        }
    }

    private void syncOfflineLocationData() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(Activity_Home.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request_attendance));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        loopCounter = 0;

        gpsMasterBeanList = new ArrayList<>();

        try {
            gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");
        } catch (Exception ex) {
        }


        JSONArray jsonArray = new JSONArray();
        if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
            getSharedPref.SET_SHOULD_CALL_API(false);
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

                try {
                    // jsonObject.put("loc_accuracy",data.getGPS_Accuracy().isEmpty()?data.getGPS_Accuracy():"0");
                    jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? "0" : data.getGPS_Accuracy() + "");
                } catch (Exception ex) {
                    try {
                        jsonObject.put("loc_accuracy", "0");
                    } catch (JSONException js) {
                    }
                }

                try {
                    jsonObject.put("km_traveled", data.getGPS_Km_Travelled());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("km_traveled", "0");
                    } catch (JSONException js) {
                    }
                }

                try {
                    jsonObject.put("is_loc_changed", data.getGPS_Is_Loc_Changed());
                } catch (Exception ex) {
                    try {
                        jsonObject.put("is_loc_changed", "0");
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

            getSharedPref = new SharedPref(Activity_Home.this);


            Request_Insert_Location_Sync dataRequest = new Request_Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
                    getSharedPref.getAppAndroidId(),
                    String.valueOf(getSharedPref.getRegisteredId()),
                    getSharedPref.getRegisteredUserId(),
                    Config.ACCESS_KEY,
                    getSharedPref.getCompanyId(),
                    getSharedPref.getBranchId(),
                    getSharedPref.getUserPhone(),
                    jsonArray.toString());


            Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(dataRequest);


          /*  Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
                    getSharedPref.getAppAndroidId(),
                    String.valueOf(getSharedPref.getRegisteredId()),
                    getSharedPref.getRegisteredUserId(),
                    Config.ACCESS_KEY,
                    getSharedPref.getUserPhone(),
                    jsonArray.toString());*/

            call.enqueue(new Callback<InsertLocationSyncResponse>() {
                @Override
                public void onResponse(Call<InsertLocationSyncResponse> call, Response<InsertLocationSyncResponse> response) {

                    /*if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                        if(response.body().getFLAG()==1){
                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId));
                        }
                    }else{

                    }*/

                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                        // Log.e("response 111",""+response.isSuccessful());
                        getSharedPref.SET_SHOULD_CALL_API(true);
                        if (response.body().getFLAG() == 1) {
                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId), getSharedPref.getRegisteredUserId() + "");

                            // getSharedPref.setPreviousLocation("0.0","0.0");
                        } else {

                            //Log.e("Erorr 111",response.body().getMSG());
                        }
                    } else {
                        //Log.e("Erorr 222",response.toString());
                        getSharedPref.SET_SHOULD_CALL_API(true);
                        dismissAppConfigDialog();
                        progDisp();
                    }

                    try {
                        if (!getSharedPref.getCurrentCityDate().equalsIgnoreCase(sdf_full.format(new Date()))) {
                            getAllSyncCityApiCall();
                        } else {
                            dismissAppConfigDialog();
                        }
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onFailure(Call<InsertLocationSyncResponse> call, Throwable t) {
                    dismissAppConfigDialog();
                    getSharedPref.SET_SHOULD_CALL_API(true);
                    progDisp();

                    try {
                        if (!getSharedPref.getCurrentCityDate().equalsIgnoreCase(sdf_full.format(new Date()))) {
                            getAllSyncCityApiCall();
                        } else {
                            dismissAppConfigDialog();
                        }
                    } catch (Exception ex) {
                    }

                    // Log.e("Erorr 333",t.getMessage().toUpperCase());
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
            try {
                if (!getSharedPref.getCurrentCityDate().equalsIgnoreCase(sdf_full.format(new Date()))) {
                    getAllSyncCityApiCall();
                } else {
                    dismissAppConfigDialog();

                    //Log.e("run 111",""+isServiceRunningOverlay(OverLayTrackingService.class));

                    if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunningOverlay(OverLayTrackingService.class))) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showOverlay();
                        } else {
                            launchMainService();
                        }

                    }

                }
            } catch (Exception ex) {
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showOverlay() {

        if (Settings.canDrawOverlays(this)) {

            // Launch service right away - the user has already previously granted permission
            launchMainService();
        } else {

            // Check that the user has granted permission, and prompt them if not
            checkDrawOverlayPermission();
        }
    }

    private void launchMainService() {

        Intent svc = new Intent(this, OverLayTrackingService.class);
        stopService(svc);
        startService(svc);


    }


    private void getAllSyncCityApiCall() {


        listSyncCity = new ArrayList<>();


        Call<GetAllSyncCityResponse> call = apiService.getAllSyncCity(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), String.valueOf(getSharedPref.getRegisteredUserId()), Config.ACCESS_KEY, getSharedPref.getCompanyId(), getSharedPref.getBranchId(), getSharedPref.getLastUpdatedSyncCityDate());

        call.enqueue(new Callback<GetAllSyncCityResponse>() {
            @Override
            public void onResponse(Call<GetAllSyncCityResponse> call, Response<GetAllSyncCityResponse> response) {

                dismissAppConfigDialog();

                if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                    try {
                        getSharedPref.setCurrentCityDate(sdf_full.format(new Date()));
                    } catch (Exception ex) {
                    }


                    if (response.body().getTOTAL() > 0) {
                        listSyncCity = response.body().getRECORDS();

                        boolean flag = dbConnector.addCityMaster(listSyncCity);
                        if (flag) {
                            getSharedPref.setLastUpdatedSyncCityDate(sdf_full.format(new Date()));
                        }

                    } else {
                        /*if((!TextUtils.isEmpty(response.body().getMESSAGE().toString()))) {
                            displaySnackBar(response.body().getMESSAGE().toString());
                        }else{
                            displaySnackBar("No city found !!!");
                        }*/
                    }
                }


                Log.e("run 111", "" + isServiceRunningOverlay(OverLayTrackingService.class));
                Log.e("run 111", "isTodayPunchINDone  " + isTodayPunchINDone());
                Log.e("run 111", "isTodayPunchOutDone  " + isTodayPunchOutDone());

                if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunningOverlay(OverLayTrackingService.class))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showOverlay();
                    } else {
                        launchMainService();
                    }

                }


            }

            @Override
            public void onFailure(Call<GetAllSyncCityResponse> call, Throwable t) {
                dismissAppConfigDialog();

                //Log.e("run 111",""+isServiceRunningOverlay(OverLayTrackingService.class));

                if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunningOverlay(OverLayTrackingService.class))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showOverlay();
                    } else {
                        launchMainService();
                    }

                }
            }
        });

    }


    private void progDisp() {
        try {
            progDialog = new ProgressDialog(Activity_Home.this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request_download_offline_data));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(Integer.MAX_VALUE);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i
                    .next();

            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {

                if (runningServiceInfo.foreground) {
                    Log.e("runhome", "service run in foreground");
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
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
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

    /* @Override
     protected void onDestroy() {
         super.onDestroy();
         System.out.println("THIS IS DESTROY!!!!!!!!!!!!!!!!!");
         if (getSharedPref != null) {
             getSharedPref.SET_SHOULD_CALL_API(true);
         }
         if (mBound) {
             // Unbind from the service. This signals to the service that this activity is no longer
             // in the foreground, and the service can respond by promoting itself to a foreground
             // service.
             unbindService(mServiceConnection);
             mBound = false;
             System.out.println("this is unbingding home !!!!!!!!!!!!!!!!!!!!!!!!!!!");
         }
     }*/
    AfterBootrBroadcastReceiver myReceiver;

    @Override
    protected void onDestroy() {
        System.out.println("on destroy called ");
        if (myReceiver != null) {
            System.out.println("unregister reciver");
            //    unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        super.onDestroy();
    }

    /*  @Override
      public void onStop() {
          super.onStop();
          System.out.println("THIS IS STOP HOME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          if (getSharedPref != null) {
              getSharedPref.SET_SHOULD_CALL_API(true);
          }
          *//*12-Aug pragna-> have comment*//*
        if (mBound) {
            System.out.println("THIS IS STOP HOME 222222!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;



            *//*12-aug pragna newly added*//*
     *//*   if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunning(LocationUpdateForegroundService.class))) {
              try {
                  mService = new LocationUpdateForegroundService();
                  bindService(new Intent(Activity_Home.this, LocationUpdateForegroundService.class), mServiceConnection,
                          Context.BIND_AUTO_CREATE);

                  if (mService != null) {
                      mService.requestLocationUpdates();
                  }
              } catch (Exception ex) {
              }

          }*//*


        } else {
            System.out.println("this is else !!!!!!!!!!!!!!");
            System.out.println("THIS IS STOP HOME 333333333333333333!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if (isTodayPunchINDone() && (!isTodayPunchOutDone()) && (!isServiceRunning(LocationUpdateForegroundService.class))) {
                try {
                    mService = new LocationUpdateForegroundService();
                    bindService(new Intent(Activity_Home.this, LocationUpdateForegroundService.class), mServiceConnection,
                            Context.BIND_AUTO_CREATE);

                    if (mService != null) {
                        mService.requestLocationUpdates();
                    }
                } catch (Exception ex) {
                }

            }
        }

        *//*12-Aug pragna-> have comment*//*
        //pppppppppppppppppppppppppp   ProcessPhoenix.triggerRebirth(Activity_Home.this);

        //   finish();
       *//* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
           //     ProcessPhoenix.triggerRebirth(Activity_Home.this);
            }
        }, 100000);*//*


    }*/
    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }

    }

    private void setAlarm() {
        final int REQUEST_CODE = 0;
        Intent intent = new Intent(Activity_Home.this, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        PendingIntent pendingIntent = PendingIntent.getActivity(Activity_Home.this, REQUEST_CODE,
                intent, 0);

        int alarmType = AlarmManager.ELAPSED_REALTIME;
        final int FIFTEEN_SEC_MILLIS = 300000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity_Home.ALARM_SERVICE);

        // setRepeating takes a start delay and period between alarms as arguments.
        // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
        // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
        alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + FIFTEEN_SEC_MILLIS,
                FIFTEEN_SEC_MILLIS, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob() {

        JobScheduler jobScheduler = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.cancelAll();
        }


        //900000
        final long ONE_DAY_INTERVAL = 1050000; // 10 Min

        final long ONE_DAY_INTERVAL_RESTRICTED = 1050000; // 15 Min

        ComponentName serviceComponent = new ComponentName(getApplicationContext(), JobScheduledService.class);
        JobInfo.Builder builder = new JobInfo.Builder(10, serviceComponent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(ONE_DAY_INTERVAL_RESTRICTED);
        } else {
            builder.setPeriodic(ONE_DAY_INTERVAL);
        }


        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(builder.build());

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
//            Log.d("Activity_Home", "Job scheduled!");
        } else {
//            Log.d("Activity_Home", "Job not scheduled");
        }
    }

    public final static int REQUEST_CODE = 10101;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {

        // Checks if app already has permission to draw overlays
        if (!Settings.canDrawOverlays(this)) {

            // If not, form up an Intent to launch the permission request
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));

            // Launch Intent, with the supplied request code
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    String LocationAddress;
    private String[] LocationArray;

    public String GetAddress(double lat, double lng, GPSMasterBean data) {
        LocationAddress = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                LocationArray = new String[1];
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                    System.out.println("this is getaddress form address 1 " + sb.toString() + "");
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
                data.setGPS_COUNTRY_NAME(address.getCountryName() + "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocationAddress;
    }

    public String GetAddress2(double lat, double lng, GPSMasterBean data) {
        LocationAddress = "";
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            String addressline1 = addresses.get(0).getAddressLine(1); // Only if available else return NULL
            data.setGPS_COUNTRY_NAME(addresses.get(0).getCountryName() + "");
            System.out.println("this is getaddress form address 1 ADDRESS  " + address + "");
            System.out.println("this is getaddress form address 1 CITY " + city + "");
            System.out.println("this is getaddress form address 1 state " + state + "");
            System.out.println("this is getaddress form address 1 country " + country + "");
            System.out.println("this is getaddress form address 1 postalCode " + postalCode + "");
            System.out.println("this is getaddress form address 1 knownName " + knownName + "");
            System.out.println("this is getaddress form address 1 ADDRESS LINE 1 " + addressline1 + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocationAddress;
    }

    private static final String TAG = Activity_Home.class.getSimpleName();

    private void startService() {

        Intent myIntent = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
        Log.e("TAG", "++++++++++222222++++++++");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.add(Calendar.SECOND, 10);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Start Alarm", Toast.LENGTH_LONG).show();

    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
        String regId = pref.getString("FCMToken", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            //     txtRegId.setText("Firebase Reg Id: " + regId);
            System.out.println("firebase RegId " + regId + "");
        } else {
            //   txtRegId.setText("Firebase Reg Id is not received yet!");
            System.out.println("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    /*20-aug-19 pragna for display notification in popup */
    private Dialog notificationDialog;
    public static Boolean DISPLAY = true;

    public void showNotificationDialog(boolean cancable, String notification) {
        System.out.println("THIS IS CANCABLE :::: " + cancable + "");
        notificationDialog = new Dialog(Activity_Home.this);
        notificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        notificationDialog.setContentView(R.layout.notification_dialog);
//        notificationDialog.setCancelable(false);
        notificationDialog.setCancelable(true);
        TextView txtTitle = (TextView) notificationDialog.findViewById(R.id.txtTitle);
//        txtTitle.setText(getString(R.string.update_dilaog));
//        txtTitle.setText(notification);
        Spanned sp = Html.fromHtml(notification);
        txtTitle.setText(sp);
        //txtTitle.setText(Html.fromHtml(notification+"")+"");

        txtTitle.setScroller(new Scroller(Activity_Home.this));
        txtTitle.setVerticalScrollBarEnabled(true);
        txtTitle.setMovementMethod(new ScrollingMovementMethod());
        Button btnPositive = (Button) notificationDialog.findViewById(R.id.btnPositive);
        Button btnNegative = (Button) notificationDialog.findViewById(R.id.btnNegative);
        /*17-oct Pragna */
       /* if (!cancable) {
            btnNegative.setVisibility(View.INVISIBLE);
        } else {
            btnNegative.setVisibility(View.VISIBLE);
        }*/
        btnPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
               /* final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    //start Play store
                    exitDialog.dismiss();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

                } catch (android.content.ActivityNotFoundException anfe) {
                    //start browser where playstore not install
                    exitDialog.dismiss();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }*/
                // if (notificationDialog != null && notificationDialog.isShowing()) {
                notificationDialog.dismiss();
                // }
                DISPLAY = false;
                Intent intent = new Intent(Activity_Home.this, NotificationListActivity.class);
                startActivity(intent);
            }
        });
        btnNegative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                notificationDialog.cancel();
                return;
            }
        });
        try {
            if (notificationDialog != null && notificationDialog.isShowing()) {
                notificationDialog.dismiss();
            }
        } catch (Exception ex) {
        }
        notificationDialog.show();
    }

    private void initView() {
        queue = Volley.newRequestQueue(this);
        mTxtTitleTourPlanning = (TextView) findViewById(R.id.txt_title_Tour_planning);
        mLinearTourPlanning = (LinearLayout) findViewById(R.id.linear_Tour_planning);
        mLinearTourPlanning.setOnClickListener(this);
        mTxtTitleNewRetailer = (TextView) findViewById(R.id.txt_title_new_retailer);
        mLinearNewRetailer = (LinearLayout) findViewById(R.id.linear_new_retailer);
        mLinearNewRetailer.setOnClickListener(this);
        linear_leave_application = findViewById(R.id.linear_leave_application);
        linear_leave_application.setOnClickListener(this);
        linear_leave_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_for_hr();
            }
        });
        llViewNewsOrNotification = findViewById(R.id.llViewNewsOrNotification);
        llViewNewsOrNotification.setOnClickListener(this);
        llViewNewsOrNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewNewsOrNotification = new Intent(Activity_Home.this, ViewNewsOrNotificationListActivity.class);
                viewNewsOrNotification.putExtra("title_screen", "View News or Notification");
                startActivity(viewNewsOrNotification);
            }
        });
    }


    void login_for_hr() {
        SharedPreferences sharedPreferences;

        sharedPreferences = getSharedPreferences("Login_master",
                MODE_PRIVATE);


        //if (pref.equals("true")){
        //  mEdtUname.setText(getSharedPref.getUserName() + "");
        //  mEdtPassword.setText(getSharedPref.getLoginLoginUserPassword() + "");
        String PASSWORD = String.valueOf(getSharedPref.getLoginLoginUserPassword() + "");
        String pssword = PASSWORD;
        /*encrypt password for special characters allowed ***** 27aug 2019 nirali*/
        try {
            pssword = URLEncoder.encode(pssword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DialogUtils.showProgressDialog(Activity_Home.this, "");
//        String url = URLS.LoginCheck + "&userName=" + edtuname.getText().toString() + "&passWord=" + edtpassword.getText().toString() + "";
        String url = URLS.LoginCheck + "&userName=" + getSharedPref.getUserName() + "" + "&passWord=" + pssword + "";
        url.replace(" ", "%20");

        System.out.println("LoginCheck URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogUtils.hideProgressDialog();

                System.out.println("response of LoginCheck !!!!!!!!!!! " + response);
                response = response + "";
                if (response.length() > 5) {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response LoginCheck !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    LoginPojo loginPojo = gson.fromJson(response, LoginPojo.class);
                    if (loginPojo != null) {
                        if (loginPojo.getData() != null) {
                            if (loginPojo.getData().get(0) != null) {
                                if (loginPojo.getData().size() > 0) {
                                    if (loginPojo.getData().get(0).getStatus().contentEquals("1")) {
                                        //DialogUtils.Show_Toast(LoginActivity.this,"Login Sucessfully");
                                        //********* store login data of user ****************
                                        getSharedPref.storeLoginData(loginPojo.getData().get(0).getStatus() + "", loginPojo.getData().get(0).getUsrm_id() + "", loginPojo.getData().get(0).getEmp_code() + "", loginPojo.getData().get(0).getUsrm_name() + "", loginPojo.getData().get(0).getUsrm_dis_name() + "", loginPojo.getData().get(0).getComp_id() + "", loginPojo.getData().get(0).getUsrm_brm_id() + "", loginPojo.getData().get(0).getCom_name() + "", loginPojo.getData().get(0).getFin_year() + "", loginPojo.getData().get(0).getFin_id() + "", loginPojo.getData().get(0).getFin_start_date() + "", loginPojo.getData().get(0).getFin_end_date() + "", loginPojo.getData().get(0).getEmp_id() + "", loginPojo.getData().get(0).getDepartment() + "", loginPojo.getData().get(0).getReportingto() + "", loginPojo.getData().get(0).getUserphoto() + "", loginPojo.getData().get(0).getDesignation() + "", loginPojo.getData().get(0).getBranch() + "", loginPojo.getData().get(0).getFullName() + "");

                                        Intent payroll_intent = new Intent(Activity_Home.this, com.infinity.infoway.vimal.kich_leave_module.Leave.Activity.MainActivity.class);
                                        startActivity(payroll_intent);
                                        // finish();

                                    } else {
                                        //  DialogUtils.Show_Toast(LoginActivity.this,"Invalid UserName/Password");
                                    }
                                }


                            }
                        }
                    }
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(Activity_Home.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

}
