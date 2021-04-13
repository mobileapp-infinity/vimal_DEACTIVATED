package com.infinity.infoway.vimal.delear.activity.UpdateCallList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.config.Config;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.GetAllRouteListPojo;
import com.infinity.infoway.vimal.delear.activity.add_schedule.activity.ScheduleMapActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCallListActivity extends AppCompatActivity {


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private SharedPref getSharedPref;
    private SearchableSpinner spRouteList;
    private RecyclerView rvUpdateCallList;
    ConstraintLayout update_call_list_main;
    private String title_screen = "";
    private ArrayList<String> routeArrayList;
    private ArrayList<String> routeVehicleArrayList;
    private ArrayList<String> routeScheduleList;
    private ArrayList<String> routeIdArrayList;
    private TextView tvView;
    private EditText edVehicleNo;
    private EditText edSchedule;
    private GetAllRouteListPojo getAllRouteListPojo;
    private ArrayList<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo.RECORDSBean> get_retailer_rout_detail_of_login_distributor_pojoArrayList;
    private ArrayList<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo.RECORDSBean> fliteredArrayList = new ArrayList<>();

    /**
     * initial items
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_call_list);
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getAllRouteList(true, false);
        // Get_Retailer_Rout_Detail_Of_Login_Distributor();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * initializaiton of views
     **/
    LinearLayoutManager linearLayoutManager;

    private String routeID = "";

    private void initView() {




        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(this);
        tvView = findViewById(R.id.tvView);
        edVehicleNo = findViewById(R.id.edVehicleNo);
        edSchedule = findViewById(R.id.edSchedule);
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (routeID != null && !routeID.equals("")) {
                    Intent intent = new Intent(UpdateCallListActivity.this, ScheduleMapActivity.class);
                    //rvpmId
                    intent.putExtra("rvpmId", routeID);

                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateCallListActivity.this, "Please Select Route", Toast.LENGTH_LONG).show();
                }


            }
        });
        spRouteList = findViewById(R.id.spRouteList);
        spRouteList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    rvUpdateCallList.setVisibility(View.VISIBLE);
                    fliteredArrayList = new ArrayList<>();
                    routeID = routeIdArrayList.get(i);
                    edVehicleNo.setText(routeVehicleArrayList.get(i));
                    edSchedule.setText(routeScheduleList.get(i));

                    for (int j = 0; j < get_retailer_rout_detail_of_login_distributor_pojoArrayList.size(); j++) {
                        if (String.valueOf(get_retailer_rout_detail_of_login_distributor_pojoArrayList.get(j).getRoute_id()).equals(routeID)) {
                            fliteredArrayList.add(get_retailer_rout_detail_of_login_distributor_pojoArrayList.get(j));
                        }

                    }


                    if (fliteredArrayList.size() > 0) {
                        rvUpdateCallList.setVisibility(View.VISIBLE);
                        UpdateCallListAdapter updateCallListAdapter = new UpdateCallListAdapter(UpdateCallListActivity.this, get_retailer_rout_detail_of_login_distributor_pojo, fliteredArrayList);
                        rvUpdateCallList.setAdapter(updateCallListAdapter);
                        updateCallListAdapter.notifyDataSetChanged();
                    } else {
                        rvUpdateCallList.setVisibility(View.GONE);
                        Toast.makeText(UpdateCallListActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    routeID = "";
                    edSchedule.setText("");
                    edVehicleNo.setText("");
                    if (get_retailer_rout_detail_of_login_distributor_pojoArrayList != null && get_retailer_rout_detail_of_login_distributor_pojoArrayList.size() > 0) {
                        rvUpdateCallList.setVisibility(View.VISIBLE);
                        UpdateCallListAdapter updateCallListAdapter = new UpdateCallListAdapter(UpdateCallListActivity.this, get_retailer_rout_detail_of_login_distributor_pojo, get_retailer_rout_detail_of_login_distributor_pojoArrayList);
                        rvUpdateCallList.setAdapter(updateCallListAdapter);
                        updateCallListAdapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                routeID = "";
            }
        });
        update_call_list_main = findViewById(R.id.update_call_list_main);


        rvUpdateCallList = findViewById(R.id.rvUpdateCallList);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUpdateCallList.setLayoutManager(linearLayoutManager);


    }

    private Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo get_retailer_rout_detail_of_login_distributor_pojo;


    private void Get_Retailer_Rout_Detail_Of_Login_Distributor() {
        if (progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }

        try {
            progDialog = new ProgressDialog(this);
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        } catch (Exception ex) {
        }

        Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call = apiService.get_retailer_rout_detail_of_login_distributor(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,
                getSharedPref.getCompanyId() + ""


        );

        call.enqueue(new Callback<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo>() {
            @Override
            public void onResponse(Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call, Response<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();

                    get_retailer_rout_detail_of_login_distributor_pojoArrayList = new ArrayList<>();
                    get_retailer_rout_detail_of_login_distributor_pojo = response.body();

                    if (get_retailer_rout_detail_of_login_distributor_pojo != null && get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().size() > 0) {

                        for (int i = 0; i < get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().size(); i++) {
                            get_retailer_rout_detail_of_login_distributor_pojoArrayList.add(get_retailer_rout_detail_of_login_distributor_pojo.getRECORDS().get(i));
                        }

                        rvUpdateCallList.setVisibility(View.VISIBLE);
                        UpdateCallListAdapter updateCallListAdapter = new UpdateCallListAdapter(UpdateCallListActivity.this, get_retailer_rout_detail_of_login_distributor_pojo, get_retailer_rout_detail_of_login_distributor_pojoArrayList);
                        rvUpdateCallList.setAdapter(updateCallListAdapter);
                        updateCallListAdapter.notifyDataSetChanged();

                    } else {
                        progDialog.dismiss();
                        rvUpdateCallList.setVisibility(View.GONE);
                        displaySnackBar(get_retailer_rout_detail_of_login_distributor_pojo.getMESSAGE() + "");

                    }


                }

            }

            @Override
            public void onFailure(Call<Get_Retailer_Rout_Detail_Of_Login_Distributor_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());
            }
        });

    }

    private Snackbar paymentSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(update_call_list_main, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
    }


    private void getAllRouteList(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllRouteListImplementer(String.valueOf(getSharedPref.getAppVersionCode()), getSharedPref.getAppAndroidId(), String.valueOf(getSharedPref.getRegisteredId()), getSharedPref.getRegisteredUserId(), getSharedPref.getCompanyId(), new Callback<GetAllRouteListPojo>() {
            @Override
            public void onFailure(Call<GetAllRouteListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(UpdateCallListActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call<GetAllRouteListPojo> call, Response<GetAllRouteListPojo> response) {

                if (isPdHide) {
                    hideProgressDialog();
                }
                Get_Retailer_Rout_Detail_Of_Login_Distributor();
                routeArrayList = new ArrayList<>();
                routeVehicleArrayList = new ArrayList<>();
                routeScheduleList = new ArrayList<>();
                routeIdArrayList = new ArrayList<>();
                routeArrayList.add("Select Route");
                routeVehicleArrayList.add("0");
                routeScheduleList.add("0");
                routeIdArrayList.add("**");

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        getAllRouteListPojo = response.body();
                        if (getAllRouteListPojo != null && getAllRouteListPojo.getRECORDS().size() > 0) {
                            for (int i = 0; i < getAllRouteListPojo.getRECORDS().size(); i++) {

                                routeArrayList.add(getAllRouteListPojo.getRECORDS().get(i).getRouteName());
                                routeIdArrayList.add(getAllRouteListPojo.getRECORDS().get(i).getRouteId() + "");
                                routeVehicleArrayList.add(getAllRouteListPojo.getRECORDS().get(i).getRvpm_vehicle_no() + "");
                                routeScheduleList.add(getAllRouteListPojo.getRECORDS().get(i).getSchedule() + "");

                            }
                            ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                                    (UpdateCallListActivity.this, R.layout.searchable_spinner_text_view,
                                            routeArrayList);
                            customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            spRouteList.setAdapter(customerNameAdapter);
                            spRouteList.setTitle("Select Route");
                            spRouteList.setPositiveButton("Cancel");
                            // spRoute.setSelection(0);


                        } else {
                            ArrayAdapter<String> customerNameAdapter = new ArrayAdapter<String>
                                    (UpdateCallListActivity.this, R.layout.searchable_spinner_text_view,
                                            routeArrayList);
                            customerNameAdapter.setDropDownViewResource(R.layout.searchable_spinner_text_view);
                            spRouteList.setAdapter(customerNameAdapter);
                            spRouteList.setTitle("Select Route");
                            spRouteList.setPositiveButton("Cancel");
                            spRouteList.setSelection(0);
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(UpdateCallListActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    private void showProgressDialog() {
        if (!((Activity) UpdateCallListActivity.this).isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!((Activity) UpdateCallListActivity.this).isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }
}



