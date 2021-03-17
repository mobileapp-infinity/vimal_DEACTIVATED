//package com.infinity.infoway.davat.fragment;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.app.job.JobInfo;
//import android.app.job.JobScheduler;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.IntentSender;
//import android.content.ServiceConnection;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.BatteryManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.preference.PreferenceManager;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.widget.CardView;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.TextClock;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.location.SettingsClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.infinity.infoway.davat.BuildConfig;
//import com.infinity.infoway.davat.R;
//import com.infinity.infoway.davat.api.ApiClient;
//import com.infinity.infoway.davat.api.ApiInterface;
//import com.infinity.infoway.davat.api.request.Request_Insert_Location_Sync;
//import com.infinity.infoway.davat.api.response.AddAttendanceResponse;
//import com.infinity.infoway.davat.api.response.InsertLocationSyncResponse;
//import com.infinity.infoway.davat.config.Config;
//import com.infinity.infoway.davat.database.DBConnector;
//import com.infinity.infoway.davat.database.SharedPref;
//import com.infinity.infoway.davat.model.GPSMasterBean;
//import com.infinity.infoway.davat.service.JobScheduledService;
//import com.infinity.infoway.davat.service.LocationUpdateForegroundService;
//import com.infinity.infoway.davat.service.OverLayTrackingService;
//import com.infinity.infoway.davat.util.common.ConnectionDetector;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class AddAttendace_bk_original_punchout_api_call extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
//
//
//    private OnAddAttendanceInteractionListener mListener;
//
//    private String location_city = "";
//    private String location_district = "";
//    private String location_taluk = "";
//    private String location_state = "";
//    private String location_country = "";
//    private Double api_latitude = 0.0, api_longitude = 0.0;
//    private int gps_flag = 0;
//
//    private SharedPref getSharedPref;
//
//
//    private CardView card_punch_in, card_punch_out;
//    private TextView txt_punch_in, txt_punch_out, txt_punch_in_message, txt_punch_out_message;
//    private FrameLayout frm_add_attendance;
//    private Context context;
//    private TextClock txt_add_attendance_time;
//    private ProgressDialog progDialog;
//
//    private String LocationAddress = "";
//
//    private Date today;
//    SimpleDateFormat sdf_full, sdf_full_server, time_formater, serverDateFormat;
//
//    //    private LocationRequest mLocationRequest;
//    private static final long INTERVAL = 1000 * 30;
//    private static final long FASTEST_INTERVAL = 1000 * 15;
//    private static final int REQUEST_CHECK_SETTINGS = 0x1;
//
//    private Location curLocation;
//    private Snackbar addAttendanceSnackBar;
//
//
//    private SweetAlertDialog dialogSuccess;
//    private int res_add_attendance_id;
//    private String res_add_attendance_message;
//    private boolean isPunchInClick = false;
//    private boolean isPunchOutClick = false;
//    private Dialog bottomSheetDialog;
//    private Button btnConfirmAddAttendance, btnCancelAttendance;
//    private boolean statusOfGPS;
//    private LocationManager manager;
//    private TextView txt_punch_in_out_title;
//
//    // Location sending data
//    private DBConnector dbConnector;
//    private Intent batteryStatus;
//    private IntentFilter ifilter;
//    private SimpleDateFormat sdf;
//
//    private ConnectionDetector cd;
//    private double locAccuracy;
//
//    private List<GPSMasterBean> gpsMasterBeanList;
//    private long Status;
//    private int loopCounter = 0;
//    private String minLastUpdatedRecordId, maxLastUpdatedRecordId;
//
//    // New Location Api
//
//    private FusedLocationProviderClient mFusedLocationClient;
//    private SettingsClient mSettingsClient;
//    private LocationRequest mLocationRequest;
//    private LocationSettingsRequest mLocationSettingsRequest;
//    private LocationCallback mLocationCallback;
//
//    // The BroadcastReceiver used to listen from broadcasts from the service.
//    private MyReceiver myReceiver;
//
//    // A reference to the service used to get location updates.
//    private LocationUpdateForegroundService mService = null;
//
//    // Tracks the bound state of the service.
//    private boolean mBound = false;
//    // Used in checking for runtime permissions.
//    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
//    private final ServiceConnection mServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            LocationUpdateForegroundService.LocalBinder binder = (LocationUpdateForegroundService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//            mBound = false;
//        }
//    };
//
//    private ApiInterface apiService;
//
//
//    private Button btnConfirmCheckout, btnCancel;
//
////    private Location mCurrentLocation;
//
////    private Boolean mRequestingLocationUpdates;
////    private String mLastUpdateTime;
//
//    public AddAttendace_bk_original_punchout_api_call() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static AddAttendace_bk_original_punchout_api_call newInstance() {
//        AddAttendace_bk_original_punchout_api_call fragment = new AddAttendace_bk_original_punchout_api_call();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.context = getActivity();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_add_attendace, container, false);
//        initControls(view);
//        return view;
//    }
//
//    private void initControls(View view) {
//
//        apiService = ApiClient.getClient().create(ApiInterface.class);
//        mService = new LocationUpdateForegroundService();
//        dbConnector = new DBConnector(context);
//
//        cd = new ConnectionDetector(context);
//        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        batteryStatus = context.registerReceiver(null, ifilter);
//
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        mSettingsClient = LocationServices.getSettingsClient(context);
//
//
//        myReceiver = new MyReceiver();
//
//        // createLocationCallback();
//        // createLocationRequestNewApi();
//        // buildLocationSettingsRequest();
//
//
//        getSharedPref = new SharedPref(context);
//        sdf_full = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        sdf_full_server = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        time_formater = new SimpleDateFormat("HH:mm");
//
//        frm_add_attendance = view.findViewById(R.id.frm_add_attendance);
//
//        txt_add_attendance_time = view.findViewById(R.id.txt_add_attendance_time);
//
//        txt_punch_in = view.findViewById(R.id.txt_punch_in);
//        txt_punch_out = view.findViewById(R.id.txt_punch_out);
//
//        txt_punch_in_out_title = view.findViewById(R.id.txt_punch_in_out_title);
//
//
//        txt_punch_in_message = view.findViewById(R.id.txt_punch_in_message);
//        txt_punch_out_message = view.findViewById(R.id.txt_punch_out_message);
//
//        card_punch_in = view.findViewById(R.id.card_punch_in);
//        card_punch_out = view.findViewById(R.id.card_punch_out);
//
//        card_punch_in.setOnClickListener(this);
//        card_punch_out.setOnClickListener(this);
//
//        if (isTodayPunchINDone()) {
//            //punch in date and time show on button
//
//            txt_punch_in_message.setText(getSharedPref.getUserPunchInDate());
//            card_punch_in.setEnabled(false);
//            punchInMessageValidate();
//
//
//        }
//
//
//        if (isTodayPunchOutDone()) {
//            //punch in date and time show on button
//            txt_punch_out_message.setText(getSharedPref.getUserPunchOutDate());
//            card_punch_out.setEnabled(false);
//            punchInMessageValidate();
//        }
//
//        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//
//
//        getLastLocation();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkDrawOverlayPermission();
//        }
//
//
//        // readGPSSettings();
//    }
//
//    private void punchInMessageValidate() {
//        txt_punch_in_out_title.setText("Today's Punch In - Out History ( " + sdf_full.format(new Date()) + " )");
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(int value) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(value);
//        }
//    }
//
//    private Dialog exitDialog;
//
//    public void noAddressFound() {
//        exitDialog = new Dialog(getContext());
//        exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        exitDialog.setContentView(R.layout.logout_dialog);
//        exitDialog.setCancelable(false);
//        TextView txtTitle = (TextView) exitDialog.findViewById(R.id.txtTitle);
//        Button btnPositive = (Button) exitDialog.findViewById(R.id.btnPositive);
//        Button btnNegative = (Button) exitDialog.findViewById(R.id.btnNegative);
//        //btnNegative.setVisibility(View.GONE);
//        btnNegative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //cancell button click finish activity
//                exitDialog.dismiss();
//
//            }
//        });
//        btnNegative.setVisibility(View.INVISIBLE);
////        txtTitle.setText(getString(R.string.something_went_wrong));
//        // txtTitle.setText(getString(R.string.stargpsmessage));
//        txtTitle.setText(getString(R.string.GPS_ON_OFF));
//        btnPositive.setText(getString(R.string.title_ok));
//        btnPositive.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                //cancell button click finish activity
//                exitDialog.dismiss();
//                //recall contact us  info0
//                exitDialog.dismiss();
////                getDataFromApi(user_id, latitude, longitude, isUserPunchIn);
//
//
//            }
//        });
//
//        try {
//            if (exitDialog != null && exitDialog.isShowing()) {
//                exitDialog.dismiss();
//            }
//        } catch (Exception ex) {
//        }
//        exitDialog.show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        switch (v.getId()) {
//            case R.id.card_punch_in:
//
//                if (statusOfGPS) {
//
//                    context.bindService(new Intent(getActivity(), LocationUpdateForegroundService.class), mServiceConnection,
//                            Context.BIND_AUTO_CREATE);
//
//                    isPunchInClick = true;
//                    isPunchOutClick = false;
//                    getLastLocation();
//                    System.out.println("api_latitude >>>>>>>>>>>>>>>>>   "+api_latitude+"");
//                    System.out.println("api_longitude >>>>>>>>>>>>>>>>>>>>   "+api_longitude+"");
//                    if (api_latitude == 0.0 && api_longitude == 0.0) {
//                       //pppppppppppppppp noAddressFound();
//
//
//                        try{
//                            if(dialogSuccess!=null && dialogSuccess.isShowing()){
//                                dialogSuccess.dismiss();
//                            }
//                        }catch (Exception ex){}
//
//                        try{
//                            dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
//                            dialogSuccess.setTitleText(getString(R.string.sorder_oops));
//
//                            dialogSuccess.setContentText("Unable to fetch your location , either chnage your position or re-open the application or restart the gps and internet connection");
//
//                            dialogSuccess.setCancelable(false);
//                            dialogSuccess.show();
//                            dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialogSuccess.dismissWithAnimation();
//                                    dialogSuccess.cancel();
//
//                                }
//                            });
//
//                        }catch (Exception ex){}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                    } else {
//                       addAttendanceApiCall("0");
//                    }
//
//                } else {
//                    displaySnackBar(getResources().getString(R.string.title_valid_gps_start));
//                }
//
//                break;
//            case R.id.card_punch_out:
//
//                if (isTodayPunchINDone()) {
//                    if (statusOfGPS) {
//                        isPunchOutClick = true;
//                        isPunchInClick = false;
//                        getLastLocation();
//                        if (api_latitude == 0.0 && api_longitude == 0.0) {
//                            noAddressFound();
//                        } else {
//                            addAttendanceApiCall("1");
//                        }
//                    } else {
//                        displaySnackBar(getResources().getString(R.string.title_valid_gps_start));
//                    }
//                } else {
//                    displaySnackBar(getString(R.string.title_do_punch_in));
//                }
//
//                break;
//
//        }
//    }
//
//
//    private boolean isTodayPunchINDone() {
//        if (TextUtils.isEmpty(getSharedPref.getUserPunchInDate())) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//
//
//    public boolean isTodayPunchOutDone() {
//        if (TextUtils.isEmpty(getSharedPref.getUserPunchOutDate())) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnAddAttendanceInteractionListener) {
//            mListener = (OnAddAttendanceInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//
//    private void fetchGPSDetails() {
//        if (curLocation != null && curLocation.getLatitude() > 0.0 && curLocation.getLongitude() > 0.0) {
//            api_latitude = curLocation.getLatitude();
//            api_longitude = curLocation.getLongitude();
//            locAccuracy = curLocation.getAccuracy();
//            getAddress(curLocation.getLatitude(), curLocation.getLongitude());
//        }
//    }
//
//
//    public void addAttendanceApiCall(final String punch_in_out) {
//
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                //Login request
//
//                if (api_latitude <= 0) {
//                    getLastLocation();
//                }
//
//
//                if (progDialog != null && progDialog.isShowing()) {
//                    progDialog.dismiss();
//                }
//
//                try {
//                    progDialog = new ProgressDialog(context);
//                    progDialog.setIndeterminate(true);
//                    progDialog.setMessage(getResources().getString(R.string.processing_request));
//                    progDialog.setCancelable(false);
//                    progDialog.show();
//                } catch (Exception ex) {
//                }
//
//                Call<AddAttendanceResponse> call = apiService.Add_Attendance(
//                        String.valueOf(getSharedPref.getAppVersionCode()),
//                        getSharedPref.getAppAndroidId(),
//                        String.valueOf(getSharedPref.getRegisteredId()),
//                        String.valueOf(getSharedPref.getRegisteredUserId()),
//                        Config.ACCESS_KEY,
//                        getSharedPref.getCompanyId(),
//                        getSharedPref.getBranchId(),
//                        "",
//                        String.valueOf(api_latitude),
//                        String.valueOf(api_longitude),
//                        LocationAddress,
//                        location_city,
//                        location_district,
//                        location_taluk,
//                        location_state,
//                        location_country,
//                        punch_in_out,
//                        isGPSON());
//
//
//                call.enqueue(new Callback<AddAttendanceResponse>() {
//                    @Override
//                    public void onResponse(Call<AddAttendanceResponse> call, final Response<AddAttendanceResponse> response) {
//                        if (progDialog != null && progDialog.isShowing()) {
//                            progDialog.dismiss();
//                        }
//
//                        if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
//
//                            try {
//                                if (response.body().getFLG() == 1) {
//                                    dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
//                                    dialogSuccess.setTitleText(getString(R.string.sorder_good_job));
//                                } else {
//                                    dialogSuccess = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
//                                    dialogSuccess.setTitleText(getString(R.string.sorder_oops));
//                                }
//
//                                dialogSuccess.setContentText(response.body().getMSG());
//                                dialogSuccess.setCancelable(false);
//                                dialogSuccess.show();
//                                dialogSuccess.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialogSuccess.dismissWithAnimation();
//                                        dialogSuccess.cancel();
//
//
//                                        if (!isTodayPunchINDone() && isPunchInClick == true && response.body().getFLG() > 0) {
//                                            if (!TextUtils.isEmpty(response.body().getPUNCHDNT())) {
//                                                getSharedPref.setUserPunchInDate(response.body().getPUNCHDNT());
//                                                txt_punch_in_message.setText(response.body().getPUNCHDNT());
//                                                card_punch_in.setEnabled(false);
//                                            }
//
//
//                                            if (response.body().getFLG() == 1) {
//                                                punchInMessageValidate();
//                                                if (!checkPermissions()) {
//                                                    requestPermissions();
//                                                } else {
//                                                    mService.requestLocationUpdates();
//                                                }
//
//                                                showOverLasysSettingsScreenCall();
//
//                                                //scheduleJob();
//
//
//                                            }
//
//                                        } else if (!isTodayPunchOutDone() && isPunchOutClick == true && response.body().getFLG() > 0) {
//
//                                            if (!TextUtils.isEmpty(response.body().getPUNCHDNT())) {
//                                                card_punch_out.setEnabled(false);
//                                                punchInMessageValidate();
//                                                getSharedPref.setUserPunchOutDate(response.body().getPUNCHDNT());
//                                                txt_punch_out_message.setText(response.body().getPUNCHDNT());
//                                            }
//
//                                            try {
//                                                addLocationDataEvent();
//                                            } catch (Exception ex) {
//                                            }
//
//
//                                            // Remove all service and job
//
//                                            Intent lintent = new Intent(context, LocationUpdateForegroundService.class);
//                                            context.stopService(lintent);
//
//                                            Intent overlay_intent = new Intent(context, OverLayTrackingService.class);
//                                            context.stopService(overlay_intent);
//
//
//                                            JobScheduler jobScheduler = null;
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                                jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                                                jobScheduler.cancelAll();
//                                            }
//
//                                            gpsMasterBeanList = new ArrayList<>();
//
//                                            try {
//                                                gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId()+"");
//                                            } catch (Exception ex) {
//                                            }
//
//
//                                            //Log.e("gpsMasterBeanList",""+gpsMasterBeanList.size());
//
//                                            if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
//                                                sendingLocationData();
//                                            }
//
//                                        }
//
//                                    }
//                                });
//
//
//                            } catch (Exception ex) {
//                            }
//
//
//                        } else {
//                            displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<AddAttendanceResponse> call, Throwable t) {
//                        if (progDialog != null && progDialog.isShowing()) {
//                            progDialog.dismiss();
//                        }
//                        displaySnackBar(context.getResources().getString(R.string.something_went_wrong));
//                    }
//                });
//
//
//            }
//
//        });
//
//    }
//
//
//    public interface OnAddAttendanceInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(int value);
//    }
//
//    private void displaySnackBar(String message) {
//        try {
//            if (addAttendanceSnackBar != null && addAttendanceSnackBar.isShown()) {
//                addAttendanceSnackBar.dismiss();
//            }
//            addAttendanceSnackBar = Snackbar.make(frm_add_attendance, message, Snackbar.LENGTH_LONG);
//            addAttendanceSnackBar.show();
//        } catch (Exception ex) {
//        }
//    }
//
//
//    public boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
//        if (status != ConnectionResult.SUCCESS) {
//            if (googleApiAvailability.isUserResolvableError(status)) {
//                googleApiAvailability.getErrorDialog((Activity) context, status, 2404).show();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    public void getAddress(double lat, double lng) {
//        try {
//            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
//            if (addresses != null && addresses.size() > 0) {
//                Address address = addresses.get(0);
//                StringBuilder sb = new StringBuilder();
//                String[] locationArray = new String[1];
//                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
//                    sb.append(address.getAddressLine(i)).append("\n");
//                }
//               /* sb.append(address.getLocality()).append("\n");
//                sb.append(address.getPostalCode()).append("\n");
//                sb.append(address.getCountryName());*/
//
//                LocationAddress = sb.toString();
//                if (LocationAddress != null && LocationAddress.length() > 0 && LocationAddress.contains("null") || LocationAddress.contains("Null") || LocationAddress.contains("NULL")) {
//                    if (LocationAddress.contains("null")) {
//                        LocationAddress = LocationAddress.replace("null", "");
//                    }
//                    if (LocationAddress.contains("Null")) {
//                        LocationAddress = LocationAddress.replace("Null", "");
//                    }
//                    if (LocationAddress.contains("NULL")) {
//                        LocationAddress = LocationAddress.replace("NULL", "");
//                    }
//                }
//
//                if (!TextUtils.isEmpty(address.getLocality())) {
//                    locationArray[0] = address.getLocality();
//                    location_city = address.getLocality();
//                } else {
//                    locationArray[0] = "";
//                    location_city = "";
//                }
//
//                if (address != null) {
//                    if (!TextUtils.isEmpty(address.getCountryName())) {
//                        location_country = address.getCountryName();
//                    }
//
//                    if (!TextUtils.isEmpty(address.getAdminArea())) {
//                        location_state = address.getAdminArea();
//                    }
//
//                    if (!TextUtils.isEmpty(address.getSubAdminArea())) {
//                        location_district = address.getSubAdminArea();
//                    }
//
//
//                }
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // New Location Api
//
//
//    private void startLocationUpdates() {
//        // Begin by checking if the device has the necessary location settings.
//        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
//                .addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>() {
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//
//
//                        //noinspection MissingPermission
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                                mLocationCallback, Looper.myLooper());
//
//                        // update ui
//
//                    }
//                })
//                .addOnFailureListener((Activity) context, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        int statusCode = ((ApiException) e).getStatusCode();
//                        switch (statusCode) {
//                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                                try {
//                                    // Show the dialog by calling startResolutionForResult(), and check the
//                                    // result in onActivityResult().
//                                    ResolvableApiException rae = (ResolvableApiException) e;
//                                    rae.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
//                                } catch (IntentSender.SendIntentException sie) {
//
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                String errorMessage = "Location settings are inadequate, and cannot be " +
//                                        "fixed here. Fix in Settings.";
//
//
////                                mRequestingLocationUpdates = false;
//                        }
//
//                        //updateUI();
//                    }
//                });
//    }
//
//    private void createLocationRequestNewApi() {
//        mLocationRequest = new LocationRequest();
//
//        // Sets the desired interval for active location updates. This interval is
//        // inexact. You may not receive updates at all if no location sources are available, or
//        // you may receive them slower than requested. You may also receive updates faster than
//        // requested if other applications are requesting location at a faster interval.
//        mLocationRequest.setInterval(INTERVAL);
//
//        // Sets the fastest rate for active location updates. This interval is exact, and your
//        // application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//    }
//
//
//    private void buildLocationSettingsRequest() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        mLocationSettingsRequest = builder.build();
//    }
//
//    private void createLocationCallback() {
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                curLocation = locationResult.getLastLocation();
//
//                // mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//                // update ui
//                if (curLocation != null) {
//                    fetchGPSDetails();
//                }
//
//            }
//        };
//    }
//
//    private void stopLocationUpdates() {
//        /*if (!mRequestingLocationUpdates) {
//           // Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
//            return;
//        }*/
//
//        // It is a good practice to remove location requests when the activity is in a paused or
//        // stopped state. Doing so helps battery performance and is especially
//        // recommended in applications that request frequent location updates.
//        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
//                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // mRequestingLocationUpdates = false;
//
//                    }
//                });
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        PreferenceManager.getDefaultSharedPreferences(getActivity())
//                .registerOnSharedPreferenceChangeListener(this);
//
//        /*img_add_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!checkPermissions()) {
//                    requestPermissions();
//                } else {
//                    mService.requestLocationUpdates();
//                }
//            }
//        });*/
//
//       /* img_minus_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mService.removeLocationUpdates();
//            }
//        });*/
//
//
//        //
//
//
//        // Restore the state of the buttons when the activity (re)launches.
//
//
//        // Bind to the service. If the service is in foreground mode, this signals to the service
//        // that since this activity is in the foreground, the service can exit foreground mode.
//
//
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//    }
//
//
//    @Override
//    public void onResume() {
//        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
//        // location updates if the user has requested them.
//
//
//        super.onResume();
//
//       /* if(isGooglePlayServicesAvailable()) {
//            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (!statusOfGPS) {
//                buildGoogleApiClient();
//                createLocationRequest();
//            }else{
//                dismissAppConfigDialog();
//            }
//        }*/
//
//        LocalBroadcastManager.getInstance(context).registerReceiver(myReceiver,
//                new IntentFilter(LocationUpdateForegroundService.ACTION_BROADCAST));
//
//        // startLocationUpdates();
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        LocalBroadcastManager.getInstance(context).unregisterReceiver(myReceiver);
//        // stopLocationUpdates();
//    }
//
//
//    @Override
//    public void onStop() {
//        if (mBound) {
//            // Unbind from the service. This signals to the service that this activity is no longer
//            // in the foreground, and the service can respond by promoting itself to a foreground
//            // service.
//            context.unbindService(mServiceConnection);
//            mBound = false;
//        }
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .unregisterOnSharedPreferenceChangeListener(this);
//        super.onStop();
//    }
//
//
//    private class MyReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Location location = intent.getParcelableExtra(LocationUpdateForegroundService.EXTRA_LOCATION);
//            if (location != null) {
//               /* Toast.makeText(MainActivity.this, LocationUtil.getLocationText(location),
//                        Toast.LENGTH_LONG).show();*/
//            }
//        }
//    }
//
//    /**
//     * Returns the current state of the permissions needed.
//     */
//    private boolean checkPermissions() {
//        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION);
//    }
//
//    private void requestPermissions() {
//        boolean shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                        Manifest.permission.ACCESS_FINE_LOCATION);
//
//        // Provide an additional rationale to the user. This would happen if the user denied the
//        // request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//
//            Snackbar.make(
//                    frm_add_attendance,
//                    R.string.permission_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            ActivityCompat.requestPermissions(getActivity(),
//                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                    REQUEST_PERMISSIONS_REQUEST_CODE);
//                        }
//                    })
//                    .show();
//        } else {
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSIONS_REQUEST_CODE);
//        }
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length <= 0) {
//                // If user interaction was interrupted, the permission request is cancelled and you
//                // receive empty arrays.
//                //Log.i(TAG, "User interaction was cancelled.");
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission was granted.
//                //mService.requestLocationUpdates();
//            } else {
//                // Permission denied.
//
//                Snackbar.make(
//                        frm_add_attendance,
//                        R.string.permission_denied_explanation,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.settings, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Build intent that displays the App settings screen.
//                                Intent intent = new Intent();
//                                intent.setAction(
//                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package",
//                                        BuildConfig.APPLICATION_ID, null);
//                                intent.setData(uri);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        })
//                        .show();
//            }
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void scheduleJob() {
//        //900000
//        final long ONE_DAY_INTERVAL = 1050000; // 10 Min
//
//        final long ONE_DAY_INTERVAL_RESTRICTED = 1050000; // 15 Min
//
//        ComponentName serviceComponent = new ComponentName(context, JobScheduledService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(10, serviceComponent);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder.setPeriodic(ONE_DAY_INTERVAL_RESTRICTED);
//        } else {
//            builder.setPeriodic(ONE_DAY_INTERVAL);
//        }
//
////        Log.d("Activity_Home", "job being to start!");
//
//        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        int resultCode = jobScheduler.schedule(builder.build());
//
//        if (resultCode == JobScheduler.RESULT_SUCCESS) {
////            Log.d("Activity_Home", "Job scheduled!");
//        } else {
////            Log.d("Activity_Home", "Job not scheduled");
//        }
//    }
//
//    private void getLastLocation() {
//        try {
//            mFusedLocationClient.getLastLocation()
//                    .addOnCompleteListener(new OnCompleteListener<Location>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Location> task) {
//                            if (task.isSuccessful() && task.getResult() != null) {
//                                curLocation = task.getResult();
//                                if (curLocation != null) {
//                                    fetchGPSDetails();
//                                }
//                            } else {
//                                //Log.e("Add Attendance", "Failed to get location.");
//                            }
//                        }
//                    });
//        } catch (SecurityException unlikely) {
//            //Log.e("Add Attendance", "Lost location permission." + unlikely);
//        }
//    }
//
//    private String isGPSON() {
//        try {
//            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (statusOfGPS) {
//                return "1";
//            } else {
//                return "0";
//            }
//        } catch (Exception ex) {
//            return "0";
//        }
//
//    }
//
//    private void sendingLocationData() {
//
//
//        if (progDialog != null && progDialog.isShowing()) {
//            progDialog.dismiss();
//        }
//
//        try {
//            progDialog = new ProgressDialog(context);
//            progDialog.setIndeterminate(true);
//            progDialog.setMessage(getResources().getString(R.string.processing_request_attendance));
//            progDialog.setCancelable(false);
//            progDialog.show();
//        } catch (Exception ex) {
//        }
//
//        loopCounter = 0;
//
//        gpsMasterBeanList = new ArrayList<>();
//
//        try {
//            gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId()+"");
//        } catch (Exception ex) {
//        }
//
//
//        JSONArray jsonArray = new JSONArray();
//        if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
//            for (GPSMasterBean data : gpsMasterBeanList) {
//
//                loopCounter++;
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("loc_name", data.getGPS_Location_Name());
//                } catch (Exception ex) {
//                }
//
//                try {
//                    jsonObject.put("loc_address", data.getGPS_Address());
//                } catch (Exception ex) {
//                }
//
//
//                try {
//                    jsonObject.put("loc_latitude", data.getGPS_Latitude());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_latitude", "0.0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//
//                try {
//                    jsonObject.put("loc_longitude", data.getGPS_Longitude());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_longitude", "0.0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//
//                try {
//                    jsonObject.put("loc_date_time", data.getGPS_DateTime());
//                } catch (Exception ex) {
//                }
//
//                try {
//                    jsonObject.put("loc_battery", data.getGPS_Battery_Percentage());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_battery", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                try {
//                    jsonObject.put("loc_gps_status", data.getGPS_Status());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_gps_status", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                try {
//                    jsonObject.put("loc_network_status", data.getGPS_Internet_Status());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_network_status", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                try {
//                  //  jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? data.getGPS_Accuracy() : "0");
//                    jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? "0" : data.getGPS_Accuracy());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("loc_accuracy", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                try {
//                    jsonObject.put("km_traveled", data.getGPS_Km_Travelled());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("km_traveled", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                try {
//                    jsonObject.put("is_loc_changed", data.getGPS_Is_Loc_Changed());
//                } catch (Exception ex) {
//                    try {
//                        jsonObject.put("is_loc_changed", "0");
//                    } catch (JSONException js) {
//                    }
//                }
//
//                jsonArray.put(jsonObject);
//
//
//                if (loopCounter == 1) {
//                    minLastUpdatedRecordId = data.getGPS_Master_Id();
//                }
//                if (loopCounter == gpsMasterBeanList.size()) {
//                    maxLastUpdatedRecordId = data.getGPS_Master_Id();
//                }
//
//
//            }
//
//            getSharedPref = new SharedPref(context);
//
//
//            Request_Insert_Location_Sync dataRequest = new Request_Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
//                    getSharedPref.getAppAndroidId(),
//                    String.valueOf(getSharedPref.getRegisteredId()),
//                    getSharedPref.getRegisteredUserId(),
//                    Config.ACCESS_KEY,
//                    getSharedPref.getCompanyId(),
//                    getSharedPref.getBranchId(),
//                    getSharedPref.getUserPhone(),
//                    jsonArray.toString());
//
//
//            Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(dataRequest);
//
//
//          /*  Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
//                    getSharedPref.getAppAndroidId(),
//                    String.valueOf(getSharedPref.getRegisteredId()),
//                    getSharedPref.getRegisteredUserId(),
//                    Config.ACCESS_KEY,
//                    getSharedPref.getUserPhone(),
//                    jsonArray.toString());*/
//
//            call.enqueue(new Callback<InsertLocationSyncResponse>() {
//                @Override
//                public void onResponse(Call<InsertLocationSyncResponse> call, Response<InsertLocationSyncResponse> response) {
//                    if (progDialog != null && progDialog.isShowing()) {
//                        progDialog.dismiss();
//                    }
//                    /*if((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
//                        if(response.body().getFLAG()==1){
//                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId));
//                        }
//                    }else{
//
//                    }*/
//
//                    if ((!TextUtils.isEmpty(response.toString())) && response.isSuccessful()) {
//
//                        // Log.e("response 111",""+response.isSuccessful());
//
//                        if (response.body().getFLAG() == 1) {
//                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId),getSharedPref.getRegisteredUserId()+"");
//                            getSharedPref.setPreviousLocation("0.0", "0.0");
//                        } else {
//                            //Log.e("Erorr 111",response.body().getMSG());
//                        }
//                    } else {
//                        //Log.e("Erorr 222",response.toString());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<InsertLocationSyncResponse> call, Throwable t) {
//                    if (progDialog != null && progDialog.isShowing()) {
//                        progDialog.dismiss();
//                    }
//
//                    // Log.e("Erorr 333",t.getMessage().toUpperCase());
//                }
//            });
//
//            /*LazyDataConnection GetSources_B2CTask = new LazyDataConnection("insert_location_shrink");
//            GetSources_B2CTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "insert_location_shrink", getAPI.insert_location_shrink(
//                    getSharedPref.getAppVersionCode(),
//                    getSharedPref.getAppAndroidId(),
//                    getSharedPref.getRegisteredId(),
//                    0,
//                    getSharedPref.getUserPhone(),
//                    jsonArray.toString()
//            ));*/
//        } else {
//            if (progDialog != null && progDialog.isShowing()) {
//                progDialog.dismiss();
//            }
//        }
//
//    }
//
//    private void addLocationDataEvent() {
//        if (dbConnector != null) {
//            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            batteryStatus = context.registerReceiver(null, ifilter);
//
//            today = new Date();
//            GPSMasterBean data = new GPSMasterBean();
//
//            data.setGPS_Location_Name("unspecified");
//            data.setGPS_Latitude(String.valueOf(api_latitude));
//            data.setGPS_Longitude(String.valueOf(api_longitude));
//
//            data.setGPS_Address(LocationAddress);
//
//            try {
//                data.setGPS_Battery_Percentage(String.valueOf(calculateBatteryPercentage()));
//            } catch (Exception ex) {
//            }
//
//            if (cd != null) {
//                data.setGPS_Internet_Status(cd.isConnectingToInternet() ? "1" : "0");
//            } else {
//                data.setGPS_Internet_Status("0");
//            }
//
//            data.setGPS_Status(String.valueOf(isGPSON()));
//
//            try {
//                data.setGPS_DateTime(sdf.format(today));
//            } catch (Exception ex) {
//            }
//
//            try {
//                data.setGPS_Accuracy(String.valueOf(locAccuracy));
//            } catch (Exception ex) {
//            }
//
//            if (api_latitude > 0 && (!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {
//
//
//                Location priviusLocation = new Location("");//provider name is unnecessary
//                priviusLocation.setLatitude(Double.parseDouble(getSharedPref.getPreviousLat()));//your coords of course
//                priviusLocation.setLongitude(Double.parseDouble(getSharedPref.getPreviousLong()));
//
//                Location currentLocation = new Location("");
//                currentLocation.setLatitude(api_latitude);
//                currentLocation.setLongitude(api_longitude);
//
//                double distanceInMeters = currentLocation.distanceTo(priviusLocation);
//
//                if (distanceInMeters > getSharedPref.getDefaultDistance()) {
//                    data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
//                    data.setGPS_Is_Loc_Changed("1");
//                } else {
//                    data.setGPS_Km_Travelled("0.0");
//                    data.setGPS_Is_Loc_Changed("0");
//                }
//            } else {
//                data.setGPS_Km_Travelled("0.0");
//                data.setGPS_Is_Loc_Changed("0");
//            }
//
//
//            if (api_latitude > 0) {
//                getSharedPref.setPreviousLocation(String.valueOf(api_latitude), String.valueOf(api_longitude));
//            }
//
//
////                Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();
//
//            boolean flag = false;
//
//            if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
//                    (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
//                    Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
//                flag = dbConnector.addGPSData(data,getSharedPref.getRegisteredUserId()+"","1");
//
//            } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
//                flag = dbConnector.addGPSData(data,getSharedPref.getRegisteredUserId()+"","2");
//            }
//
//
//        }
//    }
//
//    private int calculateBatteryPercentage() {
//
//        boolean present = batteryStatus.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
//        int battery_percentage = 0;
//        if (present) {
//            battery_percentage = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        }
//        return battery_percentage;
//    }
//
//    private void showOverLasysSettingsScreenCall() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            showOverlay();
//        } else {
//            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
//                launchMainService();
//            }
//
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void showOverlay() {
//
//        if (Settings.canDrawOverlays(context)) {
//
//            // Launch service right away - the user has already previously granted permission
//            if (isTodayPunchINDone() && !isTodayPunchOutDone()) {
//                launchMainService();
//            }
//
//        } else {
//
//            // Check that the user has granted permission, and prompt them if not
//            checkDrawOverlayPermission();
//        }
//    }
//
//    public final static int REQUEST_CODE = 10101;
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void checkDrawOverlayPermission() {
//
//        // Checks if app already has permission to draw overlays
//        if (!Settings.canDrawOverlays(context)) {
//
//            // If not, form up an Intent to launch the permission request
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
//
//            // Launch Intent, with the supplied request code
//            startActivityForResult(intent, REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//
//            // Double-check that the user granted it, and didn't just dismiss the request
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (Settings.canDrawOverlays(context)) {
//
//                    // Launch the service
//                    //launchMainService();
//                } else {
//                    Toast.makeText(context, "Please grant permission for well usage of app...", Toast.LENGTH_LONG).show();
//                    showOverLasysSettingsScreenCall();
//                }
//            } else {
//                // launchMainService();
//            }
//        }
//    }
//
//    private void launchMainService() {
//
//        Intent svc = new Intent(context, OverLayTrackingService.class);
//        context.stopService(svc);
//        context.startService(svc);
//
//
//    }
//
//}
