package com.infinity.infoway.vimal.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.infinity.infoway.vimal.BuildConfig;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Home;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.Request_Insert_Location_Sync;
import com.infinity.infoway.vimal.api.response.AddAttendanceResponse;
import com.infinity.infoway.vimal.api.response.InsertLocationSyncResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.service.JobScheduledService;
import com.infinity.infoway.vimal.service.LocationUpdateForegroundService;
import com.infinity.infoway.vimal.service.OverLayTrackingService;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;
import com.infinity.infoway.vimal.util.common.PhotoHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//public class AddAttendace extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, Camera.PictureCallback {

public class AddAttendace extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, Camera.PictureCallback {


    private OnAddAttendanceInteractionListener mListener;

    private String location_city = "";
    private String location_district = "";
    private String location_taluk = "";
    private String location_state = "";
    private String location_country = "";
    private Double api_latitude = 0.0, api_longitude = 0.0;
    private int gps_flag = 0;

    private SharedPref getSharedPref;


    private CardView card_punch_in, card_punch_out;
    private TextView txt_punch_in, txt_punch_out, txt_punch_in_message, txt_punch_out_message;
    private FrameLayout frm_add_attendance;
    private Context context;
    private TextClock txt_add_attendance_time;
    private ProgressDialog progDialog;

    private String LocationAddress = "";

    private Date today;
    SimpleDateFormat sdf_full, sdf_full_server, time_formater, serverDateFormat;

    //    private LocationRequest mLocationRequest;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private Location curLocation;
    private Snackbar addAttendanceSnackBar;


    private SweetAlertDialog dialogSuccess;
    private int res_add_attendance_id;
    private String res_add_attendance_message;
    private boolean isPunchInClick = false;
    private boolean isPunchOutClick = false;
    private Dialog bottomSheetDialog;
    private Button btnConfirmAddAttendance, btnCancelAttendance;
    private boolean statusOfGPS;
    private LocationManager manager;
    private TextView txt_punch_in_out_title;

    // Location sending data
    private DBConnector dbConnector;
    private Intent batteryStatus;
    private IntentFilter ifilter;
    private SimpleDateFormat sdf;

    private ConnectionDetector cd;
    private double locAccuracy;

    private List<GPSMasterBean> gpsMasterBeanList;
    private long Status;
    private int loopCounter = 0;
    private String minLastUpdatedRecordId, maxLastUpdatedRecordId;

    // New Location Api

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;

    // A reference to the service used to get location updates.
    private LocationUpdateForegroundService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;
    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdateForegroundService.LocalBinder binder = (LocationUpdateForegroundService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    private ApiInterface apiService;
    private AlertDialog alertDialog;

    private Button btnConfirmCheckout, btnCancel;

//    private Location mCurrentLocation;

//    private Boolean mRequestingLocationUpdates;
//    private String mLastUpdateTime;

    public AddAttendace() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddAttendace newInstance() {
        AddAttendace fragment = new AddAttendace();
        return fragment;
    }

    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("CAMERA**", "Camera failed to open: " + e.toString());
                }
            }
        }

        return cam;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.e("CAMERA**", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private Camera camera;
    private int cameraId = 0;
    File dir;
    private int locationRequestCode = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_attendace, container, false);
        initControls(view);
        Button btn_test = (Button) view.findViewById(R.id.test);
        //set_time_last_data();
        test t = new test();
        //   sendAllGpsDataIFNotPunchOut("", "");
        imgAnnexureSend = view.findViewById(R.id.imgAnnexureSend);
        imgAnnexureSendDelete = view.findViewById(R.id.imgAnnexureSendDelete);
        imgAnnexureSendUpload = view.findViewById(R.id.imgAnnexureSendUpload);
//        imgAnnexureSendDelete_regular = view.findViewById(R.id.imgAnnexureSendDelete_regular);
//        imgAnnexureSendDelete_regular = view.findViewById(R.id.imgAnnexureSendDelete_regular);
        txt_annexure_file_name = view.findViewById(R.id.txt_annexure_file_name);
//* 18-dec-19 pragna for retry of location
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                    locationRequestCode);
//
//        } else {
//            // already permission granted
//            System.out.println("already permission granted!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        }



        // do we have a camera?
       /* if (!getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getActivity(), "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        } else {
            cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                Toast.makeText(getActivity(), "No front facing camera found.",
                        Toast.LENGTH_LONG).show();
            } else {
                camera = Camera.open(cameraId);
            }
        }*/

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
        imgAnnexureSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedBitmap != null) {
                    fullScreenImageDisplay();
                } else {
                    displaySnackBar("Please capture image !!!");
                }
            }
        });

        imgAnnexureSendDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBitmap = null;
                txt_annexure_file_name.setText("");
            }
        });
//        t.execute();



       /* Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),  R.drawable.t);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte [] ba = bao.toByteArray();
        String ba1= android.util.Base64.encodeToString(ba, android.util.Base64.DEFAULT);

        System.out.println("TEST "+ba1);


        Log.e("TEST", ba1);*/


//        turnGPSOff();


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.startPreview();
                camera.takePicture(null, null,
                        new PhotoHandler(getActivity()));
                getLastLocation();
                System.out.println("btn_test api_latitude >>>>>>>>>>>>>>>>>   " + api_latitude + "");
                System.out.println("btn_test api_longitude >>>>>>>>>>>>>>>>>>>>   " + api_longitude + "");

                // GPSTracker gpsTracker = new GPSTracker(getActivity());
//                if (gpsTracker.canGetLocation()) {
//                    System.out.println("btn_test gpsTracker.getLatitude() >>>>>>>>>>>>>>>>>   " + gpsTracker.getLatitude() + "");
//                    System.out.println("btn_test gpsTracker.getLongitude() >>>>>>>>>>>>>>>>>>>>   " + gpsTracker.getLongitude() + "");
//                }
                /*if (api_latitude == 0.0 && api_longitude == 0.0) {
                    //pppppppppppppppp noAddressFound();


                    try {
                        if (dialogSuccess != null && dialogSuccess.isShowing()) {
                            dialogSuccess.dismiss();
                        }
                    } catch (Exception ex) {
                    }

                    try {

                        dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                        dialogSuccess.setTitleText(getString(R.string.sorder_oops));

                        dialogSuccess.setContentText("Unable to fetch your location , either chnage your position or re-open the application or restart the gps and internet connection");

                        dialogSuccess.setCancelable(false);
                        dialogSuccess.show();
                        dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSuccess.dismissWithAnimation();
                                dialogSuccess.cancel();

                                getLastLocation();
                            }
                        });

                    } catch (Exception ex) {
                    }

                }*/


                // Remove all service and job
                /*8-aug-19 Pragna */
               /* Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
                context.stopService(lintent);

                Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
                context.stopService(overlay_intent);
                getSharedPref.setUserPunchInDate("");
                getSharedPref.setUserPunchOutDate("");
                //     getSharedPref.setUserPunchInOutFlag(response.body().getPUNCHINFLAG());

                JobScheduler jobScheduler = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.cancelAll();
                }*/
            }
        });


        initControls(view);
        curLocation = new Location(LocationManager.GPS_PROVIDER);
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        checkPlayServices();
        buildGoogleApiClient();
        createLocationRequest();
        locationReq();
        MapsInitializer.initialize(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.viewLocationMap);
        //   mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);
//        return view;

        return view;
    }
    private LocationRequest locationRequest1;
    private LocationCallback locationCallback1;
    private void locationReq() {
        locationRequest1 = LocationRequest.create();
        locationRequest1.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest1.setInterval(20 * 1000);
        locationCallback1 = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        curLocation=location;
                        System.out.println("curLocation locationReq  !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");
                        MapRefresh();
                        fetchGPSDetails();
                       // txtLocation.setText(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));
                    }
                }
            }
        };
    }


    /*21-nov pragna for adding map zoom to full screen and for navigation*/
    ImageView imgFullScreenSchedule, imgNavigation, imgAnnexureSendUpload_regular;
//    LinearLayout lin;
    ScrollView lin;
    private boolean MapOpenClose = false;

    private void initControls(View view) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        mService = new LocationUpdateForegroundService();
        dbConnector = new DBConnector(context);
        /*21-nov pragna for adding map zoom to full screen and for navigation*/
        imgFullScreenSchedule = (ImageView) view.findViewById(R.id.imgFullScreenSchedule);
        imgNavigation = (ImageView) view.findViewById(R.id.imgNavigation);
