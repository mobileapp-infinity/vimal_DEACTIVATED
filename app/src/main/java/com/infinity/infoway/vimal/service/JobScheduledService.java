package com.infinity.infoway.vimal.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.api.request.Request_Insert_Location_Sync;
import com.infinity.infoway.vimal.api.response.InsertLocationSyncResponse;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.DBConnector;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.model.GPSMasterBean;
import com.infinity.infoway.vimal.util.common.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobScheduledService extends JobService {

    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;
    private DBConnector dbConnector;
    private LocationUpdateForegroundService mService = null;
    private Intent batteryStatus;
    // Tracks the bound state of the service.
    private boolean mBound = false;
    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private ConnectionDetector cd;
    private IntentFilter ifilter;
    private Date today;
    private SimpleDateFormat sdf;
    private LocationManager manager;
    private boolean statusOfGPS;
    private String[] LocationArray;
    private String LocationAddress = "";


    private SharedPref getSharedPref;
    private List<GPSMasterBean> gpsMasterBeanList;
    private int loopCounter = 0;
    private String minLastUpdatedRecordId, maxLastUpdatedRecordId;
    private long Status;
    private ApiInterface apiService;

    @Override
    public boolean onStartJob(JobParameters params) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        dbConnector = new DBConnector(this);
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        cd = new ConnectionDetector(this);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        getSharedPref = new SharedPref(this);

      /*  if (!isServiceRunning(LocationUpdateForegroundService.class)) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return true;
            }
            startWorkOnNewThread();
        }*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true;
        }
        startWorkOnNewThread();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //Log.e("finish job","");
        //  jobFinished(params, true);
        return true;
    }

    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i
                    .next();

            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {
                serviceRunning = true;

                if (runningServiceInfo.foreground) {
                    //service run in foreground
                    //Log.e("run", "service run in foreground");
                }
            }
        }
        return serviceRunning;
    }

    private void startWorkOnNewThread() {
        new Thread(new Runnable() {
            public void run() {
                //Log.e("job", "start");
                if (ActivityCompat.checkSelfPermission(JobScheduledService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(JobScheduledService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Log.e("job", "start 111");


                getLastLocation();


            }
        }).start();
    }

    private void getLastLocation() {
        /*try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                Log.e("lat::", "" + mLocation.getLatitude());
                            } else {
                                Log.e("location", "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e("location", "Lost location permission." + unlikely);
        }*/

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

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    if (dbConnector != null && location != null) {
                        mLocation = location;
                        batteryStatus = registerReceiver(null, ifilter);

                        today = new Date();
                        GPSMasterBean data = new GPSMasterBean();

                        data.setGPS_Location_Name("unspecified");
                        data.setGPS_Latitude(String.valueOf(mLocation.getLatitude()));
                        data.setGPS_Longitude(String.valueOf(mLocation.getLongitude()));

                        try {
                            String address = GetAddress(mLocation.getLatitude(), mLocation.getLongitude());
                            if (!TextUtils.isEmpty(address)) {
                                data.setGPS_Address(address);
                            } else {
                                data.setGPS_Address("");
                            }
                        } catch (Exception ex) {
                            data.setGPS_Address("");
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

                        data.setGPS_Status(String.valueOf(isGPSON()));

                        try {
                            data.setGPS_DateTime(sdf.format(today));
                        } catch (Exception ex) {
                        }

                        try {
                            data.setGPS_Accuracy(String.valueOf(mLocation.getAccuracy()));
                        } catch (Exception ex) {
                        }


                        if ((!TextUtils.isEmpty(getSharedPref.getPreviousLat())) && !getSharedPref.getPreviousLat().equalsIgnoreCase("0.0")) {


                            Location priviusLocation = new Location("");//provider name is unnecessary
                            priviusLocation.setLatitude(Double.parseDouble(getSharedPref.getPreviousLat()));//your coords of course
                            priviusLocation.setLongitude(Double.parseDouble(getSharedPref.getPreviousLong()));

                            Location currentLocation = new Location("");
                            currentLocation.setLatitude(mLocation.getLatitude());
                            currentLocation.setLongitude(mLocation.getLongitude());

                            double distanceInMeters = currentLocation.distanceTo(priviusLocation);

                            if (distanceInMeters > getSharedPref.getDefaultDistance()) {
                                if (mLocation.getLatitude() > 0) {
                                    getSharedPref.setPreviousLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
                                }
                                data.setGPS_Km_Travelled(String.format("%.2f", distanceInMeters / 1000));
                                data.setGPS_Is_Loc_Changed("1");
                            } else {
                                data.setGPS_Km_Travelled("0.0");
                                data.setGPS_Is_Loc_Changed("0");
                            }
                        } else {
                            data.setGPS_Km_Travelled("0.0");
                            data.setGPS_Is_Loc_Changed("0");
                            if (mLocation.getLatitude() > 0) {
                                getSharedPref.setPreviousLocation(String.valueOf(mLocation.getLatitude()), String.valueOf(mLocation.getLongitude()));
                            }
                        }


                        // Log.e("inside job service add", "save data");

                        // Log.e("insidelocation", String.valueOf(location.getLatitude()));

//                Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();
                        System.out.println("data.getGPS_Status() " + data.getGPS_Status() + " data.getGPS_Status() " + data.getGPS_Status() + " data.getGPS_Latitude() " + data.getGPS_Latitude() + "");
                        if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("1") &&
                                (!TextUtils.isEmpty(data.getGPS_Latitude())) && (!TextUtils.isEmpty(data.getGPS_Longitude())) &&
                                Double.parseDouble(data.getGPS_Latitude()) > 0 && Double.parseDouble(data.getGPS_Longitude()) > 0) {
                            dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "","TEST 1");

                        } else if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                            dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "","");
                        }

                        //Log.e("Job scheduled", "Save data");
                    }
                }
            }
        });


        if (mLocation == null && isGPSON() == 0) {
            if (dbConnector != null) {

                batteryStatus = registerReceiver(null, ifilter);

                today = new Date();
                GPSMasterBean data = new GPSMasterBean();

                data.setGPS_Location_Name("unspecified");
                data.setGPS_Latitude("0.0");
                data.setGPS_Longitude("0.0");

                data.setGPS_Address("");

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
                    data.setGPS_Accuracy("");
                } catch (Exception ex) {
                }

                data.setGPS_Km_Travelled("0.0");
                data.setGPS_Is_Loc_Changed("0");

                //Log.e("inside job service add", "save data");


//                Toast.makeText(context, "old=="+getSharedPref.getlat(), Toast.LENGTH_LONG).show();
                //     System.out.println("PPPPPPPPPPPPPP "+data.getGPS_Status()+"  data.getGPS_Status() "+data.getGPS_Status()+"");
                if (data != null && (!TextUtils.isEmpty(data.getGPS_Status())) && data.getGPS_Status().trim().equals("0")) {
                    dbConnector.addGPSData(data, getSharedPref.getRegisteredUserId() + "","");
                }

                // Log.e("Job scheduled", "Save data");
            }
        }

        if (cd != null && cd.isConnectingToInternet()) {
            sendingLocationData();
        }
    }

    public String GetAddress(double lat, double lng) {

        LocationAddress = "";

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 5);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                LocationArray = new String[1];
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    // sb.append(address.getAddressLine(i));
                    sb.append(address.getAddressLine(i)).append("\n");
                }
               /* sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());*/
                if (address.getLocality().length() > 0 && address.getLocality() != null) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocationAddress;
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
            return 0;
        }

    }

    private void sendingLocationData() {
        loopCounter = 0;

        gpsMasterBeanList = new ArrayList<>();

        try {
            gpsMasterBeanList = dbConnector.getGPSMasterData(getSharedPref.getRegisteredUserId() + "");
        } catch (Exception ex) {
        }


        apiService = ApiClient.getClient().create(ApiInterface.class);


        JSONArray jsonArray = new JSONArray();
        if (gpsMasterBeanList != null && gpsMasterBeanList.size() > 0) {
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
                    //   jsonObject.put("loc_accuracy", data.getGPS_Accuracy().isEmpty() ? data.getGPS_Accuracy() : "0");
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

            /*Call<InsertLocationSyncResponse> call = apiService.Insert_Location_Sync(String.valueOf(getSharedPref.getAppVersionCode()),
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
                            dbConnector.deleteGPSRangeData(Integer.parseInt(minLastUpdatedRecordId), Integer.parseInt(maxLastUpdatedRecordId), getSharedPref.getRegisteredUserId() + "");
                        } else {
                            //Log.e("Erorr 111",response.body().getMSG());
                        }
                    } else {
                        // Log.e("Erorr 222",response.toString());
                    }
                }

                @Override
                public void onFailure(Call<InsertLocationSyncResponse> call, Throwable t) {
                    //Log.e("Erorr 333",t.getMessage().toString());
                }
            });


           /* LazyDataConnection GetSources_B2CTask = new LazyDataConnection("insert_location_shrink");
            GetSources_B2CTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "insert_location_shrink", getAPI.insert_location_shrink(
                    getSharedPref.getAppVersionCode(),
                    getSharedPref.getAppAndroidId(),
                    getSharedPref.getRegisteredId(),
                    0,
                    getSharedPref.getUserPhone(),
                    jsonArray.toString()
            ));*/
        }

    }


}
