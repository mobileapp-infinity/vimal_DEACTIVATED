package com.infinity.infoway.vimal.activity;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.infinity.infoway.vimal.R;
import com.infinity.infoway.vimal.fragment.AddAttendace;
import com.infinity.infoway.vimal.fragment.ViewAttendance;


public class Activity_Attendance_Management extends AppCompatActivity implements ViewAttendance.OnFragmentAttendanceInteractionListener, AddAttendace.OnAddAttendanceInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String title_screen="";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_management);

        if(getIntent().hasExtra("title_screen")){
            title_screen=getIntent().getExtras().getString("title_screen");
        }


        this.setTitle(title_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_attendance);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    /*@Override
    public void onFragmentInteraction(int fragment_counter) {
        if(fragment_counter==1){
            Toast.makeText(this, ""+fragment_counter, Toast.LENGTH_SHORT).show();
        }else if(fragment_counter==2){

        }
    }*/

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(int value) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){
                return AddAttendace.newInstance();
            }else{
                return ViewAttendance.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }


}