//        lin = (LinearLayout) view.findViewById(R.id.lin);
        lin = (ScrollView) view.findViewById(R.id.lin);


        cd = new ConnectionDetector(context);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = context.registerReceiver(null, ifilter);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mSettingsClient = LocationServices.getSettingsClient(context);


        myReceiver = new MyReceiver();

        // createLocationCallback();
        // createLocationRequestNewApi();
        // buildLocationSettingsRequest();


        getSharedPref = new SharedPref(context);
        getSharedPref.SET_SHOULD_CALL_CON_API(true);
        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        sdf_full_server = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time_formater = new SimpleDateFormat("HH:mm");

        frm_add_attendance = view.findViewById(R.id.frm_add_attendance);

        txt_add_attendance_time = view.findViewById(R.id.txt_add_attendance_time);

        txt_punch_in = view.findViewById(R.id.txt_punch_in);
        txt_punch_out = view.findViewById(R.id.txt_punch_out);

        txt_punch_in_out_title = view.findViewById(R.id.txt_punch_in_out_title);


        txt_punch_in_message = view.findViewById(R.id.txt_punch_in_message);
        txt_punch_out_message = view.findViewById(R.id.txt_punch_out_message);

        card_punch_in = view.findViewById(R.id.card_punch_in);
        card_punch_out = view.findViewById(R.id.card_punch_out);

        card_punch_in.setOnClickListener(this);
        card_punch_out.setOnClickListener(this);
        getTillDataTime();
        if (isTodayPunchINDone()) {
            //punch in date and time show on button

            txt_punch_in_message.setText(getSharedPref.getUserPunchInDate());
            card_punch_in.setEnabled(false);
            punchInMessageValidate();

        }


        if (isTodayPunchOutDone()) {
            //punch in date and time show on button
            txt_punch_out_message.setText(getSharedPref.getUserPunchOutDate());
            card_punch_out.setEnabled(false);
            punchInMessageValidate();
        }

        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        getSharedPref.SET_SHOULD_CALL_API(true);
        getLastLocation();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkDrawOverlayPermission();
        }
        /*21-nov pragna for adding map zoom to full screen and for navigation*/
        //map full screen button
        imgFullScreenSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MapOpenClose == false) {
                    imgFullScreenSchedule.setImageResource(R.drawable.ic_action_fullscreen_exit);
                    //listViewLocation.setVisibility(View.GONE);
                    lin.setVisibility(View.GONE);

                    MapOpenClose = true;
                } else {
                    imgFullScreenSchedule.setImageResource(R.drawable.ic_action_fullscreen);
                    //listViewLocation.setVisibility(View.VISIBLE);
                    lin.setVisibility(View.VISIBLE);

                    MapOpenClose = false;
                }
            }
        });
        // readGPSSettings();
    }

    private void launchMainService() {

        Intent svc = new Intent(context, OverLayTrackingService.class);
        context.stopService(svc);
        context.startService(svc);


    }

    private void showOverLasysSettingsScreenCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            showOverlay();
        } else {
            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
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


    public final static int REQUEST_CODE = 10101;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {

        // Checks if app already has permission to draw overlays
        if (!Settings.canDrawOverlays(context)) {

            // If not, form up an Intent to launch the permission request
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));

            // Launch Intent, with the supplied request code
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            // Double-check that the user granted it, and didn't just dismiss the request
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(context)) {

                    // Launch the service
                    //launchMainService();
                } else {
                    Toast.makeText(context, "Please grant permission for well usage of app...", Toast.LENGTH_LONG).show();
                    showOverLasysSettingsScreenCall();
                }
            } else {
                // launchMainService();
            }
        }

        else if ((requestCode == CAMERA_REQUEST_PUNCH_IN || requestCode == CAMERA_REQUEST_PUNCH_OUT) && resultCode == Activity.RESULT_OK) {

            try {


                photoFile = saveBitmapToFile(photoFile);


//                RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), photoFile);
                RequestBody mFile = RequestBody.create(MediaType.parse("application*//*"), photoFile);

                fileToUploadPassport = MultipartBody.Part.createFormData("file", photoFile.getName(), mFile);

//                System.out.println("this is file name11111111111 " + imageNameDisplay + "");
                //filenamePassport = RequestBody.create(MediaType.parse("text/plain"), photoFile.getName());

                if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())) {
                   // photoFile = saveBitmapToFile(photoFile);

                  //  RequestBody mFile = RequestBody.create(MediaType.parse("image*//*"), photoFile);
                 //   System.out.println("this is file name11111111111 " + imageNameDisplay + "");
                 //   fileToUploadPassport = MultipartBody.Part.createFormData("file", photoFile.getName(), mFile);
                    imageNameDisplay = photoFile.getName();
                    System.out.println("this is file name****** " + imageNameDisplay + "");
                    System.out.println("this is file SIZE****** " + photoFile.getUsableSpace() + "");

                    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File folder1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    folder.mkdirs();

//storeBitmap();




                    // txt_annexure_file_name_regular.setText(imageNameDisplay);
//                    imgAnnexureSendDelete_regular.setVisibility(View.VISIBLE);
                    txt_annexure_file_name.setText(imageNameDisplay);
                    imgAnnexureSendDelete.setVisibility(View.VISIBLE);
                    if (requestCode == CAMERA_REQUEST_PUNCH_IN) {
                        sendingLocationData(tilldate, "0");
                    } else if (requestCode == CAMERA_REQUEST_PUNCH_OUT) {
                        sendingLocationData(tilldate, "1");
                    }

                } else {
                    imageNameDisplay = "";
                }

              /*  try{
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    imgAnnexureSend.setImageBitmap(bmp);
                }catch (Exception ex){
                    ex.printStackTrace();
                }*/


                //imgPassPortPhotoUploadApiCall();

            } catch (Exception ex) {
                Log.d("TAG", ex.getMessage());
                Toast.makeText(context, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String imageNameDisplay = "";
    private MultipartBody.Part fileToUploadPassport=null;

    private void punchInMessageValidate() {
        txt_punch_in_out_title.setText("Today's Punch In - Out History ( " + sdf_full.format(new Date()) + " )");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int value) {
        if (mListener != null) {
            mListener.onFragmentInteraction(value);
        }
    }

    String tilldate = "";

    @Override
    public void onClick(View v) {
        statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        getLastLocation();
        switch (v.getId()) {
            case R.id.card_punch_in:
                System.out.println("punch-in called!!!!!!!!!!");
                getLastLocation();
                //api_latitude=0.0;
                if (cd != null && cd.isConnectingToInternet()) {
                    if (statusOfGPS) {
                        System.out.println("this is status");

//                        if (api_latitude <= 0&&curLocation.getAccuracy() <= Config.ACCURACY) {
//                        if (api_latitude <= 0&&locAccuracy <= Config.ACCURACY) {
                        System.out.println("api_latitude " + api_latitude + "");
                        System.out.println("api_long " + api_longitude + "");
                        if (api_latitude <= 0) {
                            getLastLocation();
                        }

                        if (api_latitude <= 0) {


                            try {
                                if (dialogSuccess != null && dialogSuccess.isShowing()) {
                                    dialogSuccess.dismiss();
                                }
                            } catch (Exception ex) {
                            }

                            getLastLocation();
                            noAddressFound();

                        } else {

                            if (!curLocation.isFromMockProvider()) {

                          /*  try {
                                dbConnector.deleteGPSData();
                            } catch (Exception e) {

                            }*/
                                if (getSharedPref != null) {
                                    getSharedPref.SET_LAST_GPS_STOPED_TIME("");
                                    getSharedPref.SET_LAST_SERVICE_WORKED_TIME("");
                                }

                                context.bindService(new Intent(getActivity(), LocationUpdateForegroundService.class), mServiceConnection,
                                        Context.BIND_AUTO_CREATE);

                                isPunchInClick = true;
                                isPunchOutClick = false;

                                //addAttendanceApiCall("0");
//                            LocationAddress="";
                                /*28-aug-19 pragna send all data till punch-in if not done punch-out */
                          /*  if (LocationAddress == null || LocationAddress.contentEquals("")) {
                                System.out.println("we are not able to get address");
                                getAddressFromApi(api_latitude, api_longitude, "0");//will get address and send attendance with 0
                            } else {
                                addAttendanceApiCall("0");
                            }*/


                                tilldate = getSharedPref.getSHOULD_PUNCHOUT_DATE_TIME() + "";
                                if (tilldate.contentEquals("")) {
                                    try {
                                     /*   today = new Date();
                                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                        tilldate = sdf.format(today) + "";*/
                                        dbConnector.deleteGPSData();


                                    } catch (Exception e) {
                                        System.out.println("this is error in to date!!!!!! ");
                                        e.printStackTrace();
                                    }
                                    System.out.println("this is sending location data for punch-in!!!!!!!!!!!!!!!!!!! tilldate " + tilldate + "");
                                    //   dbConnector.deleteGPSData();

//                                sendingLocationData(tilldate, "0");
                                }
                                /**ppppppppppppppppppppppppppp   sendingLocationData(tilldate, "0");*/
                                imgPassPortPhotoTpload("0");


                            } else {
                                displaySnackBar("Wrong Location!!!!!");
                            }
                        }

                    } else {
                        displaySnackBar(getResources().getString(R.string.title_valid_gps_start));
                    }
                } else {
                    displaySnackBar(getResources().getString(R.string.title_no_internet));
                    System.out.println("no internet connection!!!!");
                }
                break;
            case R.id.card_punch_out:

                if (isTodayPunchINDone()) {
                    if (cd != null && cd.isConnectingToInternet()) {
                        if (statusOfGPS) {

                            if (api_latitude <= 0) {
                                getLastLocation();
                            }

                            if (api_latitude <= 0) {


                                try {
                                    if (dialogSuccess != null && dialogSuccess.isShowing()) {
                                        dialogSuccess.dismiss();
                                    }
                                } catch (Exception ex) {
                                }

                                try {
                                  /*  dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                    dialogSuccess.setTitleText(getString(R.string.sorder_oops));

                                    //   dialogSuccess.setContentText("Unable to fetch your location , either chnage your position or re-open the application or restart the gps and internet connection");
                                    *//*  dialogSuccess.setContentText("Sorry! We are not able to get your current location. Please fetch location by opening google map or share your whatsapp location!");*//*
                                    dialogSuccess.setContentText("Sorry! We are not able to get your current location. Please open google map or share your whatsapp location!");

                                    dialogSuccess.setCancelable(false);
                                    dialogSuccess.show();
                                    dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                getLastLocation();
                                            }
                                            catch (Exception e)
                                            {

                                            }
                                            dialogSuccess.dismissWithAnimation();
                                            dialogSuccess.cancel();

                                        }
                                    });*/
                                    getLastLocation();
                                    noAddressFound();

                                } catch (Exception ex) {
                                }

                            } else {
                                isPunchOutClick = true;
                                isPunchInClick = false;


                                //pppppppppppppppppppppppppppppppppppppppppp  addAttendanceApiCall("1");


                                //pragna #################################################
                                gpsMasterBeanList = new ArrayList<>();

                                try {

                                    gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");
                                } catch (Exception ex) {
                                    System.out.println("error in to getting data from local table:::::::::::::: " + ex.getMessage() + "\n\n DUE TO : " + ex.getCause());
                                    ex.printStackTrace();

                                }


                                //Log.e("gpsMasterBeanList",""+gpsMasterBeanList.size());

                                if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
                                    System.out.println("sending location details::::::  ");


                                    String tilldate = "";
                                    tilldate = getSharedPref.getSHOULD_PUNCHOUT_DATE_TIME() + "";
                                    if (tilldate.contentEquals("")) {
                                        try {
                                           /* sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                            today = new Date();
                                            tilldate = sdf.format(today) + "";*/
                                            dbConnector.deleteGPSData();

                                        } catch (Exception e) {
                                            System.out.println("this is error in to date format ");
                                        }
                                    }

                                   sendingLocationData(tilldate, "1");

                                  /*  imgPassPortPhotoTpload("1");*/
                                } else {
                                    System.out.println("doing punchout :::::: no data in to local table  ");
                                    System.out.println("now punchout apI FINALLY calls ::::::::::::::::::::::: ");



                                    /*pragna added code */

                                    // Remove all service and job
                                    try {
                                        getSharedPref.setUserPunchInDate(String.valueOf(""));
                                        getSharedPref.setUserPunchOutDate(String.valueOf(""));

                                        System.out.println("now deleting data333333333333333333 ");

                                        /*29-aug-19 pragna after sending location tilldate data delete remaining data  */
                                        dbConnector.deleteGPSData();
                                        Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
                                        context.stopService(lintent);

                                        Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
                                        context.stopService(overlay_intent);


                                        JobScheduler jobScheduler = null;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                                            jobScheduler.cancelAll();
                                        }
                                    } catch (Exception e) {
                                        System.out.println("service stop 1 ");
                                    }
                                    /*20-aug Pragna */
                                    // addAttendanceApiCall("1");
                                    if (LocationAddress == null || LocationAddress.contentEquals("")) {
                                        System.out.println("we are not able to get address at punchout");
                                        getAddressFromApi(api_latitude, api_longitude, "1");
                                    } else {
                                   //     jgjghj
                                        imgPassPortPhotoTpload("1");
                                     /**   ///addAttendanceApiCall("1");*/
                                    }
                                }

//pragna #################################################  addAttendanceApiCall("1");
                            }

                        } else {
                            displaySnackBar(getResources().getString(R.string.title_valid_gps_start));
                        }
                    } else {
                        displaySnackBar(context.getResources().getString(R.string.title_no_internet));
                    }
                } else {
                    displaySnackBar(getString(R.string.title_do_punch_in));
                }

                break;

        }
    }


    private boolean isTodayPunchINDone() {
        if (TextUtils.isEmpty(getSharedPref.getUserPunchInDate())) {
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {

    }


    public class test extends AsyncTask<Void, Void, Void> {
        String punchinData = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            punchinData = getSharedPref.getUserPunchInDate();
            if (punchinData.contains("PUNCH IN")) {
                punchinData = punchinData.replace("PUNCH IN:", "");

            }
            if (punchinData.contains("punch in")) {

                punchinData = punchinData.replace("punch in:", "");

            }
            if (punchinData.contains("PM")) {
                punchinData = punchinData.replace("PM", "");

            }
            if (punchinData.contains("AM")) {
                punchinData = punchinData.replace("AM", "");

            }
            if (punchinData.contains("pm")) {
                punchinData = punchinData.replace("pm", "");

            }
            if (punchinData.contains("am")) {
                punchinData = punchinData.replace("am", "");

            }
            if (punchinData.contains("  ")) {
                punchinData = punchinData.replace("  ", " ");

            }
            System.out.println("this is final string " + punchinData + ""); // i am having 20-08-2019  6:05:24  //28-08-2019 3:31:01
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hhmmss");
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String inputDateStr="2013-06-24";
            String inputDateStr = punchinData + "";
            Date date = null;
            try {
                date = inputFormat.parse(inputDateStr);
            } catch (ParseException e) {
                System.out.println("EROOR!!!!!!!!!!!!!!!!!");
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            System.out.println("this is final string " + outputDateStr + "");
            super.onPostExecute(aVoid);
        }
    }

    public void getTillDataTime() {
        String punchinData = "";
        String workTime = "";
        String shouldPunchOutTime = "";
        Date workingTime = null;
        punchinData = getSharedPref.getUserPunchInDate();
        workTime = getSharedPref.getOFFICE_WORK_TIME();
        if (!punchinData.contentEquals("")) {
            if (punchinData.contains("PUNCH IN")) {
                punchinData = punchinData.replace("PUNCH IN:", "");

            }
            if (punchinData.contains("punch in")) {
                punchinData = punchinData.replace("punch in:", "");

            }
            if (punchinData.contains("PM")) {
                punchinData = punchinData.replace("PM", "");

            }
            if (punchinData.contains("AM")) {
                punchinData = punchinData.replace("AM", "");

            }
            if (punchinData.contains("pm")) {
                punchinData = punchinData.replace("pm", "");

            }
            if (punchinData.contains("am")) {
                punchinData = punchinData.replace("am", "");

            }
            if (punchinData.contains("  ")) {
                punchinData = punchinData.replace("  ", " ");

            }
            if (workTime.contains("  ")) {
                workTime = workTime.replace("  ", " ");

            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            punchinData = sdf.format(new Date());
            System.out.println("this is from else condition!!!!!!");
        }

        System.out.println("this is final string " + punchinData + "");//28-08-2019 3:31:01
//        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hhmmss");
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat onlydateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        String inputDateStr="2013-06-24";
        String inputDateStr = punchinData + "";
        Date date = null;
        try {
            inputDateStr = inputDateStr.trim();
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            System.out.println("EROOR1111!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
//        String outputDateStr = outputFormat.format(date);
        String outputDateStr = onlydateFormat.format(date);
        System.out.println("this is final string " + outputDateStr + "");
        if (workTime.length() > 5) {
            shouldPunchOutTime = outputDateStr + " " + workTime + "";
            System.out.println("final string is shouldPunchOutTime " + shouldPunchOutTime + "");
            getSharedPref.SETSHOULD_PUNCHOUT_TIME(shouldPunchOutTime + "");

        }/*30-aug-19 pragna */ else {//do till 11:59pm
            shouldPunchOutTime = outputDateStr + " " + "23:59:00" + "";//19:00:00
            System.out.println("final string is shouldPunchOutTime " + shouldPunchOutTime + "");
            getSharedPref.SETSHOULD_PUNCHOUT_TIME(shouldPunchOutTime + "");
        }

    }
    /*public void set_time_last_data() {
        //    <string name="USER_PUNCH_IN_DATE_TIME">PUNCH IN:20-08-2019  6:05:24 PM</string>
        String punchinData = getSharedPref.getUserPunchInDate();
        if (punchinData.contains("PUNCH IN")) {
            punchinData = punchinData.replace("PUNCH IN:", "");

        }
        if (punchinData.contains("PM")) {
            punchinData = punchinData.replace("PM", "");

        }
        if (punchinData.contains("AM")) {
            punchinData = punchinData.replace("AM", "");

        }
        if (punchinData.contains("  ")) {
            punchinData = punchinData.replace("  ", " ");

        }
        System.out.println("this is final string "+punchinData+""); // i am having 20-08-2019  6:05:24
//want in to 2019-08-20 18:10:20


        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");//=>20190820_182700
       // SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HHmmss");//=>20190820_182700
     //   String currentDateandTime = sdf.format(new Date());
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hhmmss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String inputDateStr="2013-06-24";
        String inputDateStr=punchinData+"";
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            System.out.println("EROOR!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        System.out.println("this is final string "+outputDateStr+"");
    }*/

    public boolean isTodayPunchOutDone() {
        if (TextUtils.isEmpty(getSharedPref.getUserPunchOutDate())) {
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddAttendanceInteractionListener) {
            mListener = (OnAddAttendanceInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void fetchGPSDetails() {
        if (curLocation != null && curLocation.getLatitude() > 0.0 && curLocation.getLongitude() > 0.0) {
            api_latitude = curLocation.getLatitude();
            api_longitude = curLocation.getLongitude();
            locAccuracy = curLocation.getAccuracy();
            getAddress(curLocation.getLatitude(), curLocation.getLongitude());
        }
    }


    public void addAttendanceApiCall(final String punch_in_out) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //Login request


                if (progDialog != null && progDialog.isShowing()) {
                    progDialog.dismiss();
                }

                try {
                    progDialog = new ProgressDialog(context);
                    progDialog.setIndeterminate(true);
                    progDialog.setMessage(getResources().getString(R.string.processing_request));
                    progDialog.setCancelable(false);
                    progDialog.show();
                } catch (Exception ex) {
                }
                RequestBody AppVersionCode = RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(getSharedPref.getAppVersionCode()));
                RequestBody AppAndroidId = RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(getSharedPref.getAppAndroidId()));

//        RequestBody Type_api = RequestBody.create(MediaType.parse("text/plain"), Type);
                RequestBody reg_id = RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(getSharedPref.getRegisteredId()));
                RequestBody reg_user_id = RequestBody.create(MediaType.parse("text/plain"),  String.valueOf(getSharedPref.getRegisteredUserId()));
                RequestBody req_key = RequestBody.create(MediaType.parse("text/plain"), Config.ACCESS_KEY);
                RequestBody req_company_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getCompanyId());
                RequestBody req_branch_id = RequestBody.create(MediaType.parse("text/plain"), getSharedPref.getBranchId());
                RequestBody req_emp_code = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody req_loc_lat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(api_latitude));
                RequestBody req_loc_lng = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(api_longitude));
                RequestBody req_gps_flag = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(isGPSON()));
                RequestBody req_loc_address = RequestBody.create(MediaType.parse("text/plain"), LocationAddress);
                RequestBody req_loc_city = RequestBody.create(MediaType.parse("text/plain"), location_city);
                RequestBody req_loc_dis = RequestBody.create(MediaType.parse("text/plain"), location_district);
                RequestBody req_loc_taluk = RequestBody.create(MediaType.parse("text/plain"), location_taluk);
                RequestBody req_loc_state = RequestBody.create(MediaType.parse("text/plain"), location_state);
                RequestBody req_loc_country = RequestBody.create(MediaType.parse("text/plain"), location_country);
                RequestBody req_punch_in_out = RequestBody.create(MediaType.parse("text/plain"), punch_in_out); RequestBody req_gps = RequestBody.create(MediaType.parse("text/plain"), isGPSON());

                Call<AddAttendanceResponse> call = apiService.Add_Attendance(
                       AppVersionCode,
                     AppAndroidId,
                      reg_id,
                        reg_user_id,
                        req_key,
                        req_company_id,
                        req_branch_id,
                        req_emp_code,
                        req_loc_lat,
                        req_loc_lng,
                        req_loc_address,
                        req_loc_city,
                        req_loc_dis,
                        req_loc_taluk,
                        req_loc_state,
                        req_loc_country,
                        req_punch_in_out,
                        req_gps, fileToUploadPassport);


               /* Call<AddAttendanceResponse> call = apiService.Add_Attendance(
                        String.valueOf(getSharedPref.getAppVersionCode()),
                        getSharedPref.getAppAndroidId(),
                        String.valueOf(getSharedPref.getRegisteredId()),
                        String.valueOf(getSharedPref.getRegisteredUserId()),
                        Config.ACCESS_KEY,
                        getSharedPref.getCompanyId(),
                        getSharedPref.getBranchId(),
                        "",
                        String.valueOf(api_latitude),
                        String.valueOf(api_longitude),
                        LocationAddress,
                        location_city,
                        location_district,
                        location_taluk,
                        location_state,
                        location_country,
                        punch_in_out,
                        isGPSON(), fileToUploadPassport);*/


                call.enqueue(new Callback<AddAttendanceResponse>() {
                    @Override
                    public void onResponse(Call<AddAttendanceResponse> call, final Response<AddAttendanceResponse> response) {
                        System.out.println("add attendance api call !!!!!! " + call.request());
                        System.out.println("add attendance api call00 !!!!!! " + call.toString());
                        System.out.println("add attendance api call11 !!!!!! " + call);
                        System.out.println("add attendance api response !!!!!! " + response);
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }

                        if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                            try {
                                if (dialogSuccess != null && dialogSuccess.isShowing()) {
                                    dialogSuccess.dismiss();
                                }
                                writeDataInLogFile("ATTENDANCE::: ","ACTION ? "+punch_in_out+"\napi_latitude " +api_latitude+"" + "\napi_longitude "+api_longitude+    " \nRESPONSE "+response);
                            } catch (Exception ex) {
                            }


                            try {
                                if (response.body().getFLG() == 1) {
                                    dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                                    dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
                                } else {
                                    dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                    dialogSuccess.setTitleText(getString(R.string.sorder_oops));
                                }

                                dialogSuccess.setContentText(response.body().getMSG());
                                dialogSuccess.setCancelable(false);
                                dialogSuccess.show();
                                dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismissWithAnimation();
                                        dialogSuccess.cancel();


                                        if (!isTodayPunchINDone() && isPunchInClick == true && response.body().getFLG() > 0) {
                                            if (!TextUtils.isEmpty(response.body().getPUNCHDNT())) {
                                                getSharedPref.setUserPunchInDate(response.body().getPUNCHDNT());
                                                txt_punch_in_message.setText(response.body().getPUNCHDNT());
                                                card_punch_in.setEnabled(false);
                                                System.out.println("now deleting data1111 ");

                                                /*29-aug-19 pragna after sending location tilldate data delete remaining data  */
                                                dbConnector.deleteGPSData();
                                            }


                                            if (response.body().getFLG() == 1) {

                                                //error ########################################         getSharedPref.setPreviousLocation("0.0", "0.0");

                                                punchInMessageValidate();

                                                //scheduleJob();

                                                if (!checkPermissions()) {
                                                    requestPermissions();
                                                } else {
                                                    mService.requestLocationUpdates();
                                                }

                                                showOverLasysSettingsScreenCall();

                                            }

                                        } else if (!isTodayPunchOutDone() && isPunchOutClick == true && response.body().getFLG() > 0) {


                                            if (!TextUtils.isEmpty(response.body().getPUNCHDNT())) {
                                                card_punch_out.setEnabled(false);
                                                punchInMessageValidate();
                                                System.out.println("this is punch-Out@@@@@@@@@@@@@@@!!!!! ");
                                                getSharedPref.setUserPunchOutDate(response.body().getPUNCHDNT());
                                                txt_punch_out_message.setText(response.body().getPUNCHDNT());
                                                getSharedPref.setPreviousLocation("0.0", "0.0");

                                                /*pragna 28-aug-19 pragna finally stopping all the services!*/
                                                System.out.println("now punchout ap must call ::::::::::::::::::::::: ");
                                                System.out.println("now and service now will stop  ");
                                                /*12-aug pragna */
                                                getSharedPref.setUserPunchInDate("");
                                                getSharedPref.setUserPunchOutDate("");
                                                System.out.println("now deleting data22222 ");



                                                /*29-aug-19 pragna after sending location tilldate data delete remaining data  */
                                                dbConnector.deleteGPSData();


                                                // Remove all service and job


                                                Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
                                                context.stopService(lintent);

                                                Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
                                                context.stopService(overlay_intent);


                                                JobScheduler jobScheduler = null;
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                                                    jobScheduler.cancelAll();
                                                }


                                            }

                                            try {
                                                //######################################     addLocationDataEvent();
                                            } catch (Exception ex) {
                                            }


                                            // Remove all service and job

//                                            Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
//                                            context.stopService(lintent);
//
//                                            Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
//                                            context.stopService(overlay_intent);
//
//
//                                            JobScheduler jobScheduler = null;
//                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                                                jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                                                jobScheduler.cancelAll();
//                                            }
//
//                                            gpsMasterBeanList = new ArrayList<>();
//
//                                            try {
//                                                gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");
//                                            } catch (Exception ex) {
//                                            }
//
//
//                                            //Log.e("gpsMasterBeanList",""+gpsMasterBeanList.size());
//
//                                            if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
//                                                sendingLocationData();
//                                            }

                                        }

                                    }
                                });


                            } catch (Exception ex) {
                            }


                        } else {
                            displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                        }

                    }

                    @Override
                    public void onFailure(Call<AddAttendanceResponse> call, Throwable t) {
                        if (progDialog != null && progDialog.isShowing()) {
                            progDialog.dismiss();
                        }
                        displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                    }
                });


            }

        });

    }


    public interface OnAddAttendanceInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int value);
    }

    private void displaySnackBar(String message) {
        try {
            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
                addAttendanceSnackBar.dismiss();
            }
            addAttendanceSnackBar = Snackbar.make(frm_add_attendance, message, Snackbar.LENGTH_LONG);
            addAttendanceSnackBar.show();
        } catch (Exception ex) {
        }
    }


    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog((Activity) context, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public void getAddress(double lat, double lng) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                String[] locationArray = new String[1];
                LocationArray = new String[1];
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
               /* sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/

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

                if (!TextUtils.isEmpty(address.getLocality())) {
                    locationArray[0] = address.getLocality();
                    location_city = address.getLocality();
                } else {
                    locationArray[0] = "";
                    location_city = "";
                }

                if (address != null) {
                    if (!TextUtils.isEmpty(address.getCountryName())) {
                        location_country = address.getCountryName();
                    }

                    if (!TextUtils.isEmpty(address.getAdminArea())) {
                        location_state = address.getAdminArea();
                    }

                    if (!TextUtils.isEmpty(address.getSubAdminArea())) {
                        location_district = address.getSubAdminArea();
                    }


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // New Location Api


    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {


                        //noinspection MissingPermission
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        // update ui

                    }
                })
                .addOnFailureListener((Activity) context, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";


//                                mRequestingLocationUpdates = false;
                        }

                        //updateUI();
                    }
                });
    }
    /*Edited 21-nov-19 pragna for adding google map*/

    /**
     * private void createLocationRequestNewApi() {
     * mLocationRequest = new LocationRequest();
     * <p>
     * // Sets the desired interval for active location updates. This interval is
     * // inexact. You may not receive updates at all if no location sources are available, or
     * // you may receive them slower than requested. You may also receive updates faster than
     * // requested if other applications are requesting location at a faster interval.
     * mLocationRequest.setInterval(INTERVAL);
     * <p>
     * // Sets the fastest rate for active location updates. This interval is exact, and your
     * // application will never receive updates faster than this value.
     * mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
     * <p>
     * mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
     * <p>
     * }
     */

    protected void createLocationRequest() {
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
            @Override
            public void onResult(LocationSettingsResult result) {
                final com.google.android.gms.common.api.Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult(). c
                            startIntentSenderForResult(status.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                            //status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });


    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                curLocation = locationResult.getLastLocation();

                // mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                // update ui
                if (curLocation != null) {
                    fetchGPSDetails();
                }

            }
        };
    }

    private void stopLocationUpdates() {
        /*if (!mRequestingLocationUpdates) {
           // Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }*/

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // mRequestingLocationUpdates = false;

                    }
                });
    }

    private GoogleApiClient mGoogleApiClient;

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())

                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .registerOnSharedPreferenceChangeListener(this);
        buildGoogleApiClient();

        /*30-aug-19 pragna for location notgetting time of first punch-in/out*/
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }

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


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }


    @Override
    public void onResume() {
        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.


        super.onResume();


        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!statusOfGPS) {
            /** 18-dec-19 pragna **/
            showGPSDialog();
            // buildGoogleApiClient();
            // createLocationRequest();
        }
