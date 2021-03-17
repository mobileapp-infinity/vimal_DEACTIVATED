package com.infinity.infoway.vimal.delear.RoutePlanning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.api.ApiImplementer;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.RoutePlanning.fragment.AddRoutePlanningFragment;
import com.infinity.infoway.vimal.delear.RoutePlanning.fragment.RoutPlanningListFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutePlanningActivity extends AppCompatActivity {

    private RecyclerView rvRouteList;
    private ProgressDialog progressDialog;
    private SharedPref sharedPref;
    String title_screen = "";
    private String FOR_TESTING_COMPANY_ID = "20";//changes this before live
    private String FOR_TESTING_CUS_ID = "287";//changes this before live


    /**
     * Fragment added on 24_9_2020
     **/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    /**
     * Fragment added on 24_9_2020
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_planning_activity);
        // initView();
        initView();


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_complaint_main);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //getAllRouteList(true, true);
    }

    /**
     * Adapter For Fragments
     **/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;
            if (position == 0) {
                fragment = new RoutPlanningListFragment();
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
                fragment.setArguments(bundle);*/
                return fragment;
            } else {
                fragment = new AddRoutePlanningFragment();
              /*  Bundle bundle = new Bundle();
                bundle.putString("is_retailer","1");
                bundle.putString("call_type","0");*/
                // fragment.setArguments(bundle);
                return fragment;
                //return ViewCall.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }


    private void initView() {

        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // rvRouteList = findViewById(R.id.rvRouteList);
        sharedPref = new SharedPref(RoutePlanningActivity.this);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(RoutePlanningActivity.this);
        // rvRouteList.setLayoutManager(layoutManager);
    }

    ArrayList<GetAllRouteListPojo.RECORD> arrayList;

    private void getAllRouteList(boolean isPdShow, boolean isPdHide) {
        if (isPdShow) {
            showProgressDialog();
        }
        ApiImplementer.getAllRouteListImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), FOR_TESTING_COMPANY_ID, new Callback<GetAllRouteListPojo>() {
            @Override
            public void onResponse(Call<GetAllRouteListPojo> call, Response<GetAllRouteListPojo> response) {

                if (isPdHide) {
                    hideProgressDialog();
                }

                arrayList = new ArrayList<>();
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        GetAllRouteListPojo getAllRouteListPojo = response.body();
                        getAllEmployeeByDesignation(getAllRouteListPojo);


                    }

                } catch (Exception e) {
                    Toast.makeText(RoutePlanningActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<GetAllRouteListPojo> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(RoutePlanningActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void showProgressDialog() {
        if (!RoutePlanningActivity.this.isFinishing() &&
                progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.processing_request));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (!RoutePlanningActivity.this.isFinishing() &&
                progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    ArrayList<String> employeeNameList;

    private void getAllEmployeeByDesignation(GetAllRouteListPojo getAllRouteListPojo) {


        ApiImplementer.getAllEmployeeByDesignationImplementer(String.valueOf(sharedPref.getAppVersionCode()), sharedPref.getAppAndroidId(), String.valueOf(sharedPref.getRegisteredId()), sharedPref.getRegisteredUserId(), String.valueOf(sharedPref.getCompanyId()), "sales_person", new Callback<GetAllEmployeeByDesignationPojo>() {
            @Override
            public void onResponse(Call<GetAllEmployeeByDesignationPojo> call, Response<GetAllEmployeeByDesignationPojo> response) {

                try {

                    if (response.isSuccessful() && response.body() != null) {

                        GetAllEmployeeByDesignationPojo getAllEmployeeByDesignationPojo = response.body();

                        employeeNameList = new ArrayList<>();
                        employeeNameList.add("Select Employee");
                        if (getAllEmployeeByDesignationPojo != null && getAllEmployeeByDesignationPojo.getRECORDS().size() > 0) {

                            for (int i = 0; i < getAllEmployeeByDesignationPojo.getRECORDS().size(); i++) {
                                employeeNameList.add(getAllEmployeeByDesignationPojo.getRECORDS().get(i).getEmpUserName());
                            }

                            if (getAllRouteListPojo != null && getAllRouteListPojo.getRECORDS().size() > 0) {
                                rvRouteList.setVisibility(View.VISIBLE);
                                for (int i = 0; i < getAllRouteListPojo.getRECORDS().size(); i++) {
                                    arrayList.add(getAllRouteListPojo.getRECORDS().get(i));
                                    arrayList.get(i).setEmpList(employeeNameList);
                                }

                                // RouteListAdapter routeListAdapter = new RouteListAdapter(RoutePlanningActivity.this, getAllRouteListPojo, arrayList, employeeNameList,);
                                // rvRouteList.setAdapter(routeListAdapter);


                            } else {
                                rvRouteList.setVisibility(View.GONE);
                                Toast.makeText(RoutePlanningActivity.this, getAllRouteListPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(RoutePlanningActivity.this, getAllEmployeeByDesignationPojo.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(RoutePlanningActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetAllEmployeeByDesignationPojo> call, Throwable t) {

                Toast.makeText(RoutePlanningActivity.this, "Request Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
