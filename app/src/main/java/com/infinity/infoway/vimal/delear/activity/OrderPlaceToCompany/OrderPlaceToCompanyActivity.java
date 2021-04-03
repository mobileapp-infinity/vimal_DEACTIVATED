package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.fragment.EditOrderFragment;
import com.infinity.infoway.vimal.delear.activity.fragment.NewOrderFragment;


public class OrderPlaceToCompanyActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String title_screen = "";
    Intent intent;
    String routeID;
    String CustId;
    String vehicleNO;
    boolean isOrderPlace = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_to_company);
        setUpActionBar();
        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void setUpActionBar() {
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }

        if (getIntent().hasExtra("isOrderPlace")) {
            isOrderPlace = getIntent().getExtras().getBoolean("isOrderPlace");
        } else {
            isOrderPlace = false;
        }

        System.out.println(isOrderPlace);
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        intent = getIntent();

        routeID = intent.getStringExtra("routeId");
        CustId = intent.getStringExtra("CustId");
        vehicleNO = intent.getStringExtra("vehicleNO");
        TabLayout tabLayout = findViewById(R.id.tab_feedback_main);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

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
                fragment = new NewOrderFragment(isOrderPlace, routeID,CustId,vehicleNO);
              /*  Bundle bundle = new Bundle();
                bundle.putString("routeID", routeID);
                fragment.setArguments(bundle);*/
                return fragment;
            } else {
                fragment = new EditOrderFragment();
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