/** 18-dec-19 pragna for retry of location */
      /*  if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            // already permission granted
            System.out.println("already permission granted!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }*/
       /* if(isGooglePlayServicesAvailable()) {
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!statusOfGPS) {
                buildGoogleApiClient();
                createLocationRequest();
            }else{
                dismissAppConfigDialog();
            }
        }*/

        LocalBroadcastManager.getInstance(context).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdateForegroundService.ACTION_BROADCAST));

        // startLocationUpdates();

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(myReceiver);

        // stopLocationUpdates();
    }


    @Override
    public void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            context.unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(context)
                .unregisterOnSharedPreferenceChangeListener(this);
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdateForegroundService.EXTRA_LOCATION);
            if (location != null) {
               /* Toast.makeText(MainActivity.this, LocationUtil.getLocationText(location),
                        Toast.LENGTH_LONG).show();*/
            }
        }
    }

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {

            Snackbar.make(
                    frm_add_attendance,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
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
            }

            /**18-dec-19 pragna for check permisstion*/
            if(requestCode==1000) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                       if(location!=null)
                       {
                           System.out.println("curLocation on result !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");
                           MapRefresh();
                           fetchGPSDetails();
                       }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }




            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                //mService.requestLocationUpdates();
            } else {
                // Permission denied.

                Snackbar.make(
                        frm_add_attendance,
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
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob() {
        //900000
        final long ONE_DAY_INTERVAL = 1050000; // 10 Min

        final long ONE_DAY_INTERVAL_RESTRICTED = 1050000; // 15 Min

        ComponentName serviceComponent = new ComponentName(context, JobScheduledService.class);
        JobInfo.Builder builder = new JobInfo.Builder(10, serviceComponent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(ONE_DAY_INTERVAL_RESTRICTED);
        } else {
            builder.setPeriodic(ONE_DAY_INTERVAL);
        }

//        Log.d("Activity_Home", "job being to start!");

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(builder.build());

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
//            Log.d("Activity_Home", "Job scheduled!");
        } else {
//            Log.d("Activity_Home", "Job not scheduled");
        }
    }


    private void getLastLocation() {
        try {
            System.out.println("getlast location called!!!!!!");
           /**
            * 18-dec-19 pragna
            * mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            // if (task.isSuccessful() && task.getResult() != null) {
                            curLocation = task.getResult();
                            if (curLocation != null) {
                                System.out.println("curLocation  !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");
                                MapRefresh();
                                fetchGPSDetails();
                            }
                            // } else {
                            //  Log.e("Add Attendance", "Failed to get location.");
                            // }
                        }
                    });*/


           mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
               @Override
               public void onSuccess(Location location) {
                   if (location != null) {
                       curLocation=location;
                       System.out.println("curLocation  !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");
                       MapRefresh();
                       fetchGPSDetails();
                   }
                   // } else {
                   //  Log.e("Add Attendance", "Failed to get location.");
                   // }
               }
           });


        } catch (SecurityException unlikely) {
            Log.e("Add Attendance", "Lost location permission." + unlikely);
            unlikely.printStackTrace();
        }
    }

    private String isGPSON() {
        try {
            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (statusOfGPS) {
                return "1";
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "0";
        }

    }


    private void sendingLocationData(final String tilldateTime, final String punchInOrOut) {


        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(context);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request_attendance));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        loopCounter = 0;

        gpsMasterBeanList = new ArrayList<>();

        try {
            /*gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");*/
            gpsMasterBeanList = dbConnector.getGPSMasterData4Attendance(getSharedPref.getRegisteredUserId() + "", tilldateTime + "");
        } catch (Exception ex) {
        }


        JSONArray jsonArray = new JSONArray();
        if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
            System.out.println("sending locationdata!!!!!!^&##");
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
                    // jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? data.getGPS_Accuracy() : "0");
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

            getSharedPref = new SharedPref(context);


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
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
                    }
                    /*if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
                        if(response.body().getFLAG()==1){
                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId));
                        }
                    }else{

                    }*/

                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {

                        // Log.e("response 111",""+response.isSuccessful());

                        if (response.body().getFLAG() == 1) {


                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId), getSharedPref.getRegisteredUserId() + "");
                            //  getSharedPref.setPreviousLocation("0.0","0.0");


                            //pragna #################################################
                            // System.out.println("now punchout ap must call ::::::::::::::::::::::: ");
                            //System.out.println("now and service now will stop  ");
                            /*12-aug pragna */
                           /* getSharedPref.setUserPunchInDate("");
                            getSharedPref.setUserPunchOutDate("");
                            // Remove all service and job

                            Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
                            context.stopService(lintent);

                            Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
                            context.stopService(overlay_intent);


                            JobScheduler jobScheduler = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                                jobScheduler.cancelAll();
                            }


                            System.out.println("now punchout apI FINALLY calls ::::::::::::::::::::::: ");*/
