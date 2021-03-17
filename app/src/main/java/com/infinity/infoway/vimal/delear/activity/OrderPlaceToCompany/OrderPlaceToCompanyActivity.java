package com.infinity.infoway.vimal.delear.activity.OrderPlaceToCompany;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.fragment.NewOrderFragment;
import com.infinity.infoway.vimal.delear.activity.fragment.EditOrderFragment;

public class OrderPlaceToCompanyActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String title_screen = "";
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
                fragment = new NewOrderFragment(isOrderPlace);
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
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
