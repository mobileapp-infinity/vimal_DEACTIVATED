package com.infinity.infoway.vimal.delear.activity.add_schedule.activity;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.delear.activity.add_schedule.fragment.AddScheduleFragment;
import com.infinity.infoway.vimal.delear.activity.add_schedule.fragment.ScheduleListFragment;

public class ScheduleActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager scheduleViewPager;
    String title_screen = "";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        if (getIntent().hasExtra("title_screen")) {
            title_screen = getIntent().getExtras().getString("title_screen");
        }
        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        scheduleViewPager = (ViewPager) findViewById(R.id.scheduleViewPager);
        scheduleViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab_call);
        scheduleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(scheduleViewPager));
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
                fragment = new ScheduleListFragment();
               /* Bundle bundle = new Bundle();
                bundle.putBoolean("is_call",true);
                fragment.setArguments(bundle);*/
                return fragment;
            } else {
                fragment = new AddScheduleFragment();
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