/*                       if (LocationAddress == null || LocationAddress.contentEquals("")) {
                                System.out.println("we are not able to get address at punchout");
                                getAddressFromApi(api_latitude, api_longitude, "1");
                            } else {
                                addAttendanceApiCall("1");
                            }*/

                            /*28-aug-19 pragna sending all data from gps master till last record*/
                            System.out.println("Again calling background service!!!!!!");
                            sendingLocationData(tilldateTime, punchInOrOut);

                            // addAttendanceApiCall("1");


                        } else {
                            //Log.e("Erorr 111",response.body().getMSG());
                            try {
                                displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                            } catch (Exception e) {
                                System.out.println("error in 1 display");
                            }
                        }
                    } else {
                        //Log.e("Erorr 222",response.toString());
                        //    displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                        try {
                            displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
                        } catch (Exception e) {
                            System.out.println("error in 2 display");
                        }
                    }
                }

                @Override
                public void onFailure(Call<InsertLocationSyncResponse> call, Throwable t) {
                    if (progDialog != null && progDialog.isShowing()) {
                        progDialog.dismiss();
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
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
            }
            System.out.println("We dont found any record for gps now punchInOutApi Will call");
            getLastLocation();
          //  LocationAddress="";
            if (LocationAddress == null || LocationAddress.contentEquals("")) {
                System.out.println("we are not able to get address at punch-in/out " + punchInOrOut + "");
                getAddressFromApi(api_latitude, api_longitude, punchInOrOut + "");
            } else {
                if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())) {
                    System.out.println("now will call attendance API....");
                addAttendanceApiCall(punchInOrOut + "");
                }
                else{
                    System.out.println("not uploaded file ");
                    imgPassPortPhotoTpload(punchInOrOut+"");
                }
