package com.infinity.infoway.vimal.delear.activity.Complaint;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.fragment.Complaint;
import com.infinity.infoway.vimal.delear.activity.fragment.ComplaintReport;

public class ComplainFormActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * added on 16-09-2020   by harsh
     **/
    String title_screen = "";
    TextInputLayout tv_input_retailer, tv_input_shop_name, tv_input_mobile, tv_input_area, tv_input_village, tv_input_city, tv_input_district, et_input_complain_for;
    EditText et_select_retailer, et_shop_name, et_mobile, et_area, et_village, et_city, et_district, et_complain_for, et_complain_details;
    Button btn_submit_complain, btn_upload_photo_and_video;

    ProgressDialog progDialog;
    ApiInterface apiInterface;
    ConstraintLayout main_Complaint;


    /**
     * Fragment added on 24_9_2020
     **/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    /**
     * Fragment added on 24_9_2020
     **/

    private SharedPref getSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_form);
      //  initView();
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_complaint_main);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void initView() {

        /**TextInput Layout**/
        tv_input_retailer = (TextInputLayout) findViewById(R.id.txt_input_retailer);
        tv_input_shop_name = (TextInputLayout) findViewById(R.id.tv_input_shop_name);
        tv_input_mobile = (TextInputLayout) findViewById(R.id.tv_input_mobile);
        tv_input_area = (TextInputLayout) findViewById(R.id.tv_input_area);
        tv_input_village = (TextInputLayout) findViewById(R.id.tv_input_village);
        tv_input_city = (TextInputLayout) findViewById(R.id.tv_input_city);
        tv_input_district = (TextInputLayout) findViewById(R.id.tv_input_district);
        et_input_complain_for = (TextInputLayout) findViewById(R.id.et_input_complain_for);

        getSharedPref = new SharedPref(this);
        /**Button**/
        btn_submit_complain = (Button) findViewById(R.id.btn_submit_complain);
        btn_submit_complain.setOnClickListener(this);
        btn_upload_photo_and_video = (Button) findViewById(R.id.btn_upload_photo_and_video);
        btn_upload_photo_and_video.setOnClickListener(this);

        main_Complaint = findViewById(R.id.main_Complaint);

        /**Editext**/
        et_select_retailer = (EditText) findViewById(R.id.et_select_retailer);
        et_select_retailer.setOnClickListener(this);
        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_area = (EditText) findViewById(R.id.et_area);
        et_village = (EditText) findViewById(R.id.et_village);
        et_city = (EditText) findViewById(R.id.et_city);
        et_district = (EditText) findViewById(R.id.et_district);
        et_complain_for = (EditText) findViewById(R.id.et_complain_for);
        et_complain_details = (EditText) findViewById(R.id.et_complain_details);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_feedback) {
            if (et_select_retailer.getText().toString().contentEquals("")) {
                Toast.makeText(this, "Please Select Retailer Name", Toast.LENGTH_LONG).show();

            } else if (et_complain_details.getText().toString().contentEquals("")) {

                Toast.makeText(this, "Please Enter Valid Complain Details", Toast.LENGTH_LONG).show();
            } else {

                System.out.println("validated==== inserting users complaint");
              //insert_RoutWise_Complaint();

            }

        } else if (v.getId() == R.id.et_select_retailer_name) {
            Intent intent = new Intent(this, Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private Insert_RoutWise_Complaint_Pojo insert_routWise_complaint_pojo;

   /* private void insert_RoutWise_Complaint(){


        *//** Getting all text fields value to final submit**//*

        String SHOP_NAME = et_shop_name.getText().toString().trim();
        String RETAILER_NAME = et_select_retailer.getText().toString().trim();
        String MOBILE_NO = et_mobile.getText().toString().trim();
        String AREA_NAME = et_area.getText().toString().trim();
        String VILLAGE_NAME = et_village.getText().toString().trim();
        String CITY_NAME = et_city.getText().toString().trim();
        String DISTRICT_NAME = et_district.getText().toString().trim();
        String COMPLAINTFor = et_complain_for.getText().toString().trim();
        String COMPLAINT = et_complain_details.getText().toString().trim();


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

        Call<Insert_RoutWise_Complaint_Pojo> call = apiInterface.insert_routWise_complaint(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                RETAILER_NAME,
                SHOP_NAME, MOBILE_NO,
                AREA_NAME, VILLAGE_NAME, CITY_NAME,
                DISTRICT_NAME,COMPLAINTFor, COMPLAINT


        );


        call.enqueue(new Callback<Insert_RoutWise_Complaint_Pojo>() {
            @Override
            public void onResponse(Call<Insert_RoutWise_Complaint_Pojo> call, Response<Insert_RoutWise_Complaint_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    insert_routWise_complaint_pojo = response.body();
                    if (insert_routWise_complaint_pojo != null) {

                        displaySnackBar(insert_routWise_complaint_pojo.getMESSAGE()+"");
                    }


                }

            }

            @Override
            public void onFailure(Call<Insert_RoutWise_Complaint_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });






        *//** Getting all text fields value to final submit**//*


    }*/

    private Snackbar paymentSnackBar;
    private void displaySnackBar(String message) {
        try {
            if (paymentSnackBar != null && paymentSnackBar.isShown()) {
                paymentSnackBar.dismiss();
            }
            paymentSnackBar = Snackbar.make(main_Complaint, message, Snackbar.LENGTH_LONG);
            paymentSnackBar.show();
        } catch (Exception ex) {
        }
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
                fragment = new Complaint();
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
                fragment.setArguments(bundle);*/
                return fragment;
            } else {
                fragment = new ComplaintReport();
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











}
