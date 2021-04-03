package com.infinity.infoway.vimal.delear.activity.add_schedule.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.add_schedule.pojo.GetSaleRouteWiseVehicleWisePlanningDetailsPojo;
import com.infinity.infoway.vimal.delear.util.CommonUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    ArrayList<LatLngModel> latLngArrayList;
    private GoogleMap mMap;
    private ProgressDialog progDialog;
    private SharedPref sharedPref;
    private String rvpmId = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_map);
        initView();
        // get_sale_route_wise_vehicle_wise_planning_details(rvpmId);
    }


    private void initView() {
        progDialog = new ProgressDialog(this);
        sharedPref = new SharedPref(this);
        intent = getIntent();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Schedules");
        rvpmId = intent.getStringExtra("rvpmId");
        //latLngArrayList.add(new LatLngModel(22.336910, 73.831871));

        // latLngArrayList.add(new LatLngModel(22.303894, 70.802162));

        MapsInitializer.initialize(ScheduleMapActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewLocationMap);
        //   mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);

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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            get_sale_route_wise_vehicle_wise_planning_details(rvpmId);


        } catch (Exception e) {

            Toast.makeText(ScheduleMapActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(22.336910, 73.831871);



        /*CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latLngArrayList.get(i).getLat(), latLngArrayList.get(i).getLng())).zoom(12.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public class LatLngModel {

        double lat;
        double lng;
        String distributorName;
        String distSortORder;

        public LatLngModel(double lat, double lng, String distributorName, String distSortORder) {
            this.lat = lat;
            this.lng = lng;
            this.distributorName = distributorName;
            this.distSortORder = distSortORder;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getDistributorName() {
            return distributorName;
        }

        public void setDistributorName(String distributorName) {
            this.distributorName = distributorName;
        }

        public String getDistSortORder() {
            return distSortORder;
        }

        public void setDistSortORder(String distSortORder) {
            this.distSortORder = distSortORder;
        }
    }

    private String location_city = "";
    private String location_district = "";
    private String location_taluk = "";
    private String location_state = "";
    private String location_country = "";
    private String LocationAddress = "";
    private String[] LocationArray;

    public void getAddress(double lat, double lng) {
        try {
            Geocoder geocoder = new Geocoder(ScheduleMapActivity.this, Locale.getDefault());
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


    private void get_sale_route_wise_vehicle_wise_planning_details(String rvpmId) {
        showProgressDialog();

        ApiImplementer.getSaleRouteWiseVehicleWisePlanningDetails(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), rvpmId, new Callback<GetSaleRouteWiseVehicleWisePlanningDetailsPojo>() {
            @Override
            public void onResponse(Call<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> call, Response<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> response) {
                hideProgressDialog();

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        latLngArrayList = new ArrayList<>();
                        GetSaleRouteWiseVehicleWisePlanningDetailsPojo getSaleRouteWiseVehicleWisePlanningDetailsPojo = response.body();

                        if (getSaleRouteWiseVehicleWisePlanningDetailsPojo != null && getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().size(); i++) {
                                if (!CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLatitude()) && !CommonUtils.checkIsEmptyOrNullCommon(getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLongitude())) {
                                    if (Double.compare(getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLatitude(), 0.0) != 0 && Double.compare(getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLongitude(), 0.0) != 0) {
                                        latLngArrayList.add(new LatLngModel(getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLatitude(), getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCusLongitude(), getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getCustName(), getSaleRouteWiseVehicleWisePlanningDetailsPojo.getRECORDS().get(i).getRvpdCustSortOrder() + ""));

                                    }
                                }


                            }

                            for (int i = 0; i < latLngArrayList.size(); i++) {

                                getAddress(latLngArrayList.get(i).getLat(), latLngArrayList.get(i).getLng());

                                if (!CommonUtils.checkIsEmptyOrNullCommon(latLngArrayList.get(i).getLat()) && !CommonUtils.checkIsEmptyOrNullCommon(latLngArrayList.get(i).getLng())) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latLngArrayList.get(i).getLat(), latLngArrayList.get(i).getLng()))
                                            .title("(" + latLngArrayList.get(i).getDistSortORder() + ")" + latLngArrayList.get(i).getDistributorName() + ""));
                                }


                            }

                            if (latLngArrayList.size() > 0){
                              //  LatLng latLng = new LatLng(latLngArrayList.get(0).getLat(), latLngArrayList.get(0).getLng());
                                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latLngArrayList.get(0).getLat(), latLngArrayList.get(0).getLng()) ,10) );
                               // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                mMap.getUiSettings().setZoomControlsEnabled(true);
                            }


                        } else {
                            finish();
                            Toast.makeText(ScheduleMapActivity.this, getSaleRouteWiseVehicleWisePlanningDetailsPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (Exception e) {

                    Toast.makeText(ScheduleMapActivity.this, "Error in response", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetSaleRouteWiseVehicleWisePlanningDetailsPojo> call, Throwable t) {

            }
        });

    }

    private void showProgressDialog() {
        if (!ScheduleMapActivity.this.isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!ScheduleMapActivity.this.isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

}