//                imgPassPortPhotoTpload(punchInOrOut+"");
            }
        }

    }

  /*  private void addLocationDataEvent() {
        if (dbConnector != null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            batteryStatus = context.registerReceiver(null, ifilter);

            today = new Date();
            GPSMasterBean data = new GPSMasterBean();

            data.setGPS_Location_Name("unspecified");
            data.setGPS_Latitude(String.valueOf(api_latitude));
            data.setGPS_Longitude(String.valueOf(api_longitude));

            data.setGPS_Address(LocationAddress);

            try {
                data.setGPS_Battery_Percentage(String.valueOf(calculateBatteryPercentage()));
            } catch (Exception ex) {
            }

            if (cd != null) {
                data.setGPS_Internet_Status(cd.isConnectingToInternet() ? "1" : "0");
            } else {
                data.setGPS_Internet_Status("0");
            }

            data.setGPS_Status(String.valueOf(isGPSON()));

            try {
                data.setGPS_DateTime(sdf.format(today));
            } catch (Exception ex) {
            }

            try {
                data.setGPS_Accuracy(String.valueOf(locAccuracy));
            } catch (Exception ex) {
            }

            if (api_latitude > 0 && (!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {


                Location priviusLocation = new Location("");//provider name is unnecessary
                priviusLocation.setLatitude(Double.parseDouble(getSharedPref.getPreviousLat()));//your coords of course
                priviusLocation.setLongitude(Double.parseDouble(getSharedPref.getPreviousLong()));

                Location currentLocation = new Location("");
                currentLocation.setLatitude(api_latitude);
                currentLocation.setLongitude(api_longitude);

                double distanceInMeters = currentLocation.distanceTo(priviusLocation);
                if (distanceInMeters > getSharedPref.getDefaultDistance() && (locAccuracy < Config.ACCURACY || distanceInMeters >= Config.MIN_KM_FOR_ACCURACY)) {
                    //  if(distanceInMeters>0){
                    // if (distanceInMeters > getSharedPref.getDefaultDistance()) {
                    //    data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
                    //    data.setGPS_Is_Loc_Changed("1");
                    data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 10000));
                    getSharedPref.setPreviousLocation(String.valueOf(api_latitude), String.valueOf(api_longitude));
                    data.setGPS_Is_Loc_Changed("1");
                } else {
                    data.setGPS_Km_Travelled("0.0");
                    data.setGPS_Is_Loc_Changed("0");
                }
            } else {
                data.setGPS_Km_Travelled("0.0");
                data.setGPS_Is_Loc_Changed("0");
            }


//            if (api_latitude > 0) {
//                getSharedPref.setPreviousLocation(String.valueOf(api_latitude), String.valueOf(api_longitude));
//            }


//                Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();

            boolean flag = false;

            if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                flag = dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + ""," ADD ATTNDANCE 1 ");

            } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                flag = dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + ""," ADD ATTENDACE 2 ");
            }


        }
    }*/

    private int calculateBatteryPercentage() {

        boolean present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        int battery_percentage = 0;
        if (present) {
            battery_percentage = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        }
        return battery_percentage;
    }

    private void openBottomSheetSialog(String message) {
        try {
            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_double_confirmation);
            TextView txtBottomSheetTitle = bottomSheetDialog.findViewById(R.id.txtBottomSheetTitle);
            txtBottomSheetTitle.setText(message);
            btnConfirmCheckout = bottomSheetDialog.findViewById(R.id.btnConfirmRegister);
            btnCancel = bottomSheetDialog.findViewById(R.id.btnCancelRegistration);
            btnConfirmCheckout.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.show();
        } catch (Exception ex) {
        }
    }

    private void showGPSDialog() {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context,R.style.AlertDialog);
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

    String State = "", Country = "";

    // GPSMasterBean datainner;

    //    public void getAddressFromApi(final GPSMasterBean data) {
    public void getAddressFromApi(Double api_latitude, Double api_longitude, final String isUserPunchIn) {

        //  datainner = data;

        //final String latitude = data.getGPS_Latitude();
        // final String logitude = data.getGPS_Longitude();

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        /*String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude+ "," + logitude+"&sensor=true";*/
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + api_latitude + "," + api_longitude + "&key=AIzaSyC5F3I16VdAGGX_ZqvPNWP2s0WoQ_SB7sQ";
        //writeDataInLogFile("direction url",url);
        Log.d("getAddressFromApi ", "url:" + url);
// Request a string response from the provided URL.
        if (cd != null && cd.isConnectingToInternet()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                /*https://stackoverflow.com/questions/25506154/how-to-get-only-state-name-administrative-area-level-1-in-google-geocoding-api*/
                                JSONObject jsonObj = new JSONObject(response.toString());
                                String Status = jsonObj.getString("status");
                                if (Status.equalsIgnoreCase("OK")) {
                                    System.out.println("STATUS ok ======================");
                                    JSONArray Results = jsonObj.getJSONArray("results");
                                    JSONObject zero = Results.getJSONObject(0);
                                    JSONArray address_components = zero.getJSONArray("address_components");

                                    for (int i = 0; i < address_components.length(); i++) {
                                        JSONObject zero2 = address_components.getJSONObject(i);
                                        String long_name = zero2.getString("long_name");
                                        JSONArray mtypes = zero2.getJSONArray("types");
                                        String Type = mtypes.getString(0);

                                        if (Type.equalsIgnoreCase("locality")) {
                                            location_city = long_name;
                                            location_district = long_name;


                                            Log.e("current city name:", "city:" + location_city);

                                            System.out.println("THIS IS CITY locality ???????????????? " + location_city + " ");
                                            System.out.println("THIS IS location_district locality ???????????????? " + location_district + " ");
                                        } else {
                                            if (Type.equalsIgnoreCase("administrative_area_level_2")) {
//                                            String County = long_name;
                                                location_city = long_name;
                                                location_district = long_name;

                                                /*System.out.println("THIS IS County ???????????????? " + location_city + " ");*/
                                                System.out.println("THIS IS CITY administrative_area_level_2  ???????????????? " + location_city + " ");
                                                System.out.println("THIS IS location_district administrative_area_level_2  ???????????????? " + location_district + " ");
                                                System.out.println("THIS IS Type Type ???????????????? " + Type + " ");
                                            } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                                                State = long_name;


                                                Log.e("current city name:", "administrative_area_level_1:" + State);
                                            } else if (Type.equalsIgnoreCase("country")) {
                                                Country = long_name;
                                            }
                                        }
                                    }
                                    /*29-sept*/

//                                if (location_city != null && !location_city.trim().equals("")) {
//                                    System.out.println("THIS WILL CALL WEBSERVICE FROM ADDRESS 2222");
////                                    sendData();
//                                    writeDataInLogFile(" insert ATTANDANCE WITH GOOGLE API STARTED====>>>>>>> ", apiReqestEnvelopes.addAttendance(Utils.getAppVersionCode(getContext()), Utils.getDeviceId(getContext()), 0, user_id, latitude, logitude, mySharedPreferenses.getEMPCode(), location_city, location_district, location_taluk, location_state, location_country) + "");
//                                    AddAttendance getExpenceList = new AddAttendance("addAttendance", isUserPunchIn);
//                                    getExpenceList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "addAttendance", apiReqestEnvelopes.addAttendance(Utils.getAppVersionCode(getContext()), Utils.getDeviceId(getContext()), 0, user_id, latitude, logitude, mySharedPreferenses.getEMPCode(), location_city, location_district, location_taluk, location_state, location_country));
//                                    sendData();
//                                } else {// still address not found than
//                                    System.out.println("THIS WILL NOT CALL WEBSERVICE FROM ADDRESS 2222");
//                                    noAddressFound(user_id, latitude, logitude, isUserPunchIn);
//                                }
                                    String formatted_address = zero.getString("formatted_address");
                                    System.out.println("formatted_address  " + formatted_address + "");

                                    if (formatted_address != null && !formatted_address.contentEquals("")) {
                                        //   datainner.setGPS_Address(formatted_address + "");
                                        LocationAddress = formatted_address;
                                    } else {
                                        ///  datainner.setGPS_Address(location_district + ", " + location_city + ", " + State + "");
                                        LocationAddress = location_city + "," + State + "," + Country + "";
                                    }


/**                                    addAttendanceApiCall(isUserPunchIn + "");*/

                                    if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())&&fileToUploadPassport!=null) {
                                        addAttendanceApiCall(isUserPunchIn + "");
                                        System.out.println("now attendance api will call 111111111111111111111111");
                                    }
                                    else{
                                        imgPassPortPhotoTpload(isUserPunchIn + "");
                                    }
                                    //   dbConnector.addGPSData(datainner, getSharedPref.getRegisteredUserId() + "");
                                } else {
                                    System.out.println("THIS WILL NOT CALL WEBSERVICE FROM ADDRESS 3333");
                                    System.out.println("STATUS NOT ok ======================");
                                    /**ppppppppppp addAttendanceApiCall(isUserPunchIn + "");*/
                                    if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())&&fileToUploadPassport!=null) {
                                          addAttendanceApiCall(isUserPunchIn + "");
                                        System.out.println("now attendance api will call 2222222222222222222222");
                                    }else {
                                        imgPassPortPhotoTpload(isUserPunchIn + "");
                                    }
                                }
                            } catch (JSONException e) {
                                System.out.println("THIS WILL NOT CALL WEBSERVICE FROM ADDRESS 444444");
                                //   noAddressFound(user_id, latitude, logitude, isUserPunchIn);
                                //   System.out.println("THIS IS ERROR " + e.getLocalizedMessage() + "");
                                e.printStackTrace();
                          /**      addAttendanceApiCall(isUserPunchIn + "");*/
//                                imgPassPortPhotoTpload(isUserPunchIn+"");
                                if (photoFile != null && !TextUtils.isEmpty(photoFile.getName())&&fileToUploadPassport!=null) {
                                      addAttendanceApiCall(isUserPunchIn + "");
                                    System.out.println("now attendance api will call 333333333333333333333333");
                                }else {
                                    imgPassPortPhotoTpload(isUserPunchIn + "");
                                }

                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //     dbConnector.addGPSData(datainner, getSharedPref.getRegisteredUserId() + "");
//                if (progressBar != null) {
//                    progressBar.dismiss();
//                }
                    //noAddressFound(user_id, latitude, logitude, isUserPunchIn);
                /**    addAttendanceApiCall(isUserPunchIn + "");*/
                    imgPassPortPhotoTpload(isUserPunchIn+"");
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
            //}
        } else {
            //     dbConnector.addGPSData(datainner, getSharedPref.getRegisteredUserId() + "");
        }
    }

    /*26-aug-19 pragna https://beginnersbook.com/2013/12/how-to-loop-arraylist-in-java/*/
  /*  public void sendAllGpsDataIFNotPunchOut(String tillTime, String punchInOut) {
        //   List<GPSMasterBean> data = dbConnector.getGPSMasterData4Attendance(getSharedPref.getRegisteredUserId() + "", tillTime + "");
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i < 190; i++) {
            data.add(i);
        }
        if (data != null && data.size() > 0) {
            int count = 0;

            while (data.size() > count) {
                System.out.println(data.get(count));
                List<Integer> data1;
                if (data.size() > count + 50) {
                    data1 = data.subList(count, count + 50);

                } else {
                    data1 = data.subList(count, data.size());
                }
                for (int i = 0; i < data1.size(); i++) {
                    System.out.println("INNER ::: " + data1.get(i) + "");
                }
                count = count + 50;
            }
        } else {
            //   addAttendanceApiCall(punchInOut);
        }
    }*/ private Dialog exitDialog;

    public void noAddressFound() {
        exitDialog = new Dialog(getContext());
        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exitDialog.setContentView(R.layout.logout_dialog);
        exitDialog.setCancelable(false);
        getLastLocation();
        TextView txtTitle = (TextView) exitDialog.findViewById(R.id.txtTitle);
        Button btnPositive = (Button) exitDialog.findViewById(R.id.btnPositive);
        Button btnNegative = (Button) exitDialog.findViewById(R.id.btnNegative);
        btnNegative.setVisibility(View.GONE);
        btnPositive.setText("OK");
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancell button click finish activity
                exitDialog.dismiss();

            }
        });
