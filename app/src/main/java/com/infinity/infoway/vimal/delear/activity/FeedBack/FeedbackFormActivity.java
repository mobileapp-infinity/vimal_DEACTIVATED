package com.infinity.infoway.vimal.delear.activity.FeedBack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.activity.Activity_Select_City;
import com.infinity.infoway.vimal.api.ApiClient;
import com.infinity.infoway.vimal.api.ApiInterface;
import com.infinity.infoway.vimal.database.SharedPref;
import com.infinity.infoway.vimal.delear.activity.fragment.FeedbackFragment;
import com.infinity.infoway.vimal.delear.activity.fragment.FeedbackReport;

public class FeedbackFormActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * added on 16-09-2020   by harsh
     **/
    String title_screen = "";

    private TextInputLayout tv_input_retailer_name, tv_input_shop_name, tv_input_mobile, tv_input_area, tv_input_village, tv_input_city, tv_input_district, tv_input_feedback;
    private EditText et_select_retailer_name, et_feedback, et_shop_name, et_mobile, et_area, et_village, et_city, et_district, et_feedback_attachment;
    Button btn_submit_feedback;


    /**
     * initial items
     **/

    private ApiInterface apiService;
    private ProgressDialog progDialog;
    private ImageView feedback_image_close;
    private SharedPref getSharedPref;

    /**
     * initial items
     **/

    /**
     * Fragment added on 24_9_2020
     **/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    /**
     * Fragment added on 24_9_2020
     **/


    ConstraintLayout main_feed_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        // initView();
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_feedback_main);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initView() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getSharedPref = new SharedPref(this);
        /**Textinput Layout**/
        tv_input_retailer_name = (TextInputLayout) findViewById(R.id.tv_input_retailer_name);
        tv_input_shop_name = findViewById(R.id.tv_input_shop_name);
        tv_input_mobile = findViewById(R.id.tv_input_mobile);
        tv_input_area = findViewById(R.id.tv_input_area);
        tv_input_village = findViewById(R.id.tv_input_village);
        tv_input_city = findViewById(R.id.tv_input_city);
        tv_input_district = findViewById(R.id.tv_input_district);
        tv_input_feedback = findViewById(R.id.tv_input_feedback);
        main_feed_back = findViewById(R.id.main_feed_back);
        /**Editext**/
        et_select_retailer_name = (EditText) findViewById(R.id.et_select_retailer_name);
        et_select_retailer_name.setOnClickListener(this);
        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_area = (EditText) findViewById(R.id.et_area);
        et_village = (EditText) findViewById(R.id.et_village);
        et_city = (EditText) findViewById(R.id.et_city);
        et_district = (EditText) findViewById(R.id.et_district);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        et_feedback_attachment = (EditText) findViewById(R.id.et_feedback_attachment);

        /** Button **/
        btn_submit_feedback = (Button) findViewById(R.id.btn_submit_feedback);
        btn_submit_feedback.setOnClickListener(this);
        et_feedback_attachment.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit_feedback) {
            if (et_select_retailer_name.getText().toString().contentEquals("")) {
                Toast.makeText(FeedbackFormActivity.this, "Please Select Retailer Name", Toast.LENGTH_LONG).show();

            } else if (et_feedback.getText().toString().contentEquals("")) {

                Toast.makeText(FeedbackFormActivity.this, "Please Enter Valid Feedback", Toast.LENGTH_LONG).show();
            } else {

                System.out.println("validated==== inserting users feedback");
                insert_RoutWise_FeedBack();

            }

        } else if (v.getId() == R.id.et_select_retailer_name) {
            Intent intent = new Intent(FeedbackFormActivity.this, Activity_Select_City.class);
            intent.putExtra("isFromCitySelect", 9899);
            startActivityForResult(intent, 9888);

        }
    }

    /**
     * added on 24-09-2020 by harsh
     **/

    private Insert_RoutWise_FeedBack_Pojo insert_routWise_feedBack_pojo;

    private void insert_RoutWise_FeedBack() {

        /** Getting all text fields value to final submit**/

        String SHOP_NAME = et_shop_name.getText().toString().trim();
        String RETAILER_NAME = et_select_retailer_name.getText().toString().trim();
        String MOBILE_NO = et_mobile.getText().toString().trim();
        String AREA_NAME = et_area.getText().toString().trim();
        String VILLAGE_NAME = et_village.getText().toString().trim();
        String CITY_NAME = et_city.getText().toString().trim();
        String DISTRICT_NAME = et_district.getText().toString().trim();
        String Feedback = et_feedback.getText().toString().trim();


        /** Getting all text fields value to final submit**/


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

      /*  Call<Insert_RoutWise_FeedBack_Pojo> call = apiService.insert_routWise_feedBack(
                getSharedPref.getAppVersionCode() + "",
                getSharedPref.getAppAndroidId() + "",
                getSharedPref.getRegisteredId() + "",
                getSharedPref.getRegisteredUserId() + "",
                Config.ACCESS_KEY,

                getSharedPref.getCompanyId() + "",
                RETAILER_NAME,
                SHOP_NAME, MOBILE_NO,
                AREA_NAME, VILLAGE_NAME, CITY_NAME,
                DISTRICT_NAME, Feedback


        );*/

       /* call.enqueue(new Callback<Insert_RoutWise_FeedBack_Pojo>() {
            @Override
            public void onResponse(Call<Insert_RoutWise_FeedBack_Pojo> call, Response<Insert_RoutWise_FeedBack_Pojo> response) {
                if (response.isSuccessful()) {
                    progDialog.dismiss();
                    insert_routWise_feedBack_pojo = response.body();
                    if (insert_routWise_feedBack_pojo != null) {

                        displaySnackBar(insert_routWise_feedBack_pojo.getMESSAGE());
                    }


                }

            }

            @Override
            public void onFailure(Call<Insert_RoutWise_FeedBack_Pojo> call, Throwable t) {
                progDialog.dismiss();
                displaySnackBar(t.getMessage());

            }
        });
*/

    }

    private Snackbar addOrderSnackBar;

    private void displaySnackBar(String message) {
        try {
            if (addOrderSnackBar != null && addOrderSnackBar.isShown()) {
                addOrderSnackBar.dismiss();
            }
            addOrderSnackBar = Snackbar.make(main_feed_back, message, Snackbar.LENGTH_LONG);
            addOrderSnackBar.show();
        } catch (Exception ex) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9888) {
            if (data.hasExtra("Cus_Name")) {
                et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));

                if (data.getExtras().getString("Shop_Name") == null) {
                    et_shop_name.setText("-");
                } else {
                    et_shop_name.setText(data.getExtras().getString("Shop_Name"));
                }

                if (data.getExtras().getString("Mobile_No") == null) {
                    et_mobile.setText("-");
                } else {
                    et_mobile.setText(data.getExtras().getString("Mobile_No"));
                }

                if (data.getExtras().getString("Area_Name") == null) {
                    et_area.setText("-");
                } else {
                    et_area.setText(data.getExtras().getString("Area_Name"));
                }

                if (data.getExtras().getString("City_Name") == null) {
                    et_city.setText("-");
                } else {
                    et_city.setText(data.getExtras().getString("City_Name"));
                }

                if (data.getExtras().getString("District_Name") == null) {
                    et_district.setText("-");
                } else {
                    et_district.setText(data.getExtras().getString("District_Name"));
                }


                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));
                // et_select_retailer_name.setText(data.getExtras().getString("Cus_Name"));

            }


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
                fragment = new FeedbackFragment();
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
                fragment.setArguments(bundle);*/
                return fragment;
            } else {
                fragment = new FeedbackReport();
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
