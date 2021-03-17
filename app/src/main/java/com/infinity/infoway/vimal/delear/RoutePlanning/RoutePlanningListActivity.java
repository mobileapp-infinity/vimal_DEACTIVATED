package com.infinity.infoway.vimal.delear.RoutePlanning;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.Adapter.RouteListPlanningAdapter;
import com.infinity.infoway.vimal.delear.RoutePlanning.pojo.GetRoutePlanningListPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutePlanningListActivity extends AppCompatActivity {


    Intent intent;
    RecyclerView rvRoutePlanningList;
    ProgressDialog progDialog;
    public static String date;
    SharedPref sharedPref;
    public static int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning_list2);
        initView();

        intent = getIntent();
        if (intent != null) {
            date = intent.getStringExtra("date");

        }


        getSaleRouteWiseSalesOfficerMappingViewCopy(date);

    }


    private void initView() {
        this.setTitle("Route Planning List");

        progDialog = new ProgressDialog(this);
        sharedPref = new SharedPref(this);
        rvRoutePlanningList = findViewById(R.id.rvRoutePlanningList);
        rvRoutePlanningList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    private void getSaleRouteWiseSalesOfficerMappingViewCopy(String date) {

        showProgressDialog();

        ApiImplementer.getSaleRouteWiseSalesOfficerMappingViewCopy(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), sharedPref.getCompanyId(), date, new Callback<GetRoutePlanningListPojo>() {
            @Override
            public void onResponse(Call<GetRoutePlanningListPojo> call, Response<GetRoutePlanningListPojo> response) {

                hideProgressDialog();

                try {
                    if (response.isSuccessful() && response.body() != null) {


                        GetRoutePlanningListPojo getRoutePlanningListPojo = response.body();

                        if (getRoutePlanningListPojo != null && getRoutePlanningListPojo.getRECORDS().size() > 0) {
                            rvRoutePlanningList.setVisibility(View.VISIBLE);

                            RouteListPlanningAdapter routeListPlanningAdapter = new RouteListPlanningAdapter(RoutePlanningListActivity.this, getRoutePlanningListPojo, date);

                            rvRoutePlanningList.setAdapter(routeListPlanningAdapter);

                        } else {
                            rvRoutePlanningList.setVisibility(View.GONE);
                            Toast.makeText(RoutePlanningListActivity.this, getRoutePlanningListPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (Exception e) {
                    Toast.makeText(RoutePlanningListActivity.this, "Error in response" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetRoutePlanningListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningListActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showProgressDialog() {
        if (!RoutePlanningListActivity.this.isFinishing() &&
                progDialog != null && !progDialog.isShowing()) {
            progDialog.setIndeterminate(true);
            progDialog.setMessage(getResources().getString(R.string.processing_request));
            progDialog.setCancelable(false);
            progDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideProgressDialog() {
        if (!RoutePlanningListActivity.this.isFinishing() &&
                progDialog != null && progDialog.isShowing()) {
            progDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            getSaleRouteWiseSalesOfficerMappingViewCopy(date);
        }


    }
}