//        txtTitle.setText(getString(R.string.something_went_wrong));
        // txtTitle.setText(getString(R.string.stargpsmessage));
        txtTitle.setText(getString(R.string.GPS_ON_OFF));
        // btnPositive.setText(getString(R.string.retry));
        btnPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //cancell button click finish activity
                exitDialog.dismiss();
                //recall contact us  info0
             /*   if (SystemClock.elapsedRealtime() - lastClickTime3 < Constant.TIME_TILL_DISABLE_BTN) {
                    return;
                }

                lastClickTime3 = SystemClock.elapsedRealtime();*/
             try {
               //  context.bindService(new Intent(getActivity(), LocationUpdateForegroundService.class), mServiceConnection,
                      //   Context.BIND_AUTO_CREATE);

           Activity_Home.      WAS_GPS_OFF=true;
                 getActivity().finish();
              //   getLastLocation();
             }catch (Exception e)
             {
                 System.out.println("error in to first location get");
             }

                System.out.println("again calling for location!!!!!!!!!");

            }
        });

        try {
            if (exitDialog != null && exitDialog.isShowing()) {
                exitDialog.dismiss();
            }
        } catch (Exception ex) {
        }
        exitDialog.show();
    }


    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        }
    }

    private void turnGPSOff() {
        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            System.out.println("gps is on!!!!!!!!!!!!");
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        } else {
            System.out.println("gps is off!!!!!!!!!!!!");
        }
    }

    private int PLAY_SERVICES_RESOLUTION_REQUEST = 502;

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(getContext(), "This device is not supported.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    private GoogleMap mMap;
    private int Locationcounter = 0;
    private String[] LocationArray;
    Marker marker;
    private int MapRefreshCounter = 60000;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("this is connected!!!!!!!!!!!!!!");
        if (ActivityCompat.checkSelfPermission(Activity_Home.getHomeActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Activity_Home.getHomeActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        curLocation = location;
        System.out.println("before refresh !!!!!!!!!!!! " + location.getLatitude() + " lon " + location.getLongitude() + "");
        MapRefresh();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("this is map is ready!!!!!!!!!!!!!!");
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        MapRefresh();

    }

    public void MapRefresh() {
        System.out.println("on map refresh!!!!!!!!!!!!!!");
        if (curLocation.getLatitude() > 0 && curLocation.getLongitude() > 0 && mMap != null) {
            System.out.println("before after !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");

            try {

                if (marker != null) {
                    marker.remove();
                }

                getAddress(curLocation.getLatitude(), curLocation.getLongitude());

                LatLng latLng = new LatLng(curLocation.getLatitude(), curLocation.getLongitude());
                System.out.println("before after !!!!!!!!!!!! address0000 " + LocationArray[0] + "");
                System.out.println("before after !!!!!!!!!!!! address111 " + LocationAddress + "");

               /* marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(LocationArray[0])
                        .snippet(LocationAddress)  */

                marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(LocationAddress + "")
                        .snippet(LocationAddress + "")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_blue_map)));
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                // marker.showInfoWindow();

                if (Locationcounter == 0) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(
                            new LatLng(curLocation.getLatitude(), curLocation.getLongitude())).zoom(12.5f).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    System.out.println("before after1111111111111 !!!!!!!!!!!! " + curLocation.getLatitude() + " lon " + curLocation.getLongitude() + "");
                } else {

                    System.out.println("in to else!!!!!!!!!!!!!!!! " + MapRefreshCounter + "");

                }
                Locationcounter++;

                mMap.getUiSettings().setZoomControlsEnabled(true);

                MapRefreshCounter = 60000;


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("some thing is wrong!!!!!!!!!!!!!!!!!! " + MapRefreshCounter + "");
        }
    }

    /*12-dec-19 pragna for capturing selfi at time of attendance */
    private File photoFile = null;

    private void openCameraDialog(final int imageUploadFlag, String punch_in_out) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go

            String timeStamp = "ATTENDANCE_" + punch_in_out + "_" + "";
            try {
                timeStamp += new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            } catch (Exception ex) {
                timeStamp += String.valueOf(System.currentTimeMillis());
            }

            try {
                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            /*    photoFile = new File(getActivity().getExternalCacheDir(),
                        timeStamp + ".jpg");*/
                photoFile = new File(folder,
                        timeStamp + ".jpg");
            } catch (Exception ex) {
            }

           /* try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }*/
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                if (photoURI != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


                    takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);

                    if (punch_in_out.contentEquals("0")) {
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_PUNCH_IN);
                    } else {
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_PUNCH_OUT);
                    }
                } else {
                    displaySnackBar("Unable to load camera , try again !!!");
                }

            }
        }

    }

    private ImageView imgAnnexureSend, imgAnnexureSendDelete, imgAnnexureSendUpload, imgAnnexureSendDelete_regular;
    private TextView txt_annexure_file_name;


    private static final int CAMERA_REQUEST_PUNCH_IN = 1001;
    private static final int CAMERA_REQUEST_PUNCH_OUT = 1002;
    private String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private void imgPassPortPhotoTpload(String punch_in_out) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(context, RunTimePerMissions)) {
                ActivityCompat.requestPermissions(getActivity(), RunTimePerMissions, 101);
            } else {
                openCameraDialog(1, punch_in_out);
            }
        } else {
            openCameraDialog(1, punch_in_out);
        }

        //  getLastLocation();
    }

    //checking has runtime permmition or not
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private Bitmap selectedBitmap;
    private void storeBitmap(File file, Bitmap bitmap) throws Exception {
        if (!file.exists() && !file.createNewFile())
            throw new Exception("Not able to create " + file.getPath());
        FileOutputStream stream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.flush();
        stream.close();
        Toast.makeText(getActivity(), "Saved!!", Toast.LENGTH_LONG).show();
    }
    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
           /* BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 4;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

            inputStream.close();*/


            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            selectedBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);


            Bitmap tempBitmap = ProcessingBitmap(selectedBitmap);

            if (tempBitmap != null) {
                selectedBitmap = tempBitmap;
            }

            if (!cd.isConnectingToInternet()) {
                imageInByte = null;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                imageInByte = stream.toByteArray();
            } else {
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            }

            return file;
        } catch (Exception e) {
            return file;
        }
    }

    private Canvas canvas;
    private byte[] imageInByte = null;

    private Bitmap ProcessingBitmap(Bitmap bm1) {
        String captionString = "";
        try {
            captionString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        } catch (Exception ex) {
        }

        canvas = null;
        Bitmap newBitmap = null;
        try {
            Bitmap.Config config = bm1.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bm1, 0, 0, null);
            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.RED);
            paintText.setTextSize(50);
            paintText.setTextAlign(Paint.Align.RIGHT);
            paintText.setStyle(Paint.Style.FILL);
            // paintText.setShadowLayer(10f, 10f, 10f, Color.RED);
            Rect textRect = new Rect();
            paintText.getTextBounds(captionString, 0, captionString.length(), textRect);
           /* if(textRect.width() >= (canvas.getWidth() - 4))
                paintText.setTextSize(7);*/
            //int xPos = (canvas.getWidth() / 2) - 2;
            //int yPos = (int) ((canvas.getHeight() / 2) - ((paintText.descent() + paintText.ascent()) / 2));
            canvas.drawText(captionString, canvas.getWidth() - captionString.length(), canvas.getHeight() - 20, paintText);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newBitmap;
    }

    private Dialog fullScreenDialog;

    private void fullScreenImageDisplay() {

        if (fullScreenDialog != null && fullScreenDialog.isShowing()) {
            fullScreenDialog.dismiss();
        }

        try {
            fullScreenDialog = new Dialog(context);
            fullScreenDialog.setContentView(R.layout.layout_dialog_full_screen_image);
            fullScreenDialog.setCancelable(false);

            ImageView imgViewDisplay = fullScreenDialog.findViewById(R.id.imgViewDisplay);

            imgViewDisplay.setImageBitmap(selectedBitmap);

            ImageButton imgBtnClose = fullScreenDialog.findViewById(R.id.imgBtnClose);
            imgBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullScreenDialog.dismiss();
                }
            });

            fullScreenDialog.show();

        } catch (Exception ex) {
        }


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
}


